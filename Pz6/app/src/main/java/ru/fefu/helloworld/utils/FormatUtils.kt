package ru.fefu.helloworld.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

/**
 * Форматирует дату в строку вида "dd.MM.yyyy HH:mm"
 */
fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

/**
 * Форматирует длительность в миллисекундах в читабельный вид: "X ч Y мин" или "X мин"
 */
fun formatDuration(durationMs: Long): String {
    val minutes = (durationMs / 60000).toInt()
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    
    return when {
        hours > 0 -> "$hours ч $remainingMinutes мин"
        else -> "$minutes мин"
    }
}

/**
 * Форматирует расстояние в метрах в строку вида "X.X км" или "X м"
 */
fun formatDistance(distanceMeters: Double): String {
    return when {
        distanceMeters >= 1000 -> {
            val km = distanceMeters / 1000.0
            String.format("%.1f км", km)
        }
        else -> {
            "${distanceMeters.roundToInt()} м"
        }
    }
}

/**
 * Расширение для преобразования строки к формату "Заглавная буква в начале"
 */
fun String.capitalize(locale: Locale): String {
    return if (this.isEmpty()) "" else this[0].uppercaseChar() + this.substring(1)
} 