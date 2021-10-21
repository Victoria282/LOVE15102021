package com.example.love.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getAllTasks() = apiService.getAllTasks()
}