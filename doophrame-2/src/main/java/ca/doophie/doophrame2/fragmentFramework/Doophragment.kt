package ca.doophie.doophrame2.fragmentFramework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.transition.TransitionInflater
import android.widget.TextView
import ca.doophie.doophrame2.R


abstract class Doophragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startPostponedEnterTransition()
    }

    fun transition(toFragment: Fragment, fromObjectId: Int, containerId: Int) {
        sharedElementReturnTransition = TransitionInflater.from(activity).inflateTransition(R.transition.change_image_transform)
        exitTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.explode)

        // Create new fragment to add (Fragment B)
        toFragment.sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(R.transition.change_image_transform)
        toFragment.enterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.explode)

        // Our shared element (in Fragment A)
        val mText = activity?.findViewById(fromObjectId) as TextView

        // Add Fragment B
        val ft = fragmentManager!!.beginTransaction()
            .replace(containerId, toFragment)
            .addSharedElement(mText, mText.transitionName)
        ft.commit()
    }

}