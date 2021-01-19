package com.example.weather

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developtool.DebugLog
import com.example.weather.databinding.WeatherFragmentBinding
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {
    companion object {
        const val PREFERENCE_NAME = "test"

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    private val mViewModel = ViewModel()
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var weatherAdapter: WeatherAdapter
    var favoriteMap = mutableMapOf<String, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container.setOnRefreshListener { getWeatherData() }
        loadPreference(view.context)
        weatherAdapter = WeatherAdapter(this)
        recyclerView.adapter = weatherAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context as Activity)
        getWeatherData()
    }

    private fun loadPreference(context: Context) {
        val sharePref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        if (sharePref.all != null) { // if exist sharePref, parse value to favoriteMap
            for (pair in sharePref.all) {
                favoriteMap[pair.key] = sharePref.getBoolean(pair.key, false)
            }
        }
    }

    private fun getWeatherData() {
        if (isConnected()) {
            mViewModel.refresh(favoriteMap).observe(this, Observer {
                DebugLog.d("update, it=$it")
                recyclerView.visibility = View.VISIBLE
                hint_text.visibility = View.INVISIBLE
                container.isRefreshing = false
                weatherAdapter.listData = it
                weatherAdapter.notifyDataSetChanged()
            })
        } else {
            DebugLog.d("no internet")
            recyclerView.visibility = View.INVISIBLE
            hint_text.visibility = View.VISIBLE
            container.isRefreshing = false
            weatherAdapter.notifyDataSetChanged()
        }
    }

    private fun isConnected(): Boolean {
        val cm: ConnectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        if (networkInfo != null) {
            return networkInfo.isConnected
        }
        return false
    }

}