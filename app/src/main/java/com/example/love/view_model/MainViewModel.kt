package com.example.love.view_model

import androidx.lifecycle.*
import com.example.love.api.ApiHelper
import com.example.love.api.RetrofitBuilder
import com.example.love.database.AppDatabase
import com.example.love.model.TaskDB
import kotlinx.coroutines.Dispatchers
import com.example.love.repository.MainRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.unit6.course.android.retrofit.data.utils.Resource.Companion.error
import ru.unit6.course.android.retrofit.data.utils.Resource.Companion.loading
import ru.unit6.course.android.retrofit.data.utils.Resource.Companion.success


class MainViewModel : ViewModel() {
    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: MainRepository = MainRepository(apiHelper)
    private val _localTasks = MutableLiveData<List<TaskDB>>()
    private val appDatabase = AppDatabase.getDatabase()
    // извне никто не поменяет юзера, меняем локально
    val localTasks : LiveData<List<TaskDB>>
        get() = _localTasks

    fun getTasksFromDatabase() =
    // асинхронные операции
        // записывать данные в БД и получать после
        viewModelScope.launch {
            try {
                // взяли локалЮзерс и подставили значения из метода запрашивающего данные
                // из бд
                _localTasks.postValue(appDatabase.userDao().getAllTasks())
            }catch (exception: Exception) {
                throw exception
            }
        }

    fun setAllTasksToDatabase(tasks: List<TaskDB>) =
        viewModelScope.launch {
            // переключили контекст на поток инпут аутпут
            withContext(Dispatchers.IO) {
                appDatabase.userDao().insertAllTasks(tasks)
                getTasksFromDatabase()
                // запрос инсерт, после запрос на данные пользователей
                // после инсерта в гет, в гете обновим пользователей, увидит это фрагмент
            }
        }
  fun getTasks(taskId: String) = liveData(Dispatchers.IO) {
      emit(loading(data = null))
      try {
          emit(success(data = mainRepository.getTask(taskId)))
      } catch(exception: Exception) {
          emit(error(data = null, message =  exception.message?: "Error"))
      }
  }
    fun getAllTasks() = liveData(Dispatchers.IO) {
        emit(loading(data = null))
        try {
            emit(success(data = mainRepository.getAllTasks()))
        } catch(exception: Exception) {
            emit(error(data = null, message =  exception.message?: "Error"))
        }
    }


}