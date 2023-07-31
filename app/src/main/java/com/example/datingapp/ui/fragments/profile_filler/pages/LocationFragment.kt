package com.example.datingapp.ui.fragments.profile_filler.pages

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.datingapp.R
import com.example.datingapp.data.models.remote.CityDto
import com.example.datingapp.databinding.FragmentLocationBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import com.example.datingapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private var cities = emptyList<CityDto>()
    private val viewModel: ProfileFillerViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupListeners()
        subscribeToProfileFillerEvents()
    }

    private fun subscribeToProfileFillerEvents() {
        subscribeToProfileCreateEvent()
    }

    private fun initUI() = with(binding) {
        nextButton.isEnabled =
            !(countryAutoComplete.text.isEmpty() || cityAutoComplete.text.isEmpty())
    }

    private fun setupListeners() {
        setupNextButtonClickListener()
        setupCountryAutoCompleteListener()
        setupCityAutoCompleteListener()
    }

    private fun setCityAdapter(list: List<String>) {
        binding.cityAutoComplete.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                R.id.dropdown_item_text_view,
                list
            )
        )
    }

    private fun setCountryAdapter(list: List<String>) {
        binding.countryAutoComplete.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                R.id.dropdown_item_text_view,
                list
            )
        )
    }

    private fun setupCountryAutoCompleteListener() = with(binding) {
        countryAutoComplete.setOnItemClickListener { adapter, _, position, _ ->
            val country = adapter.getItemAtPosition(position).toString()

            cityAutoComplete.setText("")

            val cityList = cities.filter { it.countryDto.name == country }.map { it.name }
            setCityAdapter(cityList)

            nextButton.isEnabled =
                !(countryAutoComplete.text.isEmpty() || cityAutoComplete.text.isEmpty())
        }
    }

    private fun setupCityAutoCompleteListener() = with(binding) {
        cityAutoComplete.setOnItemClickListener { adapter, _, position, _ ->
            val city = adapter.getItemAtPosition(position).toString()
            nextButton.isEnabled =
                !(countryAutoComplete.text.isEmpty() || cityAutoComplete.text.isEmpty())
        }
    }

    private fun setupNextButtonClickListener() = with(binding) {
        nextButton.setOnClickListener {
            val country = countryAutoComplete.text.toString().trim()
            val city = cityAutoComplete.text.toString().trim()

            if (country.isNotEmpty() && city.isNotEmpty()) {
                viewModel.country = country
                viewModel.city = city
                viewModel.setPage(3)
            } else {
                //TODO show error edit text
            }
        }
    }

    private fun subscribeToProfileCreateEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileToCreateState.collect { resource ->
                if(resource is Resource.Success) {
                    cities = resource.data.cityDtoList
                    val countrySet = cities.map { it.countryDto.name }.toSet()
                    setCountryAdapter(countrySet.sorted())
                }
            }
        }
    }

    override fun getViewBinding() = FragmentLocationBinding.inflate(layoutInflater)
}