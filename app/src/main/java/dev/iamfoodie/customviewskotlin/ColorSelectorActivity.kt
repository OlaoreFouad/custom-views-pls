package dev.iamfoodie.customviewskotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_color_selector.*

class ColorSelectorActivity : AppCompatActivity() {

    private var selectedColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_selector)

        selectedColor = intent.extras?.getInt("color")
        color_selector.setSelectedColor(selectedColor!!)

        color_selector.setOnColorSelectedListener { selectedColor = it }
        submit_button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selectedColor", selectedColor!!)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
