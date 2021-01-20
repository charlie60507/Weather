package com.example.weather

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitManager {
    companion object {
        // define okhttp to set time out
        private val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://opendata.cwb.gov.tw/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
    }
}