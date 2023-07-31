package com.example.datingapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentLoginBinding
import com.example.datingapp.ui.activities.MainActivity
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.LoginViewModel
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.closeKeyboard
import com.example.datingapp.utils.isEmailValid
import com.example.datingapp.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        subscribeToLoginEvents()
    }

    private fun subscribeToLoginEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    is Resource.Loading -> {


                    }

                    is Resource.Error -> {

                    }

                    else -> Unit
                }
            }
        }
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            closeKeyboard(requireActivity())
            findNavController().navigateUp()
        }

        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.emailEditText.text.toString()

            if(!isEmailValid(email)) {
                binding.emailTextInputLayout.error = getString(R.string.incorrect_email)
            } else {
                binding.emailTextInputLayout.isErrorEnabled = false
            }

            if(password.isEmpty()) {
                binding.passwordTextInputLayout.error = getString(R.string.password_cant_be_empty)
            } else {
                binding.passwordTextInputLayout.isErrorEnabled = false
            }


            viewModel.login(email, password)
        }
        binding.forgotPassword.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.in_development), Toast.LENGTH_SHORT).show()
            //TODO: forgot password
        }
    }
}