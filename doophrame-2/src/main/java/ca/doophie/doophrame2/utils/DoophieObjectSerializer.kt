@file:Suppress("UNCHECKED_CAST")

package ca.doophie.doophrame2.utils

import java.io.*
import android.R.attr.password
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


object DoophieObjectSerializer {

    const val TAG = "DoophieObjectSerializer"

    /**
     * Serialize given object into [String] using [ObjectOutputStream].
     * @param obj object to serialize
     * @see ObjectInputStream
     * @return the serialization result, empty string for _null_ input
     */
    fun <T : Serializable> serialize(obj: T?): String {
        if (obj == null) {
            return ""
        }

        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(obj)
        oos.close()

        return encrypt(baos.toByteArray()).toString(Charset.defaultCharset())
    }

    /**
     * Deserialize given [String] using [ObjectInputStream].
     * @param string the string to deserialize
     * @return deserialized object, null, in case of error.
     */
    fun <T : Serializable> deserialize(string: String): T? {
        if (string.isEmpty()) {
            return null
        }

        val bais = ByteArrayInputStream(decrypt(string.toByteArray(Charset.defaultCharset())))
        val ois = ObjectInputStream(bais)

        return ois.readObject() as T
    }

    private fun encrypt(bytes: ByteArray): ByteArray {
        var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        return cipher.doFinal(bytes.reversed().toByteArray())
    }

    private fun decrypt(bytes: ByteArray): ByteArray {
        var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, getKey())
        return cipher.doFinal(bytes).reversed().toByteArray()
    }

    // TODO: Make this use a better key
    private fun getKey(): SecretKey {
        return SecretKeySpec("WhatAFuckingKey".toByteArray(charset("ISO-8859-1")), "AES")
    }

}