package ca.doophie.doophrametestapp.activities.login

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and


class LoginHelper {
    companion object {
        const val PASSWORD_HASH_SALT = "PA16SA61SIAN31351E"
    }

    fun isValidUsername(username: String): Boolean {
        return (!username.isBlank() &&
                isAlphanumeric(username))
    }

    private fun isAlphanumeric(text: String): Boolean {
        return (text.matches(Regex(".*[A-Za-z].*")) &&
            text.matches(Regex(".*[0-9].*")) &&
            text.matches(Regex("[A-Za-z0-9]*")))
    }

    fun getPasswordHash(password: String?): String? {
        val salt = PASSWORD_HASH_SALT.toByteArray()

        var generatedPassword: String? = null
        try {
            val md = MessageDigest.getInstance("SHA-512")
            md.update(salt)
            val bytes = md.digest(password?.toByteArray())
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(Integer.toString((bytes[i] and Byte.MAX_VALUE) + 0x100, 16).substring(1))
            }
            generatedPassword = sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return generatedPassword
    }
}