package ru.fefu.helloworld.data

import androidx.lifecycle.LiveData
import ru.fefu.helloworld.model.UserEntity
import java.util.UUID

/**
 * Репозиторий для работы с пользователями
 */
class UserRepository(private val userDao: UserDao) {

    fun observeCurrentUser(): LiveData<UserEntity?> {
        return userDao.observeCurrentUser()
    }
    
    suspend fun getCurrentUser(): UserEntity? {
        return userDao.getCurrentUser()
    }
    
    suspend fun registerUser(name: String, username: String, password: String): UserEntity {
        val userId = UUID.randomUUID().toString()
        val user = UserEntity(
            id = userId,
            name = name,
            username = username,
            password = password, // В реальном приложении должен быть хэш
            isCurrentUser = true
        )
        
        // Перед созданием нового текущего пользователя, сбрасываем флаг у существующих
        userDao.clearCurrentUser()
        userDao.insert(user)
        return user
    }
    
    suspend fun loginUser(username: String, password: String): UserEntity? {
        val user = userDao.getUserByUsername(username)
        if (user != null && user.password == password) { // В реальном приложении сравнивали бы хэши
            // Перед установкой нового текущего пользователя, сбрасываем флаг у существующих
            userDao.clearCurrentUser()
            userDao.setCurrentUser(user.id)
            return userDao.getUserById(user.id)
        }
        return null
    }
    
    suspend fun updateUserProfile(userId: String, name: String, username: String): Boolean {
        val currentUser = userDao.getUserById(userId)
        if (currentUser != null) {
            val updatedUser = currentUser.copy(
                name = name,
                username = username
            )
            userDao.update(updatedUser)
            return true
        }
        return false
    }
    
    suspend fun changePassword(userId: String, oldPassword: String, newPassword: String): Boolean {
        val user = userDao.getUserById(userId)
        if (user != null && user.password == oldPassword) { // В реальном приложении сравнивали бы хэши
            userDao.updatePassword(userId, newPassword) // В реальном приложении сохраняли бы хэш
            return true
        }
        return false
    }
    
    suspend fun logout() {
        userDao.clearCurrentUser()
    }
} 