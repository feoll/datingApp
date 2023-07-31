package com.example.datingapp.ui.fragments.profile_filler.pages

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentHeightBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeightFragment : BaseFragment<FragmentHeightBinding>() {

    private val viewModel: ProfileFillerViewModel by viewModels({ requireParentFragment() })
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
            viewModel.height = heightPicker.value
            viewModel.setPage(2)
        }
    }

    private fun setupAgePickerStyle() {
        val fontLight = (ResourcesCompat.getFont(requireContext(), R.font.montserrat))
        val fontBold = (ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold))
        binding.heightPicker.typeface = fontLight
        binding.heightPicker.setSelectedTypeface(fontBold)
    }

    override fun getViewBinding() = FragmentHeightBinding.inflate(layoutInflater)
}