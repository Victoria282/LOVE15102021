package com.example.love

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.love.BroadcastReceiver.BroadcastReceiver
import com.example.love.SharedPreferences.SharedPreferences
import com.example.love.SharedPreferences.SharedPreferences.pendingIntentSP
import com.example.love.SharedPreferences.SharedPreferences.timeAlarm
import com.example.love.database.AppDatabase
import com.example.love.databinding.ActivityMainBinding
import com.example.love.other.animation.Constants


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmManager: AlarmManager
    var t = ArrayList<PendingIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_settings)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        AppDatabase.invoke(applicationContext)
    }

    fun getMyData(): String? {
        return intent.getStringExtra("result").toString()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun sendActionToBroadcast(time: Long, action:String) {
        val testIntent = Intent(this, BroadcastReceiver::class.java)
        testIntent!!.putExtra("alarmInfo", time.toString())
        testIntent!!.action = action
        val pendingIntentTest = PendingIntent.getBroadcast(this, 0, testIntent!!, PendingIntent.FLAG_UPDATE_CURRENT)
        t.add(pendingIntentTest)
        if(action == "cancel") {
            val neededPending = t.get(t.size-2)
            System.out.println("VIKA WHAT YPU WANT $neededPending")
            alarmManager.cancel(neededPending)
        }
        System.out.println("VIKA I ADD ${t.last()}")
        // var test = PendingIntent.getBroadcast(this, 0, testIntent!!, PendingIntent.FLAG_UPDATE_CURRENT)
        // val test2 = pendingIntentTest /*PendingIntent.getBroadcast(context, 0, testIntent!!, PendingIntent.FLAG_UPDATE_CURRENT)*/
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(time, pendingIntentTest), pendingIntentTest)
        System.out.println("VIKA real ${pendingIntentTest.toString()} \n ${alarmManager.toString()}")
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}