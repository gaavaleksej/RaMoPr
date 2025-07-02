package ru.fefu.helloworld.util

import ru.fefu.helloworld.R

object ActivityIcons {
    // Функция для получения идентификатора ресурса иконки на основе типа активности
    fun getIconForActivityType(activityType: String): Int {
        return when (activityType.lowercase()) {
            "серфинг" -> R.drawable.sports
            "велосипед" -> R.drawable.sports
            "качели" -> R.drawable.sports
            "езда на кадилак" -> R.drawable.sports
            else -> R.drawable.sports  // Иконка по умолчанию
        }
    }
} 