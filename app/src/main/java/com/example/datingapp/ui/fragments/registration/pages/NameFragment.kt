package com.example.datingapp.ui.fragments.registration.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.datingapp.databinding.FragmentNameBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.states.ButtonState
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import com.example.datingapp.utils.isNameValid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : BaseFragment<FragmentNameBinding>() {

    private val viewModel: RegistrationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupListeners()
    }

    private fun initUI() = with(binding) {
        nextButton.isEnabled = isNameValid(nameEditText.text.toString().trim())
    }

    private fun setupListeners() {
        setupNextButtonClickListener()
        setupEditTextListener()
    }

    private fun setupNextButtonClickListener() = with(binding) {
        nextButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            if(isNameValid(name)) {
                viewModel.registrationName = name
                viewModel.setPage(1)
            } else {
                //TODO show error edit text
            }
        }
    }
    private fun setupEditTextListener() = with(binding) {
        nameEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nextButton.isEnabled = isNameValid(p0.toString().trim())
            }
        })
    }
    override fun getViewBinding() = FragmentNameBinding.inflate(layoutInflater)
}