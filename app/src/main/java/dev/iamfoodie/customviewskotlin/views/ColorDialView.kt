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

    private var colors = arrayListOf<Int>(
        Color.RED, Color.YELLOW, Color.DKGRAY, Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN, Color.MAGENTA
    )

    private var dialDrawable: Drawable? = null
    private var dialDiameter = toDP(100)
    private var noColorDrawable: Drawable? = null

    private var horizontalSize = 0f
    private var verticalSize = 0f

//    Padding Values
    private var extraPadding = toDP(30).toFloat()
    private var totalLeftPadding = 0f
    private var totalRightPadding = 0f
    private var totalTopPadding = 0f
    private var totalBottomPadding = 0f

    private var paint = Paint().also {
        it.color = Color.BLUE
        it.isAntiAlias = true
    }

//    Tick size
    private var tickSize = toDP(10).toFloat()
    private var tickPositionVertical = 0f

    private var centerHorizontal = 0f
    private var centerVertical = 0f

    private var angleBetweenColors = 0f

    init {

        val typedArray = ctx.obtainStyledAttributes(attributeSet, R.styleable.ColorDialView)

        try {

            val customColors = typedArray.getTextArray(R.styleable.ColorDialView_colors)
                .map { Color.parseColor(it.toString()) }
                as ArrayList<Int>?

            customColors?.let {
                colors = customColors
            }

            dialDiameter = typedArray.getDimension(
                R.styleable.ColorDialView_dialDiameter, toDP(100).toFloat()
            ).toInt()

            tickSize = typedArray.getDimension(
                R.styleable.ColorDialView_tickRadius, toDP(10).toFloat()
            ).toFloat()

            extraPadding = typedArray.getDimension(
                R.styleable.ColorDialView_extraPadding, toDP(30).toFloat()
            )


        } finally {
            typedArray.recycle()
        }

        dialDrawable = ctx.getDrawable(R.drawable.ic_dial)
        dialDrawable?.let {
            it.bounds = getCenteredBounds(dialDiameter)
        }
        noColorDrawable = ctx.getDrawable(R.drawable.ic_no_color).also {
            it?.bounds = getCenteredBounds(tickSize.toInt(), 2)
        }
        colors[0] = android.R.color.transparent
        angleBetweenColors = 360f / colors.size

        refreshValues()
    }

    private fun getCenteredBounds(size: Int, scalar: Int = 1): Rect {
        val half = (if (size == 0) 1 else size/2) * scalar
        return Rect(-half, -half, half, half)
    }

    private fun refreshValues() {

        totalLeftPadding = paddingLeft + extraPadding
        totalRightPadding = paddingRight + extraPadding
        totalTopPadding = paddingTop + extraPadding
        totalBottomPadding = paddingBottom + extraPadding

        tickPositionVertical = paddingTop + extraPadding / 2f

        horizontalSize = totalLeftPadding + totalRightPadding + dialDiameter.toFloat()
        verticalSize = totalTopPadding + totalBottomPadding + dialDiameter.toFloat()

        centerHorizontal = totalLeftPadding + (horizontalSize - totalLeftPadding - totalRightPadding) / 2f
        centerVertical = totalTopPadding + (verticalSize - totalTopPadding - totalBottomPadding) / 2f
    }

    private fun toDP(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            ctx.resources.displayMetrics
        ).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSizeAndState(
            horizontalSize.toInt(), widthMeasureSpec, 0
        )

        val height = resolveSizeAndState(
            verticalSize.toInt(), heightMeasureSpec, 0
        )

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var saveCount = canvas.save()
        colors.forEachIndexed { i, color ->

            if (i == 0) {
                canvas.translate(centerHorizontal, tickPositionVertical)
                noColorDrawable?.draw(canvas)
                canvas.translate(-centerHorizontal, -tickPositionVertical)
            } else {
                paint.color = color
                canvas.drawCircle(centerHorizontal, tickPositionVertical, tickSize, paint)
            }
            canvas.rotate(angleBetweenColors, centerHorizontal, centerVertical)
        }
        canvas.restoreToCount(saveCount)
        canvas.translate(centerHorizontal, centerVertical)
        dialDrawable?.draw(canvas)
    }
}
