package com.example.datingapp.ui.fragments.registration.pagers

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentLocationBinding
import com.example.datingapp.ui.fragments.base.BaseFragment


class LocationFragment : BaseFragment<FragmentLocationBinding>() {
    override fun getViewBinding() = FragmentLocationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = arrayOf("Беларусь", "Россия")
        binding.countryAutoComplete.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, R.id.dropdown_item_text_view, countries))
        binding.countryAutoComplete.setOnItemClickListener { adapter, _, position, _ ->
            val country = adapter.getItemAtPosition(position)
            var cities: Array<String> = emptyArray()
            when(country) {
                "Беларусь" -> {
                    cities = arrayOf("Минск", "Брест")
                }
                "Россия" -> {
                    cities = arrayOf("Москва", "Питер")
                }
            }
            binding.cityAutoComplete.setText("")
            binding.cityAutoComplete.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, R.id.dropdown_item_text_view,cities))
            Log.d("LocationFragment", country.toString())
        }

    }
}