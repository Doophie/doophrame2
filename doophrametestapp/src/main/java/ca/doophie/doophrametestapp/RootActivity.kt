package ca.doophie.doophrametestapp

import android.os.Bundle
import ca.doophie.doophrame2.DoophieActivity
import ca.doophie.doophrame2.extensions.attach
import ca.doophie.doophrametestapp.phrames.phrameA.PhrameA
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity: DoophieActivity() {

    override val TAG: String = "RootActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        content_view.attach(PhrameA())
    }

}