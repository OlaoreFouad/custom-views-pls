package dev.iamfoodie.customviewskotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var selectedColor: Int = Color.GRAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colorSelectorButton.setBackgroundColor(selectedColor)

        colorSelectorButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ColorSelectorActivity::class.java)
            intent.putExtra("color", selectedColor)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectedColor = data?.extras?.getInt("selectedColor")!!
            colorSelectorButton.setBackgroundColor(selectedColor)
        }
    }
}
