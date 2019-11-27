package dev.iamfoodie.customviewskotlin.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.setPadding
import dev.iamfoodie.customviewskotlin.R

class WhatsappInput @JvmOverloads
    constructor(ctx: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0, defRes: Int = 0)
    : LinearLayout(ctx, attributeSet, defStyle, defRes) {

    init {
        orientation = HORIZONTAL
        background = resources.getDrawable(R.drawable.whatsapp_bf, null)
        setPadding(15)

        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.whatsapp_input, this)
    }

}