package ru.fefu.helloworld.model

/**
 * Класс модели для элемента истории активности
 */
data class ActivityHistoryItem(
    var title: String,
    var subtitle: String,
    var iconResId: Int = 0 // Значение по умолчанию для иконки
) 