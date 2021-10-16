package com.example.love.BroadcastReceiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import com.example.love.TaskActivity
import com.example.love.MainActivity
import com.example.love.R
import androidx.navigation.NavDeepLinkBuilder

class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       // loadSettings(context)
    }
    // Запуск будильника
    @SuppressLint("InvalidWakeLockTag")
    fun setAlarm(contextTime: Long, context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(contextTime, pendingIntent), pendingIntent)
       // save(context, flagAlarm)
    }
    // Повторение будильника
     fun repeatAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + 50000, pendingIntent),
            pendingIntent
        )
        //save(context, flagAlarm)
    }
    // Выключение будильника
     fun cancelAlarm(context: Context?) {
        val argAlarm = Bundle()
        argAlarm.putString("statusAlarm", "test")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.navigation_home)
            .setArguments(argAlarm)
            .createPendingIntent()
        // val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        //save(context, flagAlarm)
        alarmManager.cancel(pendingIntent)
    }
    // Проверка на существование будильника
    // сохраняем состояние о наличии будильника
 /*   private fun save(context: Context?, flag: Boolean) {
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
     fun restoreData(savedFlag: Boolean) {
        flagAlarm = savedFlag
    }*/
}