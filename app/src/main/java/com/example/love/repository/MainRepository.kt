package com.example.love.repository

import com.example.love.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getAllTasks() = apiHelper.getAllTasks()
}