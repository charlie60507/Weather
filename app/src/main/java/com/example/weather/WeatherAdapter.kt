package com.example.weather

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
            val drawable = WeatherIconUtil.getIconFromWeather(
                binding.root.context,
                location.weatherElement[0].time[0].parameter.parameterName
            )
            binding.weatherIcon.setImageDrawable(drawable)
            binding.favoriteIcon.setOnClickListener {
                DebugLog.d("btn click")
                setFavoriteIcon(
                    binding.root.context,
                    location.locationName,
                    true
                )
            }

            // change favorite icon by preference
            setFavoriteIcon(
                binding.root.context,
                location.locationName,
                false
            )
            binding.executePendingBindings()
        }

        private fun setFavoriteIcon(context: Context, name: String, isStateChange: Boolean) {
            val sharePref = context.getSharedPreferences("test", MODE_PRIVATE)
            val isFavorite = sharePref.getBoolean(name, false)
            binding.favoriteIcon.setImageDrawable(
                binding.root.context.getDrawable(
                    // remove favorite by pressing icon || init icon to not favorite
                    if ((isStateChange && isFavorite) || (!isStateChange && !isFavorite)) {
                        R.drawable.normal_star
                    } else {
                        R.drawable.selected_star
                    }
                )
            )
            if (isStateChange) {
                val editor: SharedPreferences.Editor = sharePref.edit()
                editor.putBoolean(name, !sharePref.getBoolean(name, false))
                editor.apply()
            }
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

