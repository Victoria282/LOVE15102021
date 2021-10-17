package com.example.love.BroadcastReceiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.*
import com.example.love.TaskActivity
import com.example.love.MainActivity

class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
    }

    // Запуск будильника
    fun setAlarm(contextTime: Long, context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(contextTime, pendingIntent), pendingIntent)
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
    }
    // Выключение будильника
     fun cancelAlarm(context: Context?) {
        val argAlarm = Bundle()
        argAlarm.putString("statusAlarm", "test")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        /* val pendingIntent = NavDeepLinkBuilder(context)
             .setComponentName(MainActivity::class.java)
             .setGraph(R.navigation.mobile_navigation)
             .setDestination(R.id.navigation_home)
             .setArguments(argAlarm)
             .createPendingIntent()*/
        // val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }
}