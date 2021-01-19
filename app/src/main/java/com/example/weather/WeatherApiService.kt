package com.example.weather

import com.example.weather.data.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("api/v1/rest/datastore/F-C0032-001")
    fun getWeatherData(@Query("Authorization") auth: String): Call<WeatherData?>
}