package com.example.love.model

import com.google.gson.annotations.SerializedName

// Модель класс
// id задачи
// задача
// ответ на задачу

data class Task(
    @SerializedName("id")
    val id: String,
    @SerializedName("task")
    val task: String,
    @SerializedName("answer")
    val answer: String
)
