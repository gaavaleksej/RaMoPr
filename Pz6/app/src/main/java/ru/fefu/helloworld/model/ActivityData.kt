package ru.fefu.helloworld.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс для передачи данных об активности между компонентами приложения
 */
@Parcelize
data class ActivityDataModel(
    val activityType: String,  // Тип активности (Бег, Ходьба, Велосипед и т.д.)
    val distance: String,      // Пройденное расстояние, уже отформатированное (5.2 км)
    val timeAgo: String,       // Время с момента активности (2 часа назад)
    val duration: String,      // Длительность активности (45 мин)
    val startTime: String,     // Время начала (10:30)
    val finishTime: String,    // Время завершения (11:15)
    val comment: String = "",  // Комментарий к активности
    val isMyActivity: Boolean = true // Флаг: моя активность или других пользователей
) : Parcelable 