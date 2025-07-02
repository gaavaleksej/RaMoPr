package ru.fefu.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fefu.helloworld.data.AppDatabase
import ru.fefu.helloworld.data.ActivityRepository
import ru.fefu.helloworld.model.ActivityEntity
import ru.fefu.helloworld.model.ActivityTypeEnum
import ru.fefu.helloworld.model.Coordinate
import java.util.Date
import kotlin.random.Random

/**
 * ViewModel для управления активностями
 */
class ActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ActivityRepository
    val allActivities: LiveData<List<ActivityEntity>>

    init {
        val activityDao = AppDatabase.getDatabase(application).activityDao()
        repository = ActivityRepository(activityDao)
        allActivities = repository.allActivities
    }

    /**
     * Создание новой активности с выбранным типом (с использованием случайных данных)
     */
    fun createActivity(activityType: ActivityTypeEnum) {
        viewModelScope.launch(Dispatchers.IO) {
            // Создаем случайные данные для демонстрации
            val startTime = Date(System.currentTimeMillis() - Random.nextLong(60 * 60 * 1000)) // Начало активности от часа назад
            val endTime = Date() // Конец активности - текущее время
            
            // Генерируем случайные координаты в окрестностях Владивостока
            val vladivostokLat = 43.115536
            val vladivostokLng = 131.885485
            val coordinates = repository.generateRandomCoordinates(
                vladivostokLat,
                vladivostokLng,
                count = Random.nextInt(10, 20)
            )
            
            // Создаем и сохраняем активность
            val activity = ActivityEntity(
                activityType = activityType,
                startTime = startTime,
                endTime = endTime,
                coordinates = coordinates
            )
            
            repository.insert(activity)
        }
    }
    
    /**
     * Создание новой активности с конкретными данными
     */
    fun createActivity(
        activityType: ActivityTypeEnum, 
        startTime: Date, 
        endTime: Date, 
        coordinates: List<Coordinate>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // Создаем и сохраняем активность с предоставленными данными
            val activity = ActivityEntity(
                activityType = activityType,
                startTime = startTime,
                endTime = endTime,
                coordinates = coordinates
            )
            
            repository.insert(activity)
        }
    }
    
    fun deleteActivity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteActivity(id)
        }
    }
} 