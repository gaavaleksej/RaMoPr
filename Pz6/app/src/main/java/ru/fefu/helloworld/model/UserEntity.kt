package ru.fefu.helloworld.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность пользователя для хранения в Room базе данных
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String, // Используем уникальный идентификатор пользователя
    val name: String, // Имя пользователя
    val username: String, // Имя или никнейм пользователя для отображения
    val password: String, // Хэшированный пароль пользователя
    val email: String = "", // Email пользователя (опционально)
    val profileImageUrl: String = "", // URL изображения профиля (опционально)
    val isCurrentUser: Boolean = false // Флаг, указывающий, является ли пользователь текущим
) 