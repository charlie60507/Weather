package com.example.weather

import androidx.lifecycle.MutableLiveData
import com.example.developtool.DebugLog

class ViewModel
    : DataModel.OnDataReadyCallback {
    val listData = MutableLiveData<List<WeatherData.Location>>()
    private val dataModel = DataModel()
    fun refresh() {
        dataModel.getData(this)
    }

    override fun OnDataReady(data: List<WeatherData.Location>) {
        DebugLog.d("OnDataReady, info=$data")
        for (location in data) {
            DebugLog.d(location.locationName)
        }
        listData.postValue(data)
    }
}