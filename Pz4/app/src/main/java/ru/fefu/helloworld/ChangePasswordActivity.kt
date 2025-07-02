package ru.fefu.helloworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ChangePasswordActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChangePasswordActivity::class.java)
        }
    }

    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAccept: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        // Инициализация UI элементов
        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnAccept = findViewById(R.id.btnAccept)
        // Настройка тулбара
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        // Обработка нажатия на кнопку "Принять"
        btnAccept.setOnClickListener {
            if (validateForm()) {
                // Здесь был бы код для смены пароля
                Toast.makeText(this, "Пароль успешно изменен", Toast.LENGTH_SHORT).show()
                finish() // Возвращаемся на предыдущий экран
            }
        }
    }

    // Обработка нажатия на кнопку "Назад" в тулбаре
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Валидация формы
    private fun validateForm(): Boolean {
        val oldPassword = etOldPassword.text.toString()
        val newPassword = etNewPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Проверка на пустые поля
        if (oldPassword.isEmpty()) {
            etOldPassword.error = "Пожалуйста, введите текущий пароль"
            return false
        }

        if (newPassword.isEmpty()) {
            etNewPassword.error = "Пожалуйста, введите новый пароль"
            return false
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = "Пожалуйста, подтвердите пароль"
            return false
        }

        // Проверка на совпадение паролей
        if (newPassword != confirmPassword) {
            etConfirmPassword.error = "Пароли не совпадают"
            return false
        }

        // Проверка на минимальную длину
        if (newPassword.length < 6) {
            etNewPassword.error = "Пароль должен быть не менее 6 символов"
            return false
        }

        return true
    }
}