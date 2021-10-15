package com.example.love.BroadcastReceiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.love.TaskActivity
import java.util.*
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.love.Constants.NOTIFICATION_CHANNEL_ID
import com.example.love.Constants.NOTIFICATION_ID
import com.example.love.MainActivity
import com.example.love.R
import com.example.love.databinding.FragmentSettingsBinding
import kotlin.properties.Delegates

class Receiver: BroadcastReceiver() {
     var flagAlarm = false

    @SuppressLint("InvalidWakeLockTag")
    override fun onReceive(context: Context?, intent: Intent?) {
        loadSettings(context)
        val pm = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG")
        //Осуществляем блокировку
        wl.acquire(10 * 60 * 1000L)
    }

    // Запуск будильника
     fun setAlarm(context1: Long, context: Context?) {
        flagAlarm = true
        System.out.println("VIKA будильник заведен -> $flagAlarm")
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(context1, pendingIntent), pendingIntent)
        save(context, flagAlarm)
    }
    // Повторение будильника
     fun repeatAlarm(context: Context?) {
        flagAlarm = true
        System.out.println("VIKA будильник на повтор -> $flagAlarm")
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 50000 /*Периодичность 3 минуты*/, pendingIntent), pendingIntent)
        save(context, flagAlarm)

    }
    // Выключение будильника
     fun cancelAlarm(context: Context?) {
        flagAlarm = false
        System.out.println("VIKA будильник отменен -> $flagAlarm")
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        save(context, flagAlarm)
        alarmManager.cancel(pendingIntent)
    }
    // Проверка на существование будильника
    fun getAlarmStatusWorking() : Boolean {
        System.out.println("VIKA $flagAlarm")
        return flagAlarm
    }
    // сохраняем состояние о наличии будильника
    private fun save(context: Context?, flag: Boolean) {
        val sharedPref = (context?.getSharedPreferences("sharedPrefFlagReceiver", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val editor = sharedPref.edit()
        editor.apply() {
            putBoolean("flag", flag).apply()
        }
    }
    private fun loadSettings(context: Context?) {
        val sharedPref: SharedPreferences = (context?.getSharedPreferences("sharedPrefFlagReceiver", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val savedFlag = sharedPref.getBoolean("flag", true)
        restoreData(savedFlag)
    }
    private fun restoreData(savedFlag: Boolean) {
        flagAlarm = savedFlag
    }
}