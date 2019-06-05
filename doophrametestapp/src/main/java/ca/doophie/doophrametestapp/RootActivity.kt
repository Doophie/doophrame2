package ca.doophie.doophrametestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.doophie.doophrame2.DoophieActivity
import ca.doophie.doophrame2.extensions.attach
import ca.doophie.doophrametestapp.phrames.phrameA.PhrameA
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        content_view.attach(PhrameA())
    }

}