package ca.doophie.doophrametestapp

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import ca.doophie.doophrame2.DoophieActivity
import ca.doophie.doophrame2.extensions.attach
import ca.doophie.doophrame2.transitions.TargetedTransition
import ca.doophie.doophrametestapp.Phrames.PhrameA
import ca.doophie.doophrametestapp.Phrames.PhrameB
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.phrame_a.*
import kotlinx.android.synthetic.main.phrame_b.*

class RootActivity: DoophieActivity() {

    override val TAG: String = "RootActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        val phrameA = PhrameA()

        content_view.attach(phrameA)

        content_view.setOnClickListener {
            val frameB = PhrameB()

            content_view.attach(frameB, listOf(
                TargetedTransition(phrameA, phrameA.fufkText),
                TargetedTransition(phrameA, phrameA.textView)
            ), "fresh")
        }
    }

}