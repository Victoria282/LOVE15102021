package com.example.love.other

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.love.SharedPreferences.SharedPreferences
import com.google.gson.Gson

object ObjectPending {
    var globalList = ArrayList<PendingIntent>()
}