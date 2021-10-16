package com.example.love.database

import androidx.room.*
import com.example.love.model.TaskDB

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTasks(tasks: List<TaskDB>)

    @Query("Select * From tasks")
    suspend fun getAllTasks():List<TaskDB>
}