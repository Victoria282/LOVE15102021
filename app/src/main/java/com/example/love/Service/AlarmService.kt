package com.example.love.Service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import com.example.love.Constants.FOREGROUND_ID
import com.example.love.Constants.NOTIFICATION_CHANNEL_ID
import com.example.love.Constants.NOTIFICATION_ID
import com.example.love.MainActivity
import com.example.love.R
import com.example.love.TaskActivity
import okhttp3.internal.wait

// Сервис для проигрывания музыки и вибрации при включении будильника
class AlarmService: Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private val pattern = longArrayOf(0, 100, 1000)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        createNotificationChannel()
    }

    // Запуск при нажатии на кнопку "старт"
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        showNotification()
        mediaPlayer?.isLooping
        mediaPlayer?.start()
        vibrator!!.vibrate(pattern, 0)
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_ID,
                NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, TaskActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = Notification.Builder (this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("IT Alarm")
            .setContentText("Просыпайся!")
            .setSmallIcon(R.drawable.half_moon)
            .setContentIntent(pendingIntent)
            .setChannelId("1")
            .build()
        startForeground(FOREGROUND_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        // остановка музыки и вибрации
        mediaPlayer?.stop()
        vibrator?.cancel()
    }
}