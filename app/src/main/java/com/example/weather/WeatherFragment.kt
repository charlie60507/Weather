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
    private val mViewModel = ViewModel()
    private val mAdapter = WeatherAdapter()
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

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
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context as Activity)
        getWeatherData()
    }

    private fun getWeatherData() {
        if (isConnected()) {
            mViewModel.refresh()
            mViewModel.listData.observe(this, Observer {
                DebugLog.d("update, it=$it")
                recyclerView.visibility = View.VISIBLE
                hint_text.visibility = View.INVISIBLE
                container.isRefreshing = false
                mAdapter.listData = it
                mAdapter.notifyDataSetChanged()
            })
        } else {
            DebugLog.d("no internet")
            recyclerView.visibility = View.INVISIBLE
            hint_text.visibility = View.VISIBLE
            container.isRefreshing = false
            mAdapter.notifyDataSetChanged()
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