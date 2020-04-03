package com.example.weatherapp.Repository

import android.util.Log
import com.example.weatherapp.Model.dataClasses.WeatherInfoResponse
import com.example.weatherapp.service.RetrofitClient
import com.example.weatherapp.service.RequestCompleteListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import android.R
import android.app.Application
import com.example.weatherapp.Model.dataClasses.WeatherData
import com.example.weatherapp.Model.dataClasses.database.WeatherDatabaseRepository
import java.util.*


class WeatherServerRepository(var application: Application) {

    fun getWeatherInfo(cityName: String) :MutableLiveData<DataWrapper>{
        var errorLiveDta = MutableLiveData<DataWrapper>()
        var call: Call<WeatherInfoResponse> =
            RetrofitClient.service.callApiForWeatherInfo(cityName)
        var dataWrapper = DataWrapper()
        call.enqueue(object : Callback<WeatherInfoResponse> {
            // if retrofit network call success, this method will be triggered
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                var weatehrinfo = response.body()
                if(response.isSuccessful) {
                    //insert in database
                    insertDatainDB(weatehrinfo)
                }else{
                    dataWrapper.message = response.message()
                    errorLiveDta.postValue(dataWrapper)
                }
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                // callback.onRequestFailed(t.localizedMessage!!) //let viewModel know about failure
                dataWrapper.message = t.localizedMessage
                errorLiveDta.postValue(dataWrapper)

            }
        })
      return errorLiveDta
    }

    private fun insertDatainDB(resposne: WeatherInfoResponse?) {
        var weatherDbRepo = WeatherDatabaseRepository(application)
        var weatherData = convertToWeatehrData(resposne)
        weatherDbRepo.insert(weatherData)
    }

    private fun convertToWeatehrData(resposne: WeatherInfoResponse?): WeatherData {
        var weatherData = WeatherData()
        if (resposne != null) {
            weatherData.cityAndCountry = resposne.name
            weatherData.dateTime = resposne.dt.toString()
            weatherData.humidity = resposne.main.humidity.toString()
            weatherData.pressure = resposne.main.pressure.toString()
            weatherData.temperature = kelvinToCelsius(resposne.main.temp)
            weatherData.visibility = resposne.visibility.toString()
        }
        return weatherData
    }

    fun kelvinToCelsius(temp: Double): String {

        return String.format("%.2f",temp - 273.15) 
    }
}