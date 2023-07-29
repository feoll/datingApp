package com.example.datingapp.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentOnboardingBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.utils.safeNavigate


class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {
    override fun getViewBinding() = FragmentOnboardingBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.logIn.setOnClickListener {
            val direction = OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
            safeNavigate(direction)
        }

        binding.signUp.setOnClickListener {
            val direction = OnboardingFragmentDirections.actionOnboardingFragmentToRegistrationFragment()
            safeNavigate(direction)
        }
        binding.privacy.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.in_development), Toast.LENGTH_SHORT).show()
            //TODO: privacy
        }
    }
}