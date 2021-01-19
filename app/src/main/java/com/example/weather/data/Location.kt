package com.example.weather.data

import java.io.Serializable

data class Location(
    val locationName: String,
    val weatherElement: List<WeatherElement>
) : Serializable