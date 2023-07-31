package com.example.datingapp.ui.fragments.registration.pages

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentAgeBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import com.example.datingapp.utils.isNameValid
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgeFragment : BaseFragment<FragmentAgeBinding>() {

    private val viewModel: RegistrationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupListeners()
    }

    private fun initUI() = with(binding) {
        setupAgePickerStyle()
        nextButton.isEnabled = true
    }

    private fun setupListeners() {
        setupNextButtonClickListener()
    }

    private fun setupNextButtonClickListener() = with(binding) {
        nextButton.setOnClickListener {
            viewModel.registrationAge = agePicker.value
            viewModel.setPage(2)
        }
    }

    private fun setupAgePickerStyle() {
        val fontLight = (ResourcesCompat.getFont(requireContext(), R.font.montserrat))
        val fontBold = (ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold))
        binding.agePicker.typeface = fontLight
        binding.agePicker.setSelectedTypeface(fontBold)
    }

    override fun getViewBinding() = FragmentAgeBinding.inflate(layoutInflater)
}