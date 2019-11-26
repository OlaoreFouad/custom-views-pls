package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import dev.iamfoodie.customviewskotlin.R
import kotlinx.android.synthetic.main.color_selector.view.*

class ColorSelector @JvmOverloads
    constructor(ctx: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0, defRes: Int = 0)
    : LinearLayout(ctx, attributeSet, defStyle, defRes) {

    private val colors = listOf(Color.RED, Color.YELLOW, Color.DKGRAY, Color.BLACK, Color.CYAN, Color.GREEN)
    private var selectedColorIndex = 0

    init {
        orientation = HORIZONTAL
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)

        setColor()

        colorSelectorArrowLeft.setOnClickListener {
            onLeftArrowSelected()
        }

        colorSelectorArrowRight.setOnClickListener {
            onRightArrowSelected()
        }

    }

    fun onLeftArrowSelected() {
        if (selectedColorIndex == 0) {
            selectedColorIndex = colors.lastIndex
        } else {
            selectedColorIndex--
        }

        setColor()
    }

    fun onRightArrowSelected() {
        if (selectedColorIndex == colors.lastIndex) {
            selectedColorIndex = 0
        } else {
            selectedColorIndex++
        }

        setColor()
    }

    fun setColor() {
        colorContainer.setBackgroundColor(colors[selectedColorIndex])
    }

}