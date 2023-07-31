package com.example.datingapp.ui.fragments.profile_filler.pages

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.example.datingapp.databinding.FragmentAboutYourselfBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.states.ButtonState
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import com.example.datingapp.utils.isNameValid
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutYourselfFragment : BaseFragment<FragmentAboutYourselfBinding>() {

    private val viewModel: ProfileFillerViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupListeners()
    }

    private fun initUI() = with(binding) {
        nextButton.isEnabled = aboutEditText.text.toString().trim().length >= minSize
    }

    private fun setupListeners() {
        setupNextButtonClickListener()
        setupEditTextListener()
    }

    private fun setupNextButtonClickListener() = with(binding) {
        nextButton.setOnClickListener {
            val about = aboutEditText.text.toString().trim()
            if(about.length >= minSize) {
                viewModel.about = about
                viewModel.setPage(4)
            } else {
                //TODO show error edit text
            }
        }
    }
    private fun setupEditTextListener() = with(binding) {
        aboutEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nextButton.isEnabled = aboutEditText.text.toString().length >= minSize
            }
        })
    }
    override fun getViewBinding() = FragmentAboutYourselfBinding.inflate(layoutInflater)

    companion object {
        private const val minSize = 10
    }
}