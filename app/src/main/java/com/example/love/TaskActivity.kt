package com.example.love

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.love.databinding.ActivityTaskBinding
import com.example.love.Service.AlarmService
import com.example.love.ui.home.HomeFragment
import com.example.love.view_model.MainViewModel
import ru.unit6.course.android.retrofit.utils.Status

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: MainViewModel

    // Разрешено 2 попытки
    var countOfAnswer: Int = 2

    var rightAnswer: String = ""
    var userAnswer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Проверка, запущен ли ForegroundService или нет
        startOrStopAlarmService()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupObservers()

        binding.buttonOffAlarm.setOnClickListener {
            userAnswer = binding.editTextTextPersonName.text.toString().trim()
            countOfAnswer--
            when (userAnswer) {
                rightAnswer -> {
                    // Отправляем broadcastReceiver команду
                    // о выключении будильника и его удалении с экрана
                    stopService(Intent(this, AlarmService::class.java))
                    Toast.makeText(this, "Отлично!", Toast.LENGTH_SHORT).show()
                    val nextActivityMain = Intent(this, MainActivity::class.java)
                    // пользователь ответил верно -> посылаем ответ в MainActivity об отключении будильника
                    nextActivityMain.putExtra("result", "true")
                    startActivity(nextActivityMain)
                }
                "" -> {
                    Toast.makeText(this, "Введите ответ!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Отправляем broadcastReceiver команду
                    // о повторе будильника через 5 минут
                    // TODO
                    // Здесь будет реализация повторения
                    if(countOfAnswer != 0) {
                        Toast.makeText(this, "Не верно, попробуйте ещё!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        stopService(Intent(this, AlarmService::class.java))
                        Toast.makeText(this, "Плохо..", Toast.LENGTH_SHORT).show()
                        val nextActivityMain = Intent(this, MainActivity::class.java)
                        // пользователь ответил верно -> посылаем ответ в MainActivity об отключении будильника
                        nextActivityMain.putExtra("result", "false")
                        startActivity(nextActivityMain)
                    }
                }
            }
        }
    }

    private fun startOrStopAlarmService() {
        if(isMyServiceRunning(AlarmService::class.java)) {
            Toast.makeText(this, "Stop, its working now!", Toast.LENGTH_SHORT).show()
            // stopService(Intent(this, AlarmService::class.java))
        }
        else {
            Toast.makeText(this, "Start!", Toast.LENGTH_SHORT).show()
            startService(Intent(this, AlarmService::class.java))
        }
    }

    // проверка на работу сервиса
    private fun isMyServiceRunning(myService: Class<AlarmService>):Boolean {
        val manager : ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE))
            if(myService.name.equals(service.service.className)) {
                return true
            }
        return false
    }

    private fun setupObservers() {
        // Подключение к сети и запись в бд затем вызов функции с выводом данных
        viewModel.getTasks("2").observe(this){ resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { task ->
                        binding.textView.text = task.task
                        rightAnswer = task.answer
                    }
                }
                Status.ERROR -> {
                    binding.textView.text = "Ошибка"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}