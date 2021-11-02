package com.adservsolution.sample.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adservsolution.sample.R
import com.adservsolution.sample.databinding.FragmentMainBinding
import com.adservsolution.sdk.AdservsolutionSdk

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        addEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView(view: View) {
        _binding = FragmentMainBinding.bind(view).apply {
            regButton.setOnClickListener {
                AdservsolutionSdk.instance.sendEvent(Event.REG)
            }

            authButton.setOnClickListener {
                AdservsolutionSdk.instance.sendEvent(Event.AUTH)
            }

            betButton.setOnClickListener {
                AdservsolutionSdk.instance.sendEvent(Event.BET)
            }
        }
    }

    private fun addEvents() {
        val events = ArrayList<Event>()
        events.add(Event.REG)
        events.add(Event.AUTH)
        events.add(Event.BET)
        AdservsolutionSdk.instance.prepareEvents(events)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
