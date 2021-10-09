package com.example.love.view_model

import androidx.lifecycle.*
import com.example.love.ui.api.ApiHelper
import com.example.love.ui.api.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import com.example.love.repository.MainRepository
import ru.unit6.course.android.retrofit.data.utils.Resource.Companion.error
import ru.unit6.course.android.retrofit.data.utils.Resource.Companion.loading
import ru.unit6.course.android.retrofit.data.utils.Resource.Companion.success


class MainViewModel : ViewModel() {
    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: MainRepository = MainRepository(apiHelper)

  fun getTasks(taskId: String) = liveData(Dispatchers.IO) {
      emit(loading(data = null))
      try {
          emit(success(data = mainRepository.getTask(taskId)))
      } catch(exception: Exception) {
          emit(error(data = null, message =  exception.message?: "Error"))
      }
  }
}