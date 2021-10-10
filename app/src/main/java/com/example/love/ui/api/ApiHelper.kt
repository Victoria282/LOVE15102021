package com.example.love.ui.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getTask(taskId: String) = apiService.getTask(taskId)
}