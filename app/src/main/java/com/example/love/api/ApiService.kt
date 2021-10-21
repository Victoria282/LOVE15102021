package com.example.love.api

import com.example.love.model.TaskDB
import retrofit2.http.*

interface ApiService {
    @GET("task/")
    suspend fun getAllTasks() : List<TaskDB>
}