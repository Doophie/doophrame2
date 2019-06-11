package ca.doophie.doophrametestapp.activities.login

import android.os.Bundle
import android.widget.Toast
import ca.doophie.doophrame2.DoophieActivity
import ca.doophie.doophrame2.objectManagment.DoophieDataStore
import ca.doophie.doophrametestapp.activities.RootActivity
import ca.doophie.doophrametestapp.models.User

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : DoophieActivity("activity_login") {

    companion object {
        const val USER_KEY = "user"
    }

    private val helper = LoginHelper()

    private val usersDatabase
        get() = DoophieDataStore(applicationContext, "users")

    private val username: String
        get() = usernameText.text.toString()

    private val password: String
        get() = passwordText.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginButton.setOnClickListener {
            if (!helper.isValidUsername(username)) {
                loginFailed("Invalid Username")
                return@setOnClickListener
            }

            if (usersDatabase.contains(username)) {
                val user = usersDatabase.retrieveObject<User>(username)!!

                if (helper.getPasswordHash(user.password) == helper.getPasswordHash(password))
                    login(user)
                else
                    loginFailed("Invalid password")
            } else {
                val user = User(username, password)

                usersDatabase.storeObject(username, user)

                login(user)
            }
        }
    }

    private fun loginFailed(reason: String) {
        Toast.makeText(applicationContext, reason, Toast.LENGTH_SHORT).show()
    }

    private fun login(user: User) {
        switchTo(RootActivity(), hashMapOf(USER_KEY to user.email))
    }

}

