package com.example.love

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.love.databinding.ActivityMainBinding
import com.example.love.databinding.ActivityTaskBinding
import com.example.love.view_model.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.unit6.course.android.retrofit.utils.Status

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: MainViewModel

    var rightAnswer: String = ""
    var userAnswer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupObservers()

        binding.buttonOffAlarm.setOnClickListener {
            userAnswer = binding.editTextTextPersonName.text.toString().trim()
            when (userAnswer) {
                rightAnswer -> {
                    // Отправляем broadcastReceiver команду
                    // о выключении будильника и его удалении с экрана
                    Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show()
                }
                "" -> {
                    Toast.makeText(this, "Введите ответ!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Отправляем broadcastReceiver команду
                    // о повторе будильника через 5 минут
                    Toast.makeText(this, "Bad", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupObservers() {
        // Подключение к сети и запись в бд затем вызов функции с выводом данных
        viewModel.getTasks("4").observe(this){ resource ->
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

}