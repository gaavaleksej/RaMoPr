package ru.fefu.helloworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ProfileFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etUsername: EditText
    private lateinit var tvChangePassword: TextView
    private lateinit var btnSave: Button
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        // Инициализация UI элементов
        etName = view.findViewById(R.id.etName)
        etUsername = view.findViewById(R.id.etUsername)
        tvChangePassword = view.findViewById(R.id.tvChangePassword)
        btnSave = view.findViewById(R.id.btnSave)
        btnLogout = view.findViewById(R.id.btnLogout)
        // Установка обработчика для изменения пароля
        tvChangePassword.setOnClickListener {
            openChangePasswordScreen()
        }
        // Установка обработчика для кнопки "Сохранить"
        btnSave.setOnClickListener {
            saveProfile()
        }
        // Установка обработчика для кнопки "Выйти"
        btnLogout.setOnClickListener {
            logout()
        }

        return view
    }

    // Метод для открытия экрана изменения пароля
    private fun openChangePasswordScreen() {
        val intent = ChangePasswordActivity.newIntent(requireContext())
        startActivity(intent)
    }
    // Метод для сохранения профиля
    private fun saveProfile() {
        val name = etName.text.toString().trim()
        val username = etUsername.text.toString().trim()

        // Проверка валидности введенных данных
        if (name.isEmpty()) {
            etName.error = "Пожалуйста, введите имя"
            return
        }

        if (username.isEmpty()) {
            etUsername.error = "Пожалуйста, введите никнейм"
            return
        }

        // Здесь был бы код для сохранения профиля на сервере
        
        // Показываем сообщение об успешном сохранении
        Toast.makeText(context, "Профиль успешно сохранен", Toast.LENGTH_SHORT).show()
    }
    // Метод для выхода из аккаунта
    private fun logout() {
        // Здесь был бы код для выхода из аккаунта
        
        // Показываем сообщение об успешном выходе
        Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
        
        // Возвращаемся на экран авторизации или стартовый экран
        // Например:
        // val intent = Intent(requireContext(), AuthActivity::class.java)
        // startActivity(intent)
        // requireActivity().finish()
    }
}