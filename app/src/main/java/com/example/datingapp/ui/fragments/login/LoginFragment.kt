package com.example.datingapp.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentLoginBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.utils.closeKeyboard
import com.example.datingapp.utils.isEmailValid
import com.example.datingapp.utils.safeNavigate


class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            closeKeyboard(requireActivity())
            findNavController().navigateUp()
        }
        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.emailEditText.text.toString().trim()

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
        }
        binding.forgotPassword.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.in_development), Toast.LENGTH_SHORT).show()
            //TODO: forgot password
        }
    }
}