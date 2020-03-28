package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import dev.iamfoodie.customviewskotlin.R

class ColorSlider @JvmOverloads
    constructor(val ctx: Context, val attributeSet: AttributeSet? = null, defStyleAttr: Int = R.attr.seekBarStyle)
    : AppCompatSeekBar(ctx, attributeSet, defStyleAttr){

    private var colors: ArrayList<Int>

    init {

        val typedArray = ctx.obtainStyledAttributes(attributeSet, R.styleable.ColorSlider)
        colors = typedArray.getTextArray(R.styleable.ColorSlider_colors)
            .map { Color.parseColor(it.toString()) }
            as ArrayList<Int>

        max = colors.size - 1
        typedArray.recycle()

        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        progressBackgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        thumb = ctx.getDrawable(R.drawable.ic_arrow_down)
        setPadding(paddingLeft + 10, paddingTop, paddingRight, paddingBottom + 100)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawTickmarks(canvas)
    }

    private fun drawTickmarks(canvas: Canvas?) {
        canvas?.let {
            val w = 48f
            val h = 48f
            val halfW = if (w >= 0) w/2 else 1f
            val halfH = if (h >= 0) h/2 else 1f

            val count = colors.size
            val saveCount = canvas.save()
            canvas.translate(paddingLeft.toFloat(), (height / 2).toFloat())
            for (i in 0 until count) {
                val spacing = (width - paddingLeft - paddingRight) / count

                val paint = Paint()
                paint.color = colors[i]
                canvas.drawRect(-halfW, -halfH, halfW, halfH, paint)
                canvas.translate(spacing.toFloat(), 0f)
            }
            canvas.restoreToCount(saveCount)

        }
    }

}