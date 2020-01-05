package ca.doophie.doophrame2.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.RelativeLayout
import ca.doophie.doophrame2.R

class RoundedRelativeLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private lateinit var rectF: RectF
    private val path = Path()
    private var cornerRadius = 15f

    init {
        val ta = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedRelativeLayout)
        cornerRadius = ta.getFloat(R.styleable.RoundedRelativeLayout_cornerRadius, 15f)
        ta.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
        resetPath()
    }

    override fun draw(canvas: Canvas) {
        val save = canvas.save()
        canvas.clipPath(path)
        super.draw(canvas)
        canvas.restoreToCount(save)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val save = canvas.save()
        canvas.clipPath(path)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(save)
    }

    private fun resetPath() {
        path.reset()
        path.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
        path.close()
    }
}

