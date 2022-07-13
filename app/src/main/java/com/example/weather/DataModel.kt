package com.example.weather

import android.util.Log
import com.example.weather.data.Location
import com.example.weather.data.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataModel {
    fun getData(favoriteMap: MutableMap<String, Boolean>, callback: (List<Location>) -> Unit) {
        Log.d(javaClass.simpleName, "getData")
        val favoriteList: MutableList<Location> = mutableListOf()
        val others: MutableList<Location> = mutableListOf()
        val apiService = RetrofitManager.apiService
        val call = apiService.getWeatherData("CWB-E5CA5B10-78BE-48F9-BE62-1129B587C5F5")

//        // use execute + coroutine to call api
//        CoroutineScope(Dispatchers.IO).launch {
//            val request = async { call.execute() }
//            if (request.await().isSuccessful) {
//                request.await().body()?.records?.location?.let {
//                    for (location in it) {
//                        if (favoriteMap[location.locationName] != null && favoriteMap.getValue(
//                                location.locationName
//                            )
//                        ) {
//                            favoriteList.add(location)
//                        } else {
//                            others.add(location)
//                        }
//                    }
//                }
//                callback(favoriteList + others)
//            }
//        }
//    }

        call.enqueue(object : Callback<WeatherData?> {
            override fun onFailure(call: Call<WeatherData?>?, t: Throwable?) {
                Log.d(javaClass.simpleName, "onFailure")
            }

            override fun onResponse(
                call: Call<WeatherData?>?,
                response: Response<WeatherData?>?
            ) {
                Log.e(javaClass.simpleName, "onResponse")
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
                callback(favoriteList + others)
            }
        })
    }

}