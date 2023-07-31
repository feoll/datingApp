package com.example.datingapp.ui.fragments.profile_filler

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.databinding.FragmentProfileFillerBinding
import com.example.datingapp.ui.adapters.ViewPagerAdapter
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.fragments.profile_filler.pages.AboutYourselfFragment
import com.example.datingapp.ui.fragments.profile_filler.pages.CategoryFragment
import com.example.datingapp.ui.fragments.profile_filler.pages.GenderFragment
import com.example.datingapp.ui.fragments.profile_filler.pages.HeightFragment
import com.example.datingapp.ui.fragments.profile_filler.pages.ImageFragment
import com.example.datingapp.ui.fragments.profile_filler.pages.LocationFragment
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFillerFragment : BaseFragment<FragmentProfileFillerBinding>() {

    private val viewModel by viewModels<ProfileFillerViewModel>()

    private val fragments by lazy {
        listOf<Fragment>(
            GenderFragment(),
            HeightFragment(),
            LocationFragment(),
            AboutYourselfFragment(),
            CategoryFragment(),
            ImageFragment()
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

        hideMainElements()
        setupOnBackPressedCallback()
        setupViewPager()
        setMaxPages(fragments.size)
        subscribeToProfileFillerEvents()
        setupListeners()
    }

    private fun setupListeners() {
        setupBackButtonListener()
        setupReloadButtonListener()
    }

    private fun setupReloadButtonListener() = with(binding) {
        viewModel.loadProfileToCreate()
    }

    private fun setupBackButtonListener() = with(binding) {
        backButton.setOnClickListener {
            if (!callback.isEnabled) {
                closeKeyboard(requireActivity())
//                findNavController().navigateUp()
                requireActivity().finish()
            } else {
                viewModel.previousPage()
            }
        }
    }

    private fun subscribeToProfileFillerEvents() {
        subscribeToCurrentPageEvent()
        subscribeToMaxPageEvent()
        subscribeToOnBackPressedEvent()
        subscribeToProfileToCreateEvent()
    }

    private fun subscribeToProfileToCreateEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileToCreateState.collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        showMainElements()
                        hideLoadingBar()
                        hideErrorDialog()
                    }
                    is Resource.Loading ->  {
                        hideMainElements()
                        showLoadingBar()
                        hideErrorDialog()
                    }
                    is Resource.Error -> {
                        hideLoadingBar()
                        showErrorDialog()
                        hideMainElements()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun hideErrorDialog() = with(binding) {
        errorTextView.isVisible = false
        reloadButton.isVisible = false
    }
    private fun hideLoadingBar() = with(binding) {
        loadingBarInfo.isVisible = false
    }

    private fun hideMainElements() = with(binding) {
        backButton.isVisible = false
        progressBar.isVisible = false
        viewPager.isVisible = false
    }
    private fun showErrorDialog() = with(binding) {
        errorTextView.isVisible = true
        reloadButton.isVisible = true
    }

    private fun showLoadingBar() = with(binding) {
        loadingBarInfo.isVisible = true
    }


    private fun showMainElements() = with(binding) {
        backButton.isVisible = true
        progressBar.isVisible = true
        viewPager.isVisible = true
    }

    private fun subscribeToOnBackPressedEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onBackPressedCallbackState.collect { state ->
                callback.isEnabled = state
            }
        }
    }

    private fun subscribeToMaxPageEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.maxPages.collect { max ->
                binding.progressBar.max = max
            }
        }
    }

    private fun subscribeToCurrentPageEvent() {
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

    override fun getViewBinding() = FragmentProfileFillerBinding.inflate(layoutInflater)
}