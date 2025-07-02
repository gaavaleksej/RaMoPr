package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.fefu.helloworld.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private lateinit var etName: EditText
    private lateinit var etUsername: EditText
    private lateinit var tvChangePassword: TextView
    private lateinit var btnSave: Button
    private lateinit var btnLogout: Button
    private lateinit var userViewModel: UserViewModel

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
        // Инициализация ViewModel
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        // Наблюдаем за изменениями данных пользователя
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // Заполняем поля данными пользователя
                etName.setText(user.name)
                etUsername.setText(user.username)
            }
        }

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

        // Сохраняем данные через ViewModel
        userViewModel.updateProfile(name, username) { success ->
            if (success) {
                Toast.makeText(context, "Профиль успешно сохранен", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ошибка при сохранении профиля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Метод для выхода из аккаунта
    private fun logout() {
        userViewModel.logout {
            // Переходим на экран авторизации
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}