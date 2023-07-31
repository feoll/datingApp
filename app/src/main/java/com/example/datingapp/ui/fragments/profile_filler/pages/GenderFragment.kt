package com.example.datingapp.ui.fragments.profile_filler.pages

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.datingapp.data.common.GENDER_FEMALE
import com.example.datingapp.data.common.GENDER_MALE
import com.example.datingapp.databinding.FragmentGenderBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.states.ButtonState
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GenderFragment : BaseFragment<FragmentGenderBinding>() {

    private val viewModel: ProfileFillerViewModel by viewModels({ requireParentFragment() })
    override fun getViewBinding() = FragmentGenderBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupListeners()
    }

    private fun initUI() = with(binding) {
        nextButton.isEnabled = checkboxMan.isChecked || checkboxWoman.isChecked
    }

    private fun setupListeners() {
        setupCheckBoxListener()
        setupNextButtonClickListener()
    }

    private fun setupNextButtonClickListener() = with(binding) {
        nextButton.setOnClickListener {
            if(checkboxMan.isChecked) {
                viewModel.gender = GENDER_MALE
            }
            if(checkboxWoman.isChecked) {
                viewModel.gender = GENDER_FEMALE
            }
            viewModel.setPage(1)
        }
    }

    private fun setupCheckBoxListener() = with(binding) {
        checkboxMan.setOnClickListener {
            checkboxMan.isChecked = true
            checkboxWoman.isChecked = false
            nextButton.isEnabled = true
        }
        checkboxWoman.setOnClickListener {
            checkboxWoman.isChecked = true
            checkboxMan.isChecked = false
            nextButton.isEnabled = true
        }
    }
}