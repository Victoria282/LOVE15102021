package com.example.love.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getTask(taskId: String) = apiService.getTask(taskId)
    suspend fun getAllTasks() = apiService.getAllTasks()
}