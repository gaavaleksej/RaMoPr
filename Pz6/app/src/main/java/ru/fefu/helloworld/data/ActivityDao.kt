package ru.fefu.helloworld.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fefu.helloworld.model.ActivityEntity

/**
 * DAO интерфейс для работы с активностями в Room
 */
@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity): Long

    @Query("SELECT * FROM activities ORDER BY startTime DESC")
    fun getAllActivities(): LiveData<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getActivityById(id: Int): ActivityEntity?

    @Query("DELETE FROM activities WHERE id = :id")
    suspend fun deleteActivity(id: Int)
} 