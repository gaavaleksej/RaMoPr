package ru.fefu.helloworld.model

/**
 * Класс, представляющий тип активности
 */
data class ActivityType(
    val id: Int,
    val name: String, // Например, "Велосипед", "Бег", "Шаг"
    val iconResId: Int, // Идентификатор ресурса для иконки
    var isSelected: Boolean = false // Выбран ли этот тип активности
) 