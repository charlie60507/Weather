package com.example.weather

import com.example.developtool.DebugLog
import com.example.weather.data.Location
import com.example.weather.data.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataModel {
    fun getData(favoriteMap: MutableMap<String, Boolean>, callback: OnDataReadyCallback) {
        val favoriteList: MutableList<Location> = mutableListOf()
        val others: MutableList<Location> = mutableListOf()
        val apiService = RetrofitManager.apiService
        val call = apiService.getWeatherData("CWB-E5CA5B10-78BE-48F9-BE62-1129B587C5F5")

        call.enqueue(object : Callback<WeatherData?> {
            override fun onFailure(call: Call<WeatherData?>?, t: Throwable?) {
                DebugLog.d("onFailure")
            }

            override fun onResponse(
                call: Call<WeatherData?>?,
                response: Response<WeatherData?>?
            ) {
                DebugLog.d("onResponse")
                response?.body()?.records?.location?.let {
                    for (location in it) {
                        if (favoriteMap[location.locationName] != null && favoriteMap.getValue(
                                location.locationName
                            )
                        ) {
                            favoriteList.add(location)
                        } else {
                            others.add(location)
                        }
                    }
                }
                callback.OnDataReady(favoriteList + others)
            }
        })
    }

    interface OnDataReadyCallback {
        fun OnDataReady(data: List<Location>)
    }

}