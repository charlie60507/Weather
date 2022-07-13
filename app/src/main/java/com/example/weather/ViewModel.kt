package com.example.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weather.data.Location

class ViewModel {
    private val listData = MutableLiveData<List<Location>>()
    private val dataModel = DataModel()

    fun refresh(favoriteMap: MutableMap<String, Boolean>): MutableLiveData<List<Location>> {
        dataModel.getData(favoriteMap) { result ->
            Log.d(javaClass.simpleName, "OnDataReady, info=$result")
            for (location in result) {
                Log.d(javaClass.simpleName, location.locationName)
            }
            listData.postValue(result)
        }
        return listData
    }

}