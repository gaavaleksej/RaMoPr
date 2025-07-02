package ru.fefu.helloworld

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityStartscreenBinding

class StartScreenActivity : AppCompatActivity(R.layout.activity_startscreen) {

    private lateinit var binding: ActivityStartscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.let { win ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        binding = ActivityStartscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRegisterButton()
        setupClickableAuthLink()
    }

    private fun setupRegisterButton() {
        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setupClickableAuthLink() {
        val text = "Уже есть аккаунт?"
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@StartScreenActivity, AuthActivity::class.java))
            }
        }

        spannableString.setSpan(
            clickableSpan,
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        with(binding.haveAnAccount) {
            this.text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
