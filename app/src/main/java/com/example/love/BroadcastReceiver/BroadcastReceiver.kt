package com.example.love.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.love.Service.AlarmService

class BroadcastReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            "set" -> setAlarm(context)
        }
    }
    private fun setAlarm(context: Context) {
        val intent = Intent(context, AlarmService::class.java)
        context.startService(intent)
    }
}