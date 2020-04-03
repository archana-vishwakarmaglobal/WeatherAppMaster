package com.example.weatherapp.service

import com.example.weatherapp.utils.Constant
import okhttp3.Interceptor
import okhttp3.Response

class AddQueryInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val url =  chain.request().url().newBuilder()
            .addQueryParameter("appid", Constant.API_KEY)
           .build()

         val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)

    }
}