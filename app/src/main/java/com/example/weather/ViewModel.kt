package com.example.weather

import androidx.lifecycle.MutableLiveData
import com.example.developtool.DebugLog
import com.example.weather.data.Location

class ViewModel
    : DataModel.OnDataReadyCallback {
    private val listData = MutableLiveData<List<Location>>()
    private val dataModel = DataModel()

    fun refresh(favoriteMap: MutableMap<String, Boolean>): MutableLiveData<List<Location>> {
        dataModel.getData(favoriteMap, this)
        return listData
    }

    override fun OnDataReady(data: List<Location>) {
        DebugLog.d("OnDataReady, info=$data")
        for (location in data) {
            DebugLog.d(location.locationName)
        }
        listData.postValue(data)
    }
}