package com.example.weather

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.WeatherCardItemBinding
import com.example.weather.data.Location

class WeatherAdapter(var fragment: WeatherFragment) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    var listData: List<Location> = emptyList()

    class ViewHolder(
        var fragment: WeatherFragment,
        private val binding: WeatherCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            // set long click
            binding.root.setOnLongClickListener {
                Log.d(javaClass.simpleName, "longClick")
                val infoFragment = InfoDialogFragment.newInstance(location)
                fragment.fragmentManager?.let {
                    infoFragment.show(it, "InfoDialogFragment")
                }
                true
            }
            binding.data = location
            val drawable = WeatherIconUtil.getIconFromWeather(
                binding.root.context,
                location.weatherElement[0].time[0].parameter.parameterName
            )
            binding.weatherIcon.setImageDrawable(drawable)
            binding.favoriteIcon.setOnClickListener {
                Log.d(javaClass.simpleName, "btn click")
                setPreference(binding.root.context, location.locationName)
                fragment.weatherAdapter.notifyDataSetChanged()
            }

            // change favorite icon by preference
            binding.favoriteIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    if (fragment.favoriteMap[location.locationName] == true) {
                        R.drawable.selected_star
                    } else {
                        R.drawable.normal_star
                    }
                )
            )
            binding.executePendingBindings()
        }

        private fun setPreference(context: Context, locationName: String) {
            val sharePref =
                context.getSharedPreferences(WeatherFragment.PREFERENCE_NAME, MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharePref.edit()
            val result = !sharePref.getBoolean(locationName, false)
            editor.putBoolean(locationName, result)
            editor.apply()
            fragment.favoriteMap[locationName] = result
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(javaClass.simpleName, "onCreateViewHolder")
        val binding =
            WeatherCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(fragment, binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(javaClass.simpleName, "onBindViewHolder")
        holder.bind(listData[position])
    }

}

