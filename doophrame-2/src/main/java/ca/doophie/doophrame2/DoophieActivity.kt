package ca.doophie.doophrame2

import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import ca.doophie.doophrame2.utils.DoophieObjectSerializer
import java.io.Serializable

abstract class DoophieActivity : AppCompatActivity() {

    abstract val TAG: String

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    private val activityPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences(TAG, MODE_PRIVATE) }

    private val appPrefs: SharedPreferences
        get() { return applicationContext.getSharedPreferences("AppPrefs", MODE_PRIVATE) }

    /***
     * Saves obj with the given key, applies the changes in background
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePref(key: String, obj: Serializable?, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()

        if (obj == null) {
            prefs.remove(key)
        } else {
            prefs.putString(key, DoophieObjectSerializer.serialize(obj))
        }

        prefs.apply()
    }

    /***
     * Saves obj with the given key, applies the changes immediately and is a blocking call
     * @param key the key of the obj
     * @param obj the object
     * @param private determines whether or not this key is accessible outside of this activity
     */
    protected fun savePrefNow(key: String, obj: Serializable?, private: Boolean = false){
        val prefs = if (private) activityPrefs.edit() else appPrefs.edit()

        if (obj == null) {
            prefs.remove(key)
        } else {
            prefs.putString(key, DoophieObjectSerializer.serialize(obj))
        }

        prefs.commit()
    }


    protected fun <T: Serializable>getPref(key: String, private: Boolean = true): T? {
        val prefs = if (private) activityPrefs else appPrefs
        val obj = DoophieObjectSerializer.deserialize<T>(prefs.getString(key, ""))
        return obj
    }

    protected val screenWidth: Int
        get() {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getRealSize(size)
            return size.x
        }

    protected val screenHeight: Int
        get() {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getRealSize(size)
            return size.y
        }
}