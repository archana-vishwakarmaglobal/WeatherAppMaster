package com.example.weatherapp.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Model.dataClasses.WeatherData
import com.example.weatherapp.Model.dataClasses.WeatherInfoResponse
import com.example.weatherapp.Model.dataClasses.database.WeatherDatabaseRepository
import com.example.weatherapp.Repository.DataWrapper
import com.example.weatherapp.Repository.WeatherServerRepository
import com.example.weatherapp.service.RequestCompleteListener
import com.example.weatherapp.utils.MySharePref

class MainActivityViewModel (application:Application): AndroidViewModel(application) {

    lateinit var errorLiveData:MutableLiveData<DataWrapper>;
    // here we can also inject WeatherRepo by Dagger in constructor of MainActivityViewModel
    private val weatherRepo: WeatherServerRepository = WeatherServerRepository(application)
    private var weatherDbRepo: WeatherDatabaseRepository = WeatherDatabaseRepository(application)
    private  val sharePref = MySharePref(application)
    private val refreshTime = 2*60*1000*1000*1000L

    fun getWeatherData(city: String) : LiveData<WeatherData>{
        val dbInsertionTime = sharePref.getDBInsertionTime()
        if(System.nanoTime()- dbInsertionTime > refreshTime){
            Log.d("MainActivityViewModel","fetching data from server");
            errorLiveData = weatherRepo.getWeatherInfo(city)
        }
        return weatherDbRepo.getWeatherData(city)
    }


}