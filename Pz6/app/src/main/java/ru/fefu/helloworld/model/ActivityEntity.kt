package ru.fefu.helloworld.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * Сущность активности для хранения в Room
 */
@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val activityType: ActivityTypeEnum,
    val startTime: Date,
    val endTime: Date,
    val coordinates: List<Coordinate>
)

/**
 * Класс для хранения координат
 */
data class Coordinate(
    val latitude: Double,
    val longitude: Double
)

/**
 * Класс конвертеров для Room
 */
class Converters {
    // Конвертеры для ActivityTypeEnum
    @TypeConverter
    fun fromActivityType(value: ActivityTypeEnum): String {
        return value.name
    }

    @TypeConverter
    fun toActivityType(value: String): ActivityTypeEnum {
        return try {
            ActivityTypeEnum.valueOf(value)
        } catch (e: IllegalArgumentException) {
            ActivityTypeEnum.WALKING
        }
    }

    // Конвертеры для Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Конвертеры для списка координат
    @TypeConverter
    fun fromCoordinatesList(value: List<Coordinate>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Coordinate>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCoordinatesList(value: String): List<Coordinate> {
        val gson = Gson()
        val type = object : TypeToken<List<Coordinate>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
} 