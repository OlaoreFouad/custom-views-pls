package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import dev.iamfoodie.customviewskotlin.R

class ColorDialView @JvmOverloads
    constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, private val defStyle: Int = 0)
    : View(ctx, attributeSet, defStyle) {

    private var dialDrawable: Drawable? = null
    private var dialDiameter = toDP(100)

    private var horizontalSize = 0f
    private var verticalSize = 0f

    private var centerHorizontal = 0f
    private var centerVertical = 0f

    init {
        dialDrawable = ctx.getDrawable(R.drawable.ic_dial)
        dialDrawable?.let {
            it.bounds = getCenteredBounds(dialDiameter)
        }

        refreshValues()
    }

    private fun getCenteredBounds(size: Int, scalar: Int = 1): Rect {
        val half = (if (size == 0) 1 else size/2) * scalar
        return Rect(-half, -half, half, half)
    }

    private fun refreshValues() {
        horizontalSize = dialDiameter.toFloat()
        verticalSize = dialDiameter.toFloat()

        centerHorizontal = (horizontalSize / 2f).toFloat()
        centerVertical = (verticalSize / 2f).toFloat()
    }

    private fun toDP(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            ctx.resources.displayMetrics
        ).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(centerHorizontal, centerVertical)
        dialDrawable?.draw(canvas)
    }
}
