package com.example.datingapp.ui.fragments.registration.pagers

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentAgeBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgeFragment : BaseFragment<FragmentAgeBinding>() {

    private val viewModel by activityViewModels<RegistrationViewModel>()
    override fun getViewBinding() = FragmentAgeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setPage(1)

        val fontLight = (ResourcesCompat.getFont(requireContext(), R.font.montserrat))
        val fontBold = (ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold))
        binding.agePicker.typeface = fontLight
        binding.agePicker.setSelectedTypeface(fontBold)
    }
}