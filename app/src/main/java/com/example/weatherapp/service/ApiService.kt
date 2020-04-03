package com.example.weatherapp.service


import com.example.weatherapp.Model.dataClasses.WeatherInfoResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // key = 296e0fb972b59b33e814b130232d8447

    //http://api.openweathermap.org/data/2.5/weather?id=7778677&amp;appid=5ad72
    //18f2e11df834b0eaf3a33a39d2a&quot;
    //http://api.openweathermap.org/data/2.5/weather?q=Bengaluru&app_id=296e0fb972b59b33e814b130232d8447

    @GET("weather")
    fun callApiForWeatherInfo(@Query("q") cityName: String): Call<WeatherInfoResponse>
}