package com.example.love.database

import androidx.room.*
import com.example.love.model.TaskDB

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // вставка в БД из сети
    suspend fun insertAllTasks(tasks: List<TaskDB>)

    //  получаем все таски из БД
    @Query("Select * From tasks")
    suspend fun getAllTasks():List<TaskDB>
}