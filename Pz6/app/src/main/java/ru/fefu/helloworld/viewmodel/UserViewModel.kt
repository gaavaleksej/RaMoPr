package ru.fefu.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fefu.helloworld.data.AppDatabase
import ru.fefu.helloworld.data.UserRepository
import ru.fefu.helloworld.model.UserEntity

/**
 * ViewModel для управления данными пользователя
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val currentUser: LiveData<UserEntity?>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        currentUser = repository.observeCurrentUser()
    }

    /**
     * Регистрация нового пользователя
     */
    fun registerUser(name: String, username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.registerUser(name, username, password)
                launch(Dispatchers.Main) {
                    callback(true)
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    /**
     * Вход пользователя в систему
     */
    fun loginUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.loginUser(username, password)
            launch(Dispatchers.Main) {
                callback(user != null)
            }
        }
    }

    /**
     * Обновление профиля пользователя
     */
    fun updateProfile(name: String, username: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = repository.getCurrentUser()
            if (currentUser != null) {
                val success = repository.updateUserProfile(currentUser.id, name, username)
                launch(Dispatchers.Main) {
                    callback(success)
                }
            } else {
                launch(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    /**
     * Изменение пароля пользователя
     */
    fun changePassword(oldPassword: String, newPassword: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = repository.getCurrentUser()
            if (currentUser != null) {
                val success = repository.changePassword(currentUser.id, oldPassword, newPassword)
                launch(Dispatchers.Main) {
                    callback(success)
                }
            } else {
                launch(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    /**
     * Выход пользователя из системы
     */
    fun logout(callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.logout()
            launch(Dispatchers.Main) {
                callback()
            }
        }
    }
} 