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
    private val timer = Timer()

    companion object {
        const val TIMER_UPDATED = "timeUpdated"
        const val TIME_EXTRA = "timeExtra"
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
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        //
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return super.onStartCommand(intent, flags, startId)
    }

    private inner class TimeTask(private var time: Double): TimerTask() {
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time++
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        timer.cancel()
    }
}