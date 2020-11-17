package com.example.weather

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.developtool.DebugLog
import com.example.weather.databinding.WeatherCardItemBinding

class WeatherAdapter(var fragment: WeatherFragment) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    var listData: List<WeatherData.Location> = emptyList()

    class ViewHolder(
        var fragment: WeatherFragment,
        private val binding: WeatherCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: WeatherData.Location) {
            binding.data = location
            val drawable = WeatherIconUtil.getIconFromWeather(
                binding.root.context,
                location.weatherElement[0].time[0].parameter.parameterName
            )
            binding.weatherIcon.setImageDrawable(drawable)
            binding.favoriteIcon.setOnClickListener {
                DebugLog.d("btn click")
                setPreference(binding.root.context, location.locationName)
                fragment.weatherAdapter.notifyDataSetChanged()
            }

            // change favorite icon by preference
            binding.favoriteIcon.setImageDrawable(
                binding.root.context.getDrawable(
                    if (fragment.favoriteMap[location.locationName] != null
                        && fragment.favoriteMap[location.locationName]!!
                    ) {
                        R.drawable.selected_star
                    } else {
                        R.drawable.normal_star
                    }
                )
            )
            binding.executePendingBindings()
        }

        private fun setPreference(context: Context, locationName: String) {
            val sharePref = context.getSharedPreferences("test", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharePref.edit()
            val result = !sharePref.getBoolean(locationName, false)
            editor.putBoolean(locationName, result)
            editor.apply()
            fragment.favoriteMap[locationName] = result
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        DebugLog.d("onCreateViewHolder")
        val binding =
            WeatherCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(fragment, binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DebugLog.d("onBindViewHolder")
        holder.bind(listData[position])
    }

}

