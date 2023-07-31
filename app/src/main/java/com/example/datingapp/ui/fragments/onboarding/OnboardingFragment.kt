package com.example.datingapp.ui.fragments.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentOnboardingBinding
import com.example.datingapp.ui.activities.MainActivity
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.AuthViewModel
import com.example.datingapp.utils.NAVIGATE_TO_PROFILE
import com.example.datingapp.utils.NAVIGATE_TO_PROFILE_FILLER
import com.example.datingapp.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun getViewBinding() = FragmentOnboardingBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToOnboardingEvents()
        setupListeners()
    }

    private fun subscribeToOnboardingEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigate.collect { id ->
                when(id) {
                    NAVIGATE_TO_PROFILE -> {
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    NAVIGATE_TO_PROFILE_FILLER -> {
                        val direction = OnboardingFragmentDirections.actionOnboardingFragmentToProfileFillerFragment()
                        findNavController().navigate(direction)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.logIn.setOnClickListener {
            val direction = OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
            safeNavigate(direction)
        }

        binding.signUp.setOnClickListener {
            val direction = OnboardingFragmentDirections.actionOnboardingFragmentToRegistrationSlideFragment()
            safeNavigate(direction)
        }
        binding.privacy.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.in_development), Toast.LENGTH_SHORT).show()
            //TODO: privacy
        }
    }
}