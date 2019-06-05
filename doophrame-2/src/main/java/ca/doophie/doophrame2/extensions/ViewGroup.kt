package ca.doophie.doophrame2.extensions

import android.transition.TransitionInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ca.doophie.doophrame2.fragmentFramework.Doophragment
import ca.doophie.doophrame2.R
import ca.doophie.doophrame2.transitions.TargetedTransition

fun ViewGroup.attach(fragment: Doophragment, withBackState: String? = null) {
    (this.context as? AppCompatActivity?)?.supportFragmentManager?.commit {
        if (withBackState != null) addToBackStack(withBackState)

        replace(this@attach.id, fragment)
    }
}

fun ViewGroup.attach(fragment: Doophragment, transitions: List<TargetedTransition>, withBackState: String? = null) {

    val slide = TransitionInflater.from(context).inflateTransition(R.transition.change_image_transform)
    val explode  = TransitionInflater.from(context).inflateTransition(R.transition.explode)

    for (transition in transitions) {
        transition.fromFragment.exitTransition = explode
        transition.fromFragment.sharedElementReturnTransition = slide

        fragment.sharedElementEnterTransition = slide
        fragment.enterTransition = explode
    }

    fragment.postponeEnterTransition()

    (this.context as? AppCompatActivity?)?.supportFragmentManager?.commit {
        if (withBackState != null) addToBackStack(withBackState)

        fragment.transitions = transitions

        for (transition in transitions) {
            addSharedElement(transition.fromObject, transition.fromObject.transitionName)
        }

        replace(this@attach.id, fragment)
    }
}