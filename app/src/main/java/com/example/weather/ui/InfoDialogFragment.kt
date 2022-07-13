package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.weather.databinding.InfoDialogFragmentBinding
import com.example.weather.data.Location

class InfoDialogFragment : DialogFragment() {
    companion object {
        private const val DATA = "data"
        fun newInstance(data: Location): InfoDialogFragment {
            val f = InfoDialogFragment()
            val args = Bundle()
            args.putSerializable(DATA, data)
            f.arguments = args
            return f
        }
    }

    lateinit var data: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments!!.getSerializable(DATA) as Location
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            InfoDialogFragmentBinding.inflate(inflater, container, false)
        binding.data = data
        return binding.root
    }
}