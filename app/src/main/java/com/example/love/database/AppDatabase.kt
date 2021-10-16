package com.example.love.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.love.model.TaskDB

@Database(entities = [TaskDB::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "tasks"

        fun invoke(context: Context) : AppDatabase {
            if(INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                }
            }
            return INSTANCE!!
        }
        fun getDatabase() = INSTANCE!!
    }
}