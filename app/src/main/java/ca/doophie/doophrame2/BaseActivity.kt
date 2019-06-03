package ca.doophie.doophrame2

import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import ca.doophie.doophrame2.fragmentFramework.Doophrame

/*
    this should always be the first and only
    activity used in any application using doophrame2
*/

abstract class BaseActivity : AppCompatActivity() {

    abstract val rootFrame: Doophrame

    abstract val contentViewId: Int

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        attachBaseFragment(contentViewId)
    }

    final fun attachBaseFragment(contentViewId: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().add(contentViewId, rootFrame)

        fragmentTransaction.commit()
    }
}