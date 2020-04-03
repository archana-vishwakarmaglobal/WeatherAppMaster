package com.example.weatherapp.Model.dataClasses.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.Model.dataClasses.WeatherData
import java.lang.annotation.RetentionPolicy

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData:WeatherData)

    @Query("SELECT * FROM weather_data")
    fun getWeatherData(): LiveData<WeatherData>

    @Query("DELETE FROM weather_data")
    fun deleteAll()

    @Query("SELECT * FROM weather_data WHERE cityAndCountry LIKE :city")
    fun getCityWeatherData(city:String): LiveData<WeatherData>
}