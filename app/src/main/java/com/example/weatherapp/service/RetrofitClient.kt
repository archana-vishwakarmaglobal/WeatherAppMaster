package com.example.weatherapp.service

import com.example.weatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.logging.Level

object RetrofitClient {

    private  var retrofit:Retrofit? = null

    private val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    val service: ApiService
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java){
                    if(retrofit==null) {
                      val client  = OkHttpClient.Builder().addInterceptor(AddQueryInterceptor()).build()

                        retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()
                    }

                }

            }
            return retrofit!!.create(ApiService::class.java)
        }

}