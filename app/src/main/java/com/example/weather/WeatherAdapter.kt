package com.example.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.developtool.DebugLog
import com.example.weather.databinding.WeatherCardItemBinding

class WeatherAdapter :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    var listData: List<WeatherData.Location> = emptyList()

    class ViewHolder(private val binding: WeatherCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: WeatherData.Location) {
            binding.data = location
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        DebugLog.d("onCreateViewHolder")
        val binding =
            WeatherCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DebugLog.d("onBindViewHolder")
        holder.bind(listData[position])
    }

}

