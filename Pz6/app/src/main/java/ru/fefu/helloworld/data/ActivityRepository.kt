package ru.fefu.helloworld.data

import androidx.lifecycle.LiveData
import ru.fefu.helloworld.model.ActivityEntity
import kotlin.random.Random

/**
 * Репозиторий для работы с активностями
 */
class ActivityRepository(private val activityDao: ActivityDao) {

    val allActivities: LiveData<List<ActivityEntity>> = activityDao.getAllActivities()

    suspend fun insert(activity: ActivityEntity): Long {
        return activityDao.insert(activity)
    }
    
    suspend fun getActivityById(id: Int): ActivityEntity? {
        return activityDao.getActivityById(id)
    }
    
    suspend fun deleteActivity(id: Int) {
        activityDao.deleteActivity(id)
    }
    
    // Генерация случайных координат вокруг точки
    fun generateRandomCoordinates(centerLat: Double, centerLng: Double, count: Int, radiusKm: Double = 0.1): List<ru.fefu.helloworld.model.Coordinate> {
        val coordinates = mutableListOf<ru.fefu.helloworld.model.Coordinate>()
        val earthRadius = 6371.0 // Радиус Земли в км
        
        repeat(count) {
            // Генерация случайного расстояния в пределах radiusKm
            val distance = Random.nextDouble() * radiusKm
            
            // Генерация случайного угла
            val angle = Random.nextDouble() * 2 * Math.PI
            
            // Вычисление смещения в градусах
            val latOffset = (distance / earthRadius) * (180 / Math.PI)
            val lngOffset = (distance / earthRadius) * (180 / Math.PI) / Math.cos(centerLat * Math.PI / 180)
            
            // Вычисление новых координат
            val newLat = centerLat + latOffset * Math.sin(angle)
            val newLng = centerLng + lngOffset * Math.cos(angle)
            
            coordinates.add(ru.fefu.helloworld.model.Coordinate(newLat, newLng))
        }
        
        return coordinates
    }
} 