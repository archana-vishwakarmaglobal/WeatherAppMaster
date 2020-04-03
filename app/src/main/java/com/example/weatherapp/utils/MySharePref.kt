package com.example.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences

class MySharePref( context: Context) {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "weather_time"
    private val DB_INSERTION_TIME = "db_insertion_time";

    val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

    fun updateRefreshTime(time:Long){
        val editor = sharedPref.edit()
        editor.putLong(DB_INSERTION_TIME,time)
        editor.apply()

    }

    fun getDBInsertionTime():Long{
        return sharedPref.getLong(DB_INSERTION_TIME,0L)
    }
}