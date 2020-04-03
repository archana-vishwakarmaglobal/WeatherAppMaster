package com.example.weatherapp.Model.dataClasses.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Model.dataClasses.City
import com.example.weatherapp.Model.dataClasses.WeatherData

class WeatherDatabaseRepository (application:Application) {
    private var weatherDao: WeatherDao
    private val allData: LiveData<WeatherData>

    init {
        val roomDatabase = WeatherRoomDatabase.getDataBase(application)
        weatherDao = roomDatabase.getWeatherDao()
        allData = weatherDao.getWeatherData()

    }

    fun insert(weatherData:WeatherData){
            InsertWeatherAsyTask(weatherDao).execute(weatherData)
    }
    fun delete(){
            DeleteWeatherAsyTask(weatherDao).execute()
    }
    fun getWeatherData(city: String):LiveData<WeatherData>{
        return weatherDao.getCityWeatherData(city)
    }

    private class InsertWeatherAsyTask(val weatherDao: WeatherDao): AsyncTask<WeatherData, Unit, Unit>(){
        override fun doInBackground(vararg params: WeatherData?) {
            weatherDao.insertWeatherData(params[0]!!)
        }

    }
    private class DeleteWeatherAsyTask(val weatherDao: WeatherDao): AsyncTask<Unit, Unit, Unit>(){

        override fun doInBackground(vararg params: Unit?) {
            weatherDao.deleteAll()
        }

    }


}