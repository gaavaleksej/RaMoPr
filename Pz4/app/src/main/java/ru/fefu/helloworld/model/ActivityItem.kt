package ru.fefu.helloworld.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Базовый класс для различных типов элементов в RecyclerView
 */
sealed class ActivityItem {
    companion object {
        const val VIEW_TYPE_DATE_HEADER = 0
        const val VIEW_TYPE_ACTIVITY = 1
    }
}

/**
 * Элемент-заголовок с датой (например, "Вчера" или "Май 2022 года")
 */
data class DateHeaderItem(
    val date: String
) : ActivityItem()

/**
 * Элемент активности пользователя
 */
@Parcelize
data class ActivityData(
    val distance: String,         // Например, "14.32 км" или "1000 м"
    val duration: String,         // Например, "2 часа 46 минут" или "60 минут"
    val activityType: String,     // Например, "Серфинг" или "Велосипед"
    val timeAgo: String,          // Например, "14 часов назад"
    val date: String,             // Используется для группировки по дате
    val username: String = "",    // Имя пользователя (только для OthersActivityFragment)
    val startTime: String = "14:49",
    val finishTime: String = "16:31"
) : ActivityItem(), Parcelable
