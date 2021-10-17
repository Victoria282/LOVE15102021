package com.example.love

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.love.database.AppDatabase
import com.example.love.databinding.ActivityMainBinding
import android.os.PowerManager

import android.os.PowerManager.WakeLock
import com.example.love.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_settings)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        AppDatabase.invoke(applicationContext)

       /* t = intent.getStringExtra("result").toString()
        m = "NEW"
*/
        /*if(!t.equals("") and t.equals("true")) {
            // Отправляем в HomeFragment коменду об удалении cardView
                System.out.println("MAINVIKA $t")
                *//*val args = Bundle()
                args.putString("result", t)
                val fragment = HomeFragment()
                fragment.arguments = args*//*
            val bundle = Bundle()
            bundle.putString("edttext", "From Activity")
            // set Fragmentclass Arguments
            // set Fragmentclass Arguments
            val fragobj = HomeFragment()
            fragobj.arguments = bundle

        }
*/    }

    fun getMyData(): String? {
        return intent.getStringExtra("result").toString()
    }
}