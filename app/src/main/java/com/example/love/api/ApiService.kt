package com.example.love.api

import com.example.love.model.Task
import retrofit2.http.*

interface ApiService {
    // подключение к апи к задаче
    @GET("task/{id}")
    suspend fun getTask(@Path("id") taskId: String): Task
}