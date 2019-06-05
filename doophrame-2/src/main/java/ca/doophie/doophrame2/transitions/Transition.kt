package ca.doophie.doophrame2.transitions

import android.view.View
import ca.doophie.doophrame2.fragmentFramework.Doophragment

data class TargetedTransition (
    val fromFragment: Doophragment,
    val fromObject: View,
    val toObjectId: Int
)