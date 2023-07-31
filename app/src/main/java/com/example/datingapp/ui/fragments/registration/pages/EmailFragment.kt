package com.example.datingapp.ui.fragments.registration.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentEmailBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.fragments.registration.RegistrationFragmentDirections
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isEmailValid
import com.example.datingapp.utils.isPasswordValid
import kotlinx.coroutines.launch


class EmailFragment : BaseFragment<FragmentEmailBinding>() {

    private val viewModel: RegistrationViewModel by viewModels({ requireParentFragment() })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToParentViewModelEvents()
        setupListeners()
    }

    private fun subscribeToParentViewModelEvents() {
        subscribeToUserState()
    }

    private fun subscribeToUserState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registrationUserStatus.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        //Navigate To Profile Filler
                        val direction =
                            RegistrationFragmentDirections.actionRegistrationSlideFragmentToProfileFillerFragment()
                        requireParentFragment().findNavController().navigate(direction)
                    }

                    is Resource.Error -> {
                        binding.createUserButton.text = getString(R.string.create_account)
                        binding.loadingBar.isVisible = false
                        viewModel.setOnBackPressedCallbackState(true)
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> {
                        binding.createUserButton.text = ""
                        binding.loadingBar.isVisible = true
                        viewModel.setOnBackPressedCallbackState(false)
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun setupListeners() {
        createAccountButtonListener()
    }

    private fun createAccountButtonListener() = with(binding) {
        createUserButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val repeatPassword = repeatPasswordEditText.text.toString()

            if (!isEmailValid(email)) {
                //todo show email invalid
                return@setOnClickListener
            }

            if (!isPasswordValid(password)) {
                //todo show password invalid
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                //todo show password invalid
                return@setOnClickListener
            }

            viewModel.registrationEmail = email
            viewModel.registrationPassword = password
            viewModel.registrationRepeatPassword = repeatPassword
            viewModel.createUser()
        }
    }

    override fun getViewBinding() = FragmentEmailBinding.inflate(layoutInflater)
}