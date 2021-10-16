package com.example.love.api

import com.example.love.model.Task
import com.example.love.model.TaskDB
import retrofit2.http.*

interface ApiService {
    // получение задачи по id
    @GET("task/{id}")
    suspend fun getTask(@Path("id") taskId: String): Task

    // получение всех задач
    @GET("task/")
    suspend fun getAllTasks() : List<TaskDB>

}