package ru.fefu.helloworld.model

/**
 * Представляет тип активности (например, "Бег", "Шаги", "Велосипед")
 */
data class ActivityType(
    val id: Int,
    val name: String,        // Название активности
    val iconResId: Int,      // Идентификатор ресурса иконки
    var isSelected: Boolean = false // Флаг выделения
)
