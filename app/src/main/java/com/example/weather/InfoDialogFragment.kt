package com.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.weather.databinding.InfoDialogFragmentBinding
import com.google.gson.Gson

class InfoDialogFragment : DialogFragment() {
    companion object {
        private const val DATA = "data"
        fun newInstance(data: WeatherData.Location): InfoDialogFragment {
            val f = InfoDialogFragment()
            // Supply num input as an argument.
            val args = Bundle()
            args.putString(DATA, Gson().toJson(data))
            f.arguments = args
            return f
        }
    }

    lateinit var stringData: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stringData = arguments!!.getString(DATA)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            InfoDialogFragmentBinding.inflate(inflater, container, false)
        val data = WeatherData.parseJsonToLocation(stringData)
        binding.data = data
        return binding.root
    }
}