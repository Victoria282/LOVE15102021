package com.example.love

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Receiver: BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "Alarm IT"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            createNotificationChannel(context)
            notifyNotification(context)
        }
    }

    private fun createNotificationChannel(context: Context) {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Будильник",
            NotificationManager.IMPORTANCE_HIGH)

        NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
    }
    private fun notifyNotification(context: Context) {
        with(NotificationManagerCompat.from(context)) {
            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Будильник")
                .setContentText("Пора вставать")
                .setSmallIcon(R.drawable.half_moon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notify(NOTIFICATION_ID, build.build())
        }
    }
}