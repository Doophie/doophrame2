package ca.doophie.doophrametestapp.phrames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.doophie.doophrame2.fragmentFramework.Doophragment
import ca.doophie.doophrametestapp.R
import ca.doophie.doophrametestapp.models.PhrameObject
import kotlinx.android.synthetic.main.list_item_a.*
import kotlinx.android.synthetic.main.phrame_b.*

class PhrameB(private val phrameObject: PhrameObject) : Doophragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.phrame_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsText.text = "Position ${phrameObject.pos}"
        detailsImage.setColorFilter(phrameObject.color)
    }

}