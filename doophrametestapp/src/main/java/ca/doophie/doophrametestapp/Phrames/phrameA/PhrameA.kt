package ca.doophie.doophrametestapp.phrames.phrameA

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ca.doophie.doophrame2.extensions.attach
import ca.doophie.doophrame2.fragmentFramework.Doophragment
import ca.doophie.doophrame2.transitions.TargetedTransition
import ca.doophie.doophrametestapp.phrames.PhrameB
import ca.doophie.doophrametestapp.R
import ca.doophie.doophrametestapp.models.PhrameObject
import kotlinx.android.synthetic.main.phrame_a.*

class PhrameA: Doophragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.phrame_a, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        phrameRecycler.layoutManager = LinearLayoutManager(context)

        // make a list of data
        val data = List(500) { PhrameObject(it) }

        phrameRecycler.adapter = PhrameAAdapter(data) { text, image, phrameObject ->
            // On item clicked callback

            // we attach a view and transition from the clicked items text and image to the
            // details fragment text and image, we also set a back state so on back pressed
            // will return to here
            (this.view?.parent as? ViewGroup?)?.attach(PhrameB(phrameObject), listOf(
                TargetedTransition(this, text, R.id.detailsText),
                TargetedTransition(this, image, R.id.detailsImage)
            ), "StateA")

        }
    }

}