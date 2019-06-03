package ca.doophie.doophrame2.fragmentFramework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class Doophrame : Fragment() {

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    final fun attach(phrame: Doophrame, containerId: Int) {
        val transaction = activity?.supportFragmentManager?.beginTransaction() ?: return

        transaction.replace(containerId, phrame)

        transaction.commit()
    }

    final fun transition(phrame: Doophrame) {
        val transaction = activity?.supportFragmentManager?.beginTransaction() ?: return

        val parent = view?.parent as? ViewGroup? ?: return

        transaction.remove(this)

        transaction.add(parent.id, phrame)

        transaction.addToBackStack(this::class.toString())

        transaction.commit()
    }
}