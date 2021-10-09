package com.example.love.repository

import androidx.lifecycle.LiveData
import com.example.love.ui.api.ApiHelper
import com.example.love.ui.api.RetrofitBuilder
import retrofit2.Response

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getTask(taskId: String) = apiHelper.getTask(taskId)
}