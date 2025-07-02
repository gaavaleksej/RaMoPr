package ru.fefu.helloworld.util

import ru.fefu.helloworld.model.ActivityItem
import ru.fefu.helloworld.model.DateHeaderItem
import ru.fefu.helloworld.model.ActivityDataModel // Импортируем переименованный класс
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Вспомогательный класс для работы с данными активностей
 */
object ActivityDataHelper {

    // Получить данные для MyActivityFragment
    fun getMyActivities(): List<ActivityItem> {
        val result = mutableListOf<ActivityItem>()
        
        // Вчера
        result.add(DateHeaderItem("Вчера"))
        
        // Создаем экземпляр класса ActivityData из ActivityItem.kt
        val itemData1 = ru.fefu.helloworld.model.ActivityData(
            distance = "14.32 км",
            duration = "2 часа 46 минут",
            activityType = "Серфинг",
            timeAgo = "14 часов назад",
            date = "Вчера", // Используем поле date, так как это класс из ActivityItem.kt
            username = "", // Используем поле username, так как это класс из ActivityItem.kt
            startTime = "14:49",
            finishTime = "16:31"
        )
        result.add(itemData1)
        
        // Май 2022
        result.add(DateHeaderItem("Май 2022 года"))
        
        // Создаем экземпляр класса ActivityData из ActivityItem.kt
        val itemData2 = ru.fefu.helloworld.model.ActivityData(
            distance = "1 000 м",
            duration = "60 минут",
            activityType = "Велосипед",
            timeAgo = "29.05.2022",
            date = "Май 2022 года", // Используем поле date, так как это класс из ActivityItem.kt
            username = "", // Используем поле username, так как это класс из ActivityItem.kt
            startTime = "10:00",
            finishTime = "11:00"
        )
        result.add(itemData2)
        
        return result
    }
    
    // Получить данные для OthersActivityFragment
    fun getOthersActivities(): List<ActivityDataModel> {
        val activities = mutableListOf<ActivityDataModel>()
        
        // Добавляем активности за сегодня
        activities.add(
            ActivityDataModel(
                activityType = "Бег",
                distance = "5.8 км",
                timeAgo = "2 часа назад",
                duration = "32 мин",
                startTime = "08:15",
                finishTime = "08:47",
                comment = "Утренняя пробежка в парке",
                isMyActivity = false
            )
        )
        
        activities.add(
            ActivityDataModel(
                activityType = "Велосипед",
                distance = "18.3 км",
                timeAgo = "5 часов назад",
                duration = "1 ч 15 мин",
                startTime = "06:30",
                finishTime = "07:45",
                comment = "Поездка на работу через парк",
                isMyActivity = false
            )
        )
        
        // Добавляем активности за вчера
        activities.add(
            ActivityDataModel(
                activityType = "Плавание",
                distance = "1.5 км",
                timeAgo = "1 день назад",
                duration = "45 мин",
                startTime = "18:00",
                finishTime = "18:45",
                comment = "Тренировка в бассейне",
                isMyActivity = false
            )
        )
        
        activities.add(
            ActivityDataModel(
                activityType = "Ходьба",
                distance = "4.2 км",
                timeAgo = "1 день назад",
                duration = "50 мин",
                startTime = "12:30",
                finishTime = "13:20",
                comment = "Прогулка в обеденный перерыв",
                isMyActivity = false
            )
        )
        
        // Добавляем активности за прошлую неделю
        activities.add(
            ActivityDataModel(
                activityType = "Тренировка",
                distance = "0 км",
                timeAgo = "5 дней назад",
                duration = "1 ч 30 мин",
                startTime = "17:00",
                finishTime = "18:30",
                comment = "Силовая тренировка в тренажерном зале",
                isMyActivity = false
            )
        )
        
        activities.add(
            ActivityDataModel(
                activityType = "Бег",
                distance = "10.5 км",
                timeAgo = "6 дней назад",
                duration = "55 мин",
                startTime = "07:00",
                finishTime = "07:55",
                comment = "Длинная пробежка перед работой",
                isMyActivity = false
            )
        )
        
        return activities
    }
    
    // Форматирует время в формате HH:mm
    fun formatTime(time: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(time))
    }
    
    // Расчет времени прошедшего с момента активности
    fun getTimeAgo(timeInMillis: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timeInMillis
        
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> "$days ${pluralize(days.toInt(), "день", "дня", "дней")} назад"
            hours > 0 -> "$hours ${pluralize(hours.toInt(), "час", "часа", "часов")} назад"
            minutes > 0 -> "$minutes ${pluralize(minutes.toInt(), "минута", "минуты", "минут")} назад"
            else -> "только что"
        }
    }
    
    // Вспомогательная функция для правильного склонения слов
    private fun pluralize(count: Int, one: String, few: String, many: String): String {
        val mod10 = count % 10
        val mod100 = count % 100
        
        return when {
            mod100 in 11..19 -> many
            mod10 == 1 -> one
            mod10 in 2..4 -> few
            else -> many
        }
    }
} 