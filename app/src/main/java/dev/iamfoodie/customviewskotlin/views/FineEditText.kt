package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import dev.iamfoodie.customviewskotlin.R
import kotlinx.android.synthetic.main.fine_edit_text.view.*

@RequiresApi(Build.VERSION_CODES.O)
class FineEditText @JvmOverloads
    constructor(ctx: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0, defRes: Int = 0)
    : LinearLayout(ctx, attributeSet, defStyle, defRes) {

    var focused = false
    val defaultColor = Color.GRAY

    init {
        orientation = VERTICAL
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.fine_edit_text, this)

        fineFocusedIndicator.setBackgroundColor(defaultColor)
        fineInput.isFocusedByDefault = false

        fineInput.setOnFocusChangeListener { _, b ->
            if (focused != b) {
                focused = b
                effectFocus(focused)
            }
        }

    }

    fun effectFocus(f: Boolean) {
        if (f) {
            fineFocusedIndicator.setBackgroundColor(Color.BLACK)
        } else {
            fineFocusedIndicator.setBackgroundColor(defaultColor)
        }
    }

}