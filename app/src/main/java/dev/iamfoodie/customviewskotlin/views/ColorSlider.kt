package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import dev.iamfoodie.customviewskotlin.R

class ColorSlider @JvmOverloads
    constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, defStyleAttr: Int = R.attr.seekBarStyle)
    : AppCompatSeekBar(ctx, attributeSet, defStyleAttr){

    private var colors: ArrayList<Int>
    private val noColorDrawable: Drawable
    private val listeners: ArrayList<((Int) -> Unit)> = arrayListOf()
    private val w = 48f
    private val h = 48f
    private val halfW = if (w >= 0) w/2 else 1f
    private val halfH = if (h >= 0) h/2 else 1f
    private val paint = Paint()

    init {

        val typedArray = ctx.obtainStyledAttributes(attributeSet, R.styleable.ColorSlider)
        colors = typedArray.getTextArray(R.styleable.ColorSlider_colors)
            .map { Color.parseColor(it.toString()) }
            as ArrayList<Int>

        max = colors.size
        typedArray.recycle()

        colors.add(0, android.R.color.transparent)

        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        progressBackgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        thumb = ctx.getDrawable(R.drawable.ic_arrow_down)
        setPadding(paddingLeft + 10, paddingTop, paddingRight, paddingBottom + 100)
        noColorDrawable = ctx.getDrawable(R.drawable.ic_no_color)!!

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                listeners.forEach {
                    it(colors[p1])
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawTickmarks(canvas)
    }

    private fun drawTickmarks(canvas: Canvas?) {
        canvas?.let {
            val count = colors.size
            val saveCount = canvas.save()
            canvas.translate(paddingLeft.toFloat(), (height / 2).toFloat())
            for (i in 0 until count) {
                val spacing = (width - paddingLeft - paddingRight) / count

                if (i == 0) {
                    val drawableWidth = noColorDrawable.intrinsicWidth
                    val drawableHeight = noColorDrawable.intrinsicHeight
                    val dW2 = if (drawableWidth >= 0) drawableWidth/2 else 1
                    val dH2 = if (drawableHeight >= 0) drawableHeight/2 else 1

                    noColorDrawable.setBounds(-dW2, -dH2, dW2, dH2)
                    noColorDrawable.draw(canvas)

                } else {
                    paint.color = colors[i]
                    canvas.drawRect(-halfW, -halfH, halfW, halfH, paint)
                }
                canvas.translate(spacing.toFloat(), 0f)
            }
            canvas.restoreToCount(saveCount)
        }
    }

    fun addListener(listener: (Int) -> Unit) {
        listeners.add(listener)
    }

}