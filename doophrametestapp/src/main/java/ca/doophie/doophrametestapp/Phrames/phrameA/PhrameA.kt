package ca.doophie.doophrametestapp.phrames.phrameA

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import ca.doophie.doophrame2.extensions.attach
import ca.doophie.doophrame2.fragmentFramework.Doophragment
import ca.doophie.doophrame2.transitions.TargetedTransition
import ca.doophie.doophrametestapp.phrames.PhrameB
import ca.doophie.doophrametestapp.R
import ca.doophie.doophrametestapp.models.PhrameObject
import kotlinx.android.synthetic.main.phrame_a.*
import kotlinx.android.synthetic.main.phrame_b.view.*

class PhrameA: Doophragment() {

    private val data = List(500) { PhrameObject(it) }

    private var selectedObject: PhrameObject? = null
    private var isShowingMiniDetails = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.phrame_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        updateMiniDetails()

        miniDetailsButton.setOnClickListener {
            isShowingMiniDetails = !isShowingMiniDetails

            updateMiniDetails()
        }

        miniDetailsLayout.setOnClickListener {
            val obj = selectedObject ?: return@setOnClickListener

            showLargeDetails(miniDetailsLayout.detailsText, miniDetailsLayout.detailsImage, obj)
        }
    }

    private fun updateMiniDetails() {
        val selObj = selectedObject

        miniDetailsButton.text = if (isShowingMiniDetails)
            "Hide Mini Details View"
        else
            "Show Mini Details View"

        if (isShowingMiniDetails && selObj != null) {
            miniDetailsLayout.visibility = View.VISIBLE
            miniDetailsLayout.attach(PhrameB(selObj), "StateA")
        } else {
            miniDetailsLayout.visibility = View.GONE
        }
    }

    private fun showLargeDetails(trasitionText: TextView, transitionImage: ImageView, phrameObject: PhrameObject) {
        (this.view?.parent as? ViewGroup?)?.attach(PhrameB(phrameObject), listOf(
            TargetedTransition(this, trasitionText, R.id.detailsText),
            TargetedTransition(this, transitionImage, R.id.detailsImage)
        ), "StateB")
    }

    private fun setUpAdapter() {
        phrameRecycler.layoutManager = LinearLayoutManager(context)

        phrameRecycler.adapter = PhrameAAdapter(data) { text, image, phrameObject ->
            // On item clicked callback
            selectedObject = phrameObject

            if (isShowingMiniDetails)
                updateMiniDetails()
            else
                showLargeDetails(text, image, phrameObject)
        }
    }

}