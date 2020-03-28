package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import dev.iamfoodie.customviewskotlin.R
import kotlinx.android.synthetic.main.color_selector.view.*

class ColorSelector @JvmOverloads
    constructor(ctx: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0, defRes: Int = 0)
    : LinearLayout(ctx, attributeSet, defStyle, defRes) {

    private var colors = listOf(Color.RED, Color.YELLOW, Color.DKGRAY, Color.BLACK, Color.CYAN, Color.GREEN, Color.GRAY)
    private var selectedColorIndex = 0
    private var function: ((Int) -> Unit)? = null

    init {

        val attributes = ctx.obtainStyledAttributes(attributeSet, R.styleable.ColorSelector)

        colors = attributes.getTextArray(R.styleable.ColorSelector_colors)
            .map { Color.parseColor(it.toString()) }
        selectedColorIndex = attributes.getInteger(R.styleable.ColorSelector_defaultIndex, 0)

        attributes.recycle()

        orientation = HORIZONTAL
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)

        setColor()
        colorSelectorCheckbox.isChecked = true

        colorSelectorArrowLeft.setOnClickListener { onLeftArrowSelected() }

        colorSelectorArrowRight.setOnClickListener { onRightArrowSelected() }
    }

    private fun onLeftArrowSelected() {
        if (selectedColorIndex == 0) {
            selectedColorIndex = colors.lastIndex
        } else {
            selectedColorIndex--
        }

        setColor()
    }

    fun setOnColorSelectedListener(block: (Int) -> Unit) {
        function = block
    }

    private fun onRightArrowSelected() {
        if (selectedColorIndex == colors.lastIndex) {
            selectedColorIndex = 0
        } else {
            selectedColorIndex++
        }

        setColor()
    }

    private fun setColor() {
        colorContainer.setBackgroundColor(colors[selectedColorIndex])
        if (colorSelectorCheckbox.isChecked) {
            function?.invoke(colors[selectedColorIndex])
        }
    }

    fun setSelectedColor(color: Int) {
        selectedColorIndex = if (colors.indexOf(color) < 0) 0 else colors.indexOf(color)
        setColor()
    }

}