package ru.fefu.helloworld

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegisterActivity : AppCompatActivity(R.layout.activity_register) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.let { win ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                win.statusBarColor = Color.WHITE
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                win.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
                win.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}
