package com.example.weather

import com.google.gson.Gson

object WeatherData {
    data class Info(
        val success: Boolean,
        val records: Record
    )

    data class Record(
        val location: List<Location>
    )

    data class Location(
        val locationName: String,
        val weatherElement: List<WeatherElement>
    )

    data class WeatherElement(
        val elementName: String,
        val time: List<Time>
    )

    data class Time(
        val startTime: String,
        val endTime: String,
        val parameter: Parameter
    )

    data class Parameter(
        val parameterName: String,
        val parameterUnit: String
    )

    fun parseJsonToWeatherData(json: String?): Info {
        return Gson().fromJson(json, Info::class.java)
    }

}