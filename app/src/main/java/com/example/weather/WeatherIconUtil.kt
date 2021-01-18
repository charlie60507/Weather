package com.example.weather

import android.content.Context
import android.graphics.drawable.Drawable

object WeatherIconUtil {
    fun getIconFromWeather(context: Context, weather: String): Drawable? {
        when (weather) {
            context.getString(R.string.sunny)
            -> return context.getDrawable(R.drawable.sun)
            context.getString(R.string.mostly_clear), context.getString(R.string.partly_clear)
            -> return context.getDrawable(R.drawable.sun_cloud)
            context.getString(R.string.cloudy), context.getString(R.string.mostly_cloudy),
            context.getString(R.string.mostly_cloudy_1), context.getString(R.string.partly_cloudy)
            -> return context.getDrawable(R.drawable.cloud)
            context.getString(R.string.cloudy_rain), context.getString(R.string.cloudy_short_rain), context.getString(R.string.mostly_cloudy_short_rain), context.getString(R.string.cloudy_short_temp_rain)
            -> return context.getDrawable(R.drawable.rain)
        }
        return null
    }

}