package com.example.love.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// name DB
@Entity(tableName = "tasks")

data class TaskDB(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name = "task")
    val task:String,
    @ColumnInfo(name = "answer")
    val answer:String
)