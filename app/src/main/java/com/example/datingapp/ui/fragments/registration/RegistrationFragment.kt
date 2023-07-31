package com.example.datingapp.ui.fragments.registration

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.databinding.FragmentRegistrationBinding
import com.example.datingapp.ui.adapters.ViewPagerAdapter
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.fragments.registration.pages.AgeFragment
import com.example.datingapp.ui.fragments.registration.pages.EmailFragment
import com.example.datingapp.ui.fragments.registration.pages.NameFragment
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import com.example.datingapp.utils.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    private val viewModel by viewModels<RegistrationViewModel>()

    private val fragments by lazy {
        listOf<Fragment>(
            NameFragment(),
            AgeFragment(),
            EmailFragment()
        )
    }

    private val adapter by lazy {
        ViewPagerAdapter(
            fragments,
            childFragmentManager,
            lifecycle
        )
    }

    private val callback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            viewModel.previousPage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnBackPressedCallback()
        setupViewPager()
        setMaxPages(fragments.size)
        subscribeToRegistrationEvents()
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        backButton.setOnClickListener {
            if (!callback.isEnabled) {
                closeKeyboard(requireActivity())
                findNavController().navigateUp()
            } else {
                viewModel.previousPage()
            }
        }
    }

    private fun subscribeToRegistrationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentPage.collect { page ->
                when (page) {
                    0 -> viewModel.setOnBackPressedCallbackState(false)

                    fragments.size - 1 -> {
                        viewModel.setOnBackPressedCallbackState(true)
                    }

                    else -> viewModel.setOnBackPressedCallbackState(true)
                }

                binding.viewPager.currentItem = page
                binding.progressBar.progress = page + 1
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.maxPages.collect { max ->
                binding.progressBar.max = max
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onBackPressedCallbackState.collect { state ->
                callback.isEnabled = state
            }
        }
    }

    private fun setupOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setupViewPager() = with(binding) {
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
    }


    private fun setMaxPages(pages: Int) {
        viewModel.setMaxPages(pages)
    }

    override fun getViewBinding() = FragmentRegistrationBinding.inflate(layoutInflater)
}