package ru.fefu.helloworld.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Базовый класс для различных типов элементов в RecyclerView
sealed class ActivityItem {
    // Константы для типов представлений
    companion object {
        const val VIEW_TYPE_DATE_HEADER = 0
        const val VIEW_TYPE_ACTIVITY = 1
    }
}

// Элемент-заголовок с датой
data class DateHeaderItem(
    val date: String  // Например, "Вчера" или "Май 2022 года"
) : ActivityItem()

// Элемент активности
@Parcelize
data class ActivityData(
    val distance: String,
    val duration: String,
    val activityType: String,
    val timeAgo: String,
    val date: String,
    val username: String = "",
    val startTime: String = "14:49",
    val finishTime: String = "16:31"
) : ActivityItem(), Parcelable 