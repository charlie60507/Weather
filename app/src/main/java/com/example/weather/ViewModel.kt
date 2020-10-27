package com.example.weather

import androidx.lifecycle.MutableLiveData
import com.example.developtool.DebugLog

class ViewModel
    : DataModel.OnDataReadyCallback {
    var data = MutableLiveData<List<WeatherData.Location>>()
    val dataModel = DataModel()
    fun refresh(): MutableLiveData<List<WeatherData.Location>> {
        dataModel.getData(this)
        return data
    }

    override fun OnDataReady(data: List<WeatherData.Location>) {
        DebugLog.d("OnDataReady, info=$data")
        for (location in data) {
            DebugLog.d(location.locationName)
        }
        this.data.postValue(data)
    }
}