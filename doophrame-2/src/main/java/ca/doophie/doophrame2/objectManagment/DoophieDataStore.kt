package ca.doophie.doophrame2.objectManagment

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.lang.Exception
import kotlin.collections.HashMap
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaType
import android.R



interface DataStore {
    fun delete(key: String? = null)
    fun child(name: String) : DataStore
    fun contains(key: String): Boolean
    fun <ObjectType: Any>retrieveObject(key: String) : ObjectType?
    fun <ObjectType: Any>storeObject(key: String, `object`: ObjectType)
    fun <ObjectType: Any>watch(key: String, watcher: (ObjectType?)->Unit)
}

class DoophieDataStore(context: Context, private val name: String) : DataStore {

    companion object {
        private const val TAG = "DoophieDataStore"

        private val cachedObjects: HashMap<String, Any> = HashMap()
    }

    private var storedJSON: JSONObject
        set(value) {
            dataStoreFile.writeText(value.toString())
        }
        get() {
            val text = dataStoreFile.readText()

            if(text.isBlank())
                return JSONObject()

            return JSONObject(text)
        }

    private val dataStoreFile: File = File(context.filesDir, name)
        get() {
            if (!field.exists())
                field.createNewFile()

            return field
        }

    // value is always a (Object)->Unit where object is the type of the key's object
    private var watchers: HashMap<String, Any> = HashMap()

    override fun delete(key: String?) {
        if (key == null) {
            storedJSON = JSONObject()
            cachedObjects.keys.filter { it.startsWith("$name/") }.forEach {
                cachedObjects.remove(it)
            }
        } else {
            if (!contains(key)) return

            val updatedJSON = storedJSON

            var removeKey: (String)->Unit = { key ->
                updatedJSON.remove(key)

                if (cachedObjects.containsKey("$name/$key"))
                    cachedObjects.remove("$name/$key")
            }

            if (key.endsWith("/*"))
                updatedJSON.keys().forEach {
                    if (it.startsWith("${key.dropLast(1)}")) removeKey(it)
                }
            else removeKey(key)

            storedJSON = updatedJSON
        }
    }

    override fun child(name: String): DataStore {
        return DoophieDataStoreChild(this, name)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <ObjectType : Any> watch(key: String, watcher: (ObjectType?) -> Unit) {
        if (watchers.containsKey(key)) {
            (watchers[key] as?  ArrayList<(ObjectType?)->Unit>)?.add(watcher)
        } else {
            val list = ArrayList<(ObjectType?)->Unit>()

            list.add(watcher)

            watchers[key] = list
        }

        watcher.invoke(retrieveObject(key))
    }


    override fun contains(key: String): Boolean {
        return storedJSON.opt(key) != null
    }

    private fun <ObjectType: Any>getObjectProperties(`object`: ObjectType): HashMap<String, Any> {
        val allObjectMembers = `object`::class.memberProperties

        val outMap = HashMap<String, Any>()
        for (property in allObjectMembers) {
            try {
                outMap[property.name] = readInstanceProperty(`object`, property.name)

                if (outMap[property.name]?.javaClass?.isEnum == true) {
                    outMap[property.name] = (outMap[property.name] as? Enum<*>?)?.name ?: ""
                }
            } catch (e: Exception) {
                continue
            }
        }

        Log.d(TAG, "getObjProperties outMap = $outMap")

        return outMap
    }

    @Suppress("UNCHECKED_CAST")
    fun <R>readInstanceProperty(instance: Any, propertyName: String): R {
        val property = instance::class.memberProperties
                .first { it.name == propertyName } as KProperty1<Any, *>

        return property.get(instance) as R
    }

    @Suppress("UNCHECKED_CAST")
    override fun <ObjectType: Any>storeObject(key: String, `object`: ObjectType) {
        cachedObjects["$name/$key"] = `object`

        val propertiesJson = JSONObject(getObjectProperties(`object`))

        val objJSON = JSONObject()

        objJSON.put(`object`::class.java.name, propertiesJson)

        val updatedJSON = storedJSON

        updatedJSON.put(key, objJSON)

        storedJSON = updatedJSON

        if (watchers.containsKey(key)) {
            (watchers[key] as ArrayList<(ObjectType?)->Unit>).forEach { it(`object`) }
        }
    }

    @Throws
    @Suppress("UNCHECKED_CAST")
    override fun <ObjectType: Any>retrieveObject(key: String) : ObjectType? {
        val storedObjJSON = storedJSON[key] as? JSONObject? ?: return null

        if (!contains(key)) return null

        val ofType = storedObjJSON.keys().next()

        if (cachedObjects.containsKey("$name/$key")) {
            Log.d(TAG, "Retrieving cached $key object")

            return(cachedObjects["$name/$key"] as? ObjectType?)
        }

        Log.d(TAG, "Retrieving $key object from file")

        val returnObject: ObjectType
        try {
             returnObject = Class.forName(ofType).newInstance() as ObjectType
        } catch (e: Exception) {
            if (e::class.java == InstantiationException::class.java)
                throw InvalidObjectParametersException()
            else
                throw InvalidKeyForObjectType(ofType)
        }
        val objProperties = storedObjJSON[ofType] as JSONObject

        val properties = returnObject.javaClass.kotlin.memberProperties
        for (p in properties.filterIsInstance<KMutableProperty<*>>()) {
            val data = try { when (p.returnType.javaType) {
                Int::class.javaPrimitiveType,
                Int::class.javaObjectType -> objProperties[p.name] as? Int?
                Double::class.javaPrimitiveType,
                Double::class.javaObjectType -> objProperties[p.name] as? Double?
                String::class.java -> objProperties[p.name] as? String?
                else -> if (readInstanceProperty<Any?>(returnObject, p.name)?.javaClass?.isEnum == true) {
                    val valueOf = p.javaClass.getMethod("valueOf", String::class.java)
                    valueOf.invoke(null, objProperties[p.name])
                } else
                    objProperties[p.name]
            } } catch (e: Exception) { null }

            if (data != null)
                p.setter.call(returnObject, data)
        }

        cachedObjects[key] = returnObject

        return returnObject
    }

    private class DoophieDataStoreChild(private val parentDataStore: DataStore,
                                        private val name: String) : DataStore {
        override fun delete(key: String?) {
            if (key == null)
                parentDataStore.delete("$name/$key")
            else
                parentDataStore.delete("$name/*")
        }

        override fun <ObjectType : Any> watch(key: String, watcher: (ObjectType?) -> Unit) {
            parentDataStore.watch(key, watcher)
        }

        override fun child(name: String): DataStore {
            return DoophieDataStoreChild(parentDataStore, "${this.name}/$name")
        }

        override fun <ObjectType: Any>storeObject(key: String, `object`: ObjectType) {
            parentDataStore.storeObject("$name/$key", `object`)
        }

        override fun <ObjectType: Any>retrieveObject(key: String) : ObjectType? {
            return parentDataStore.retrieveObject("$name/$key")
        }

        override fun contains(key: String): Boolean {
            return parentDataStore.contains("$name/$key")
        }

    }

}

class InvalidKeyForObjectType(expected: String):
        Exception("The object stored with this key has a different type than expected.\nExpected: $expected)")

class InvalidObjectParametersException: Exception("All objects must have default values for all parameters.")

