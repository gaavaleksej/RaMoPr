package ru.fefu.helloworld.util

import ru.fefu.helloworld.model.ActivityData
import ru.fefu.helloworld.model.ActivityItem
import ru.fefu.helloworld.model.DateHeaderItem

object ActivityDataHelper {

    // Получить данные для MyActivityFragment
    fun getMyActivities(): List<ActivityItem> {
        val result = mutableListOf<ActivityItem>()
        
        // Вчера
        result.add(DateHeaderItem("Вчера"))
        result.add(
            ActivityData(
                distance = "14.32 км",
                duration = "2 часа 46 минут",
                activityType = "Серфинг",
                timeAgo = "14 часов назад",
                date = "Вчера",
                startTime = "14:49",
                finishTime = "16:31"
            )
        )
        
        // Май 2022
        result.add(DateHeaderItem("Май 2022 года"))
        result.add(
            ActivityData(
                distance = "1 000 м",
                duration = "60 минут",
                activityType = "Велосипед",
                timeAgo = "29.05.2022",
                date = "Май 2022 года",
                startTime = "10:00",
                finishTime = "11:00"
            )
        )
        
        return result
    }
    
    // Получить данные для OthersActivityFragment
    fun getOthersActivities(): List<ActivityItem> {
        val result = mutableListOf<ActivityItem>()
        
        // Вчера
        result.add(DateHeaderItem("Вчера"))
        result.add(
            ActivityData(
                distance = "14.32 км",
                duration = "2 часа 46 минут",
                activityType = "Серфинг",
                timeAgo = "14 часов назад",
                date = "Вчера",
                username = "ivan_darkholme",
                startTime = "09:15",
                finishTime = "12:01"
            )
        )
        
        result.add(
            ActivityData(
                distance = "228 м",
                duration = "14 часов 48 минут",
                activityType = "Качели",
                timeAgo = "14 часов назад",
                date = "Вчера",
                username = "techniquepasha",
                startTime = "06:00",
                finishTime = "20:48"
            )
        )
        
        result.add(
            ActivityData(
                distance = "10 км",
                duration = "1 час 10 минут",
                activityType = "Езда на кадилак",
                timeAgo = "14 часов назад",
                date = "Вчера",
                username = "morgen_shtern",
                startTime = "14:30",
                finishTime = "15:40"
            )
        )
        
        return result
    }
} 