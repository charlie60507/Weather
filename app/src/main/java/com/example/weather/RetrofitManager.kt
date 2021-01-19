package com.example.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitManager {
    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://opendata.cwb.gov.tw/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
    }
}