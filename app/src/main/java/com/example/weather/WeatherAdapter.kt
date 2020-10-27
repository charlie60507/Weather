package com.example.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.developtool.DebugLog
import com.example.weather.databinding.WeatherCardviewBinding

class WeatherAdapter(val dataList: List<WeatherData.Location>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    class ViewHolder(val binding: WeatherCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: WeatherData.Location) {
            DebugLog.d("bind, location.locationName=" + location.locationName)
            binding.location.text = location.locationName
            binding.weather.text = location.weatherElement[0].time[0].parameter.parameterName
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        DebugLog.d("onCreateViewHolder")
        val binding =
            WeatherCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DebugLog.d("onBindViewHolder")
        holder.bind(dataList[position])
    }

}