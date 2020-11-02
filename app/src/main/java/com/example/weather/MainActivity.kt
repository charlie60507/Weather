package com.example.weather

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developtool.DebugLog
import com.example.weather.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val mViewModel = ViewModel()
    private val mAdapter = WeatherAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        DebugLog.d("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // using data binding
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
        binding.container.setOnRefreshListener { getWeatherData(binding) }
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        getWeatherData(binding)
    }

    private fun getWeatherData(binding: ActivityMainBinding) {
        if (isConnected()) {
            mViewModel.refresh()
            mViewModel.listData.observe(this, Observer {
                DebugLog.d("update, it=$it")
                binding.hintText.visibility = View.INVISIBLE
                binding.container.isRefreshing = false
                mAdapter.listData = it
                mAdapter.notifyDataSetChanged()
            })
        } else {
            DebugLog.d("no internet")
            mAdapter.notifyDataSetChanged()
            binding.container.isRefreshing = false
            binding.hintText.visibility = View.VISIBLE
        }
    }

    private fun isConnected(): Boolean {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        if (networkInfo != null) {
            return networkInfo.isConnected
        }
        return false
    }

}
