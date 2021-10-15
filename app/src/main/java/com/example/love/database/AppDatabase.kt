package com.example.love.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.love.model.TaskDB

// version текущей ДБ
@Database(entities = [TaskDB::class],version = 1)

abstract class AppDatabase : RoomDatabase() {
    // запросы которые будем выполнять
    abstract fun userDao() : UserDao

    companion object {
        // нал если база данных еще не создана
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "tasks"

        // при создании DB необходим контекст прил-я (БД создается)
        // в рамках контекста
        fun invoke(context: Context) : AppDatabase {
            if(INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    // экземпляр даты байз
                    INSTANCE = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    // собирает БД
                }
            }
            // уверены что объект создан
            return INSTANCE!!
        }
        fun getDatabase() = INSTANCE!!
    }
}