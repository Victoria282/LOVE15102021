package com.example.love.ui.Service

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.example.love.R
import java.util.*

class AlarmService: Service() {
    private var mediaPlayer: MediaPlayer? = null

    companion object {
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        /*mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer?.isLooping
        mediaPlayer?.start()*/
    }

    // Запуск при нажатии на кнопку "старт"
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private inner class TimeTask(private var time: Double): TimerTask() {
        override fun run() {

        }
    }
    override fun onDestroy() {
        super.onDestroy()
       // mediaPlayer?.stop()
    }
}