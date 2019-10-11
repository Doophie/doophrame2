package ca.doophie.doophrametestapp.activities

import android.os.Bundle
import android.widget.Toast
import ca.doophie.doophrame2.DoophieActivity
import ca.doophie.doophrame2.extensions.attach
import ca.doophie.doophrame2.objectManagment.DoophieDataStore
import ca.doophie.doophrametestapp.activities.login.LoginActivity
import ca.doophie.doophrametestapp.models.User
import ca.doophie.doophrametestapp.phrames.phrameA.PhrameA
import kotlinx.android.synthetic.main.activity_root.*
import java.lang.Thread.sleep

class RootActivity: DoophieActivity("activity_root") {

    private val usersDatabase
        get() = DoophieDataStore(applicationContext, "users")

    private val user: User
        get() = usersDatabase.retrieveObject(intent.getStringExtra(LoginActivity.USER_KEY)) ?: User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Welcome ${user.email}"

        content_view.attach(PhrameA())
    }

}