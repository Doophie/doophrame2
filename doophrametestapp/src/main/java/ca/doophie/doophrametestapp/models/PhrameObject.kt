package ca.doophie.doophrametestapp.models

import android.graphics.Color
import kotlin.random.Random

data class PhrameObject(val pos: Int) {

    val color: Int
        get() {
            val rnd = Random(pos)
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

}