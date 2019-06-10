package ca.doophie.doophrametestapp.activities

import android.os.Bundle
import android.widget.Toast
import ca.doophie.doophrame2.DoophieActivity
import ca.doophie.doophrame2.objectManagment.DoophieDataStore
import ca.doophie.doophrametestapp.models.User

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : DoophieActivity("activity_login") {

    companion object {
        const val USER_KEY = "user"
    }

    private val usersDatabase
        get() = DoophieDataStore(applicationContext, "users")

    private val username: String
        get() = usernameText.text.toString()

    private val password: String
        get() = passwordText.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginButton.setOnClickListener {

            if (username.isBlank()) {
                loginFailed("Username can not be blank")
                return@setOnClickListener
            }

            if (usersDatabase.contains(username)) {
                val user = usersDatabase.retrieveObject<User>(username)

                if (user?.password == password)
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

