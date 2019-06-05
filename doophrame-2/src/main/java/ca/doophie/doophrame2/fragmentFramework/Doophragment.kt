package ca.doophie.doophrame2.fragmentFramework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.transition.TransitionInflater
import android.widget.TextView
import ca.doophie.doophrame2.R
import ca.doophie.doophrame2.transitions.TargetedTransition


abstract class Doophragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transitions?.forEach { transition ->
            view.findViewById<View>(transition.toObjectId).transitionName = transition.fromObject.transitionName
        }

        startPostponedEnterTransition()
    }

    internal var transitions: List<TargetedTransition>? = null
}