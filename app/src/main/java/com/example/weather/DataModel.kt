package com.example.weather

import com.example.developtool.DebugLog
import java.net.URL


class DataModel {
    fun getData(callback: OnDataReadyCallback) {
        Thread {
            val data: MutableList<WeatherData.Location> = mutableListOf()
            val url =
                "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-E5CA5B10-78BE-48F9-BE62-1129B587C5F5"
            val inputStream = URL(url).openStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            DebugLog.d("inputString=$inputString")

            val weatherDataInfo = WeatherData.parseJsonToWeatherData(inputString).records.location
            for (location in weatherDataInfo) {
                data.add(location)
            }

            callback.OnDataReady(data)
        }.start()
    }

    interface OnDataReadyCallback {
        fun OnDataReady(data: List<WeatherData.Location>)
    }

}