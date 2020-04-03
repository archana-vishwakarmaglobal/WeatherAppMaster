package com.example.weatherapp.Model.dataClasses.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.Model.dataClasses.WeatherData

@Database(entities = arrayOf(WeatherData::class),version = 1,exportSchema = false)
abstract class WeatherRoomDatabase :RoomDatabase () {
    abstract fun getWeatherDao(): WeatherDao

    companion object {
        private var INSTANCE: WeatherRoomDatabase? = null

        fun getDataBase(context: Context): WeatherRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoomDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}