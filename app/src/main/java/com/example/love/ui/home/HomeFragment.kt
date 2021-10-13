package com.example.love.ui.home

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.love.R
import com.example.love.TaskActivity
import com.example.love.databinding.FragmentHomeBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    // аргумент из настроек
    private val args: HomeFragmentArgs by navArgs()
    private val calendar: Calendar = Calendar.getInstance()

    // Дата и время установленного будильника
    var dateAlarm = " "
    var timeAlarm = " "

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // загрузить настройки уже установленного будильника и тему из Preference
        loadSettingsTheme()
        loadAlarm()
        // нажатие на "Установить будильник"
        binding.buttonSetAlarm.setOnClickListener {
            // открытие календаря
            setAlarmDate()
        }
        // Удаление при нажатии на checkBox
        binding.statusAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                // Будильник заведен
            }
            else {
                // Диалог с пользователем
                setDialogDeleteAlarm()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(args.back == "true") {
            setSettingsTheme(args.back.toBoolean())
        }
        if(args.back == "false") {
            setSettingsTheme(args.back.toBoolean())
        }
    }

    // Диалог удаления будильника
    private fun setDialogDeleteAlarm() {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle("Удалить будильник")
            setIcon(R.drawable.half_moon)
            setMessage("Вы уверены?")
            setPositiveButton("Да"){ dialog, which ->
                // удаляем
                save("", "", false, -1)
                binding.cardViewActiveAlarm.visibility = View.GONE
            }
            setNegativeButton("Отмена"){ dialog,which ->
                binding.statusAlarm.isChecked = true
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setAlarmDate() {
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        context?.let { DatePickerDialog(it, DatePickerDialog.OnDateSetListener
        { _, y, m, dM ->
                year = y
                month = m + 1
                dayOfMonth = dM
                // Результирующая дата
                dateAlarm = "$dayOfMonth.$month.$year"
                // Вызов часов
                setAlarmTime()
        }, year, month, dayOfMonth)
        }?.show()
    }

    // Показываем пользователю часы для установки будильника
    private fun  setAlarmTime() {
        val picker = MaterialTimePicker
            .Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Выберите время для будильника")
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()

        picker.addOnPositiveButtonClickListener {
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.MINUTE, picker.minute)
            calendar.set(Calendar.HOUR_OF_DAY, picker.hour)

           // val intent = Intent(context, Receiver::class.java)

            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, getAlarmInfoPendingIntent()), getAlarmInfoPendingIntent())

            val timeAlarmFormat = SimpleDateFormat("HH:mm")
            val resultTime: String = timeAlarmFormat.format(calendar.time).toString()
            timeAlarm = resultTime
            // Устанавливаем дату и время на экране приложения
            setAlarmCard(dateAlarm, timeAlarm)
        }
        fragmentManager?.let { it1 -> picker.show(it1, "tag") }
    }

    // переход в окно Задачи при запуске будильника
    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val intent = Intent(context, TaskActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    // Показываем карточку пользователю с установленным временем и датой для будильника
    private fun setAlarmCard(dateAlarm: String, timeAlarm: String) {
        binding.cardViewActiveAlarm.visibility = View.VISIBLE
        binding.timeAlarm.text = timeAlarm
        binding.dateAlarm.text = dateAlarm
        binding.statusAlarm.isChecked = true
        save(timeAlarm, dateAlarm, binding.statusAlarm.isChecked, binding.cardViewActiveAlarm.visibility)
    }

    // загрузить данные об уже установленном будильнике
    private fun loadAlarm() {
        val sharedPref: SharedPreferences = (context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val savedTime = sharedPref.getString("timeAlarm", "")
        val savedDate = sharedPref.getString("dateAlarm", "")
        val savedVisibility = sharedPref.getInt("visibility", -1)
        val savedStatusSwitch = sharedPref.getBoolean("checked", false)
        restoreData(savedTime, savedDate, savedVisibility, savedStatusSwitch)
    }

    // запомнить данные об установленном будильнике
    private fun save(timeAlarm: String, dateAlarm: String, checked: Boolean, visibility: Int) {
        val sharedPref: SharedPreferences = (context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val editor = sharedPref.edit()
        editor.apply() {
            putString("timeAlarm", timeAlarm).apply()
            putString("dateAlarm", dateAlarm).apply()
            putBoolean("checked", checked).apply()
            putInt("visibility", visibility).apply()
        }
        // Intent(context, Receiver::class.java)
    }
    // восстанавливаем данные из Preferences (если будильник уже был установлен)
    private fun restoreData(savedTime: String?, savedDate: String?, savedVisibility: Int, savedStatusSwitch: Boolean) {
        binding.timeAlarm.text = savedTime
        binding.dateAlarm.text = savedDate
        binding.cardViewActiveAlarm.visibility = savedVisibility
        binding.statusAlarm.isChecked = savedStatusSwitch
    }

    private fun setSettingsTheme(theme: Boolean) {
        saveTheme(theme)
    }

    private fun loadSettingsTheme() {
        val sharedPref: SharedPreferences = (context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val savedTheme = sharedPref.getBoolean("theme", false)
        restoreTheme(savedTheme)
    }

    private fun saveTheme(theme: Boolean) {
        val sharedPref: SharedPreferences = (context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val editor = sharedPref.edit()
        editor.apply() {
            putBoolean("theme", theme).apply()
        }
        setThemeApp(theme)
    }
    private fun restoreTheme(savedTheme: Boolean) {
        setThemeApp(savedTheme)
    }

    private fun setThemeApp(theme: Boolean) {
        if(theme) {
            binding.homeContainer.setBackgroundResource(R.drawable.light_gradient_theme)
        } else {
            binding.homeContainer.setBackgroundResource(R.drawable.dark_gradient_theme)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}