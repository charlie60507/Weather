package com.example.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(javaClass.simpleName, "onCreateView")
        return LayoutInflater.from(context).inflate(R.layout.setting_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(javaClass.simpleName, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

    }

}