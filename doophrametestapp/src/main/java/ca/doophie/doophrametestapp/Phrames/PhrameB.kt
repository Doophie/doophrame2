package ca.doophie.doophrametestapp.Phrames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.doophie.doophrame2.fragmentFramework.Doophragment
import ca.doophie.doophrametestapp.R

class PhrameB : Doophragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.phrame_b, container, false)
    }
}