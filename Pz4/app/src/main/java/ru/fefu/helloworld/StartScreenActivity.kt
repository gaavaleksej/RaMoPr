package ru.fefu.helloworld

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityStartscreenBinding


class StartScreenActivity : AppCompatActivity(R.layout.activity_startscreen) {
    lateinit var binding: ActivityStartscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        binding = ActivityStartscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val haveAnAccountText = binding.haveAnAccount
        val startScreenBtn = binding.registerBtn

        startScreenBtn.setOnClickListener {
            val intent = Intent(this@StartScreenActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        // Создаем SpannableString
        val fullText = "Уже есть аккаунт?"
        val spannableString = SpannableString(fullText)
        // Определяем, какую часть текста сделать кликабельной
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Действие при клике
                val intent = Intent(this@StartScreenActivity, AuthActivity::class.java)
                // Запускаем активность
                startActivity(intent)
            }

        }
        // Применяем ClickableSpan к части текста
        val start = 0 // Начало кликабельной части
        val end = haveAnAccountText.text.length   // Конец кликабельной части
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        // Устанавливаем текст в TextView
        haveAnAccountText.text = spannableString
        // Включаем возможность клика по тексту
        haveAnAccountText.movementMethod = LinkMovementMethod.getInstance()

        
        

    }

}