package com.example.datingapp.ui.fragments.registration

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentRegistrationBinding
import com.example.datingapp.ui.adapters.ViewPagerAdapter
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.fragments.registration.pagers.AboutYourselfFragment
import com.example.datingapp.ui.fragments.registration.pagers.AgeFragment
import com.example.datingapp.ui.fragments.registration.pagers.CategoryFragment
import com.example.datingapp.ui.fragments.registration.pagers.GenderFragment
import com.example.datingapp.ui.fragments.registration.pagers.LocationFragment
import com.example.datingapp.ui.fragments.registration.pagers.NameFragment
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import com.example.datingapp.utils.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(){

    private val callback = object: OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            viewModel.previousPage()
        }
    }

    private val viewModel by activityViewModels<RegistrationViewModel>()
    override fun getViewBinding() = FragmentRegistrationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val fragments = listOf<Fragment>(
            LocationFragment(),
            GenderFragment(),
            NameFragment(),
            AgeFragment(),
            AboutYourselfFragment(),
            CategoryFragment.newInstance(
                "Отношение к животным",
                ArrayList<String>().apply {
                    repeat(5) {
                        add("Люблю животных")
                        add("Ненавижу")
                        add("У меня срывает крышу")
                        add("Это все потому")
                    }
                                          },
                true
            )
        )

        val adapter = ViewPagerAdapter (
            fragments,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        binding.progressBar2.max = fragments.size

        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pageState.collect { page ->
                binding.viewPager.currentItem = page
                binding.progressBar2.progress = page + 1
            }
        }

        binding.nextButton.setOnClickListener {
            closeKeyboard(requireActivity())
            viewModel.nextPage()
        }

        binding.backButton.setOnClickListener {
            if(viewModel.canGoBack) {
                closeKeyboard(requireActivity())
                findNavController().navigateUp()
            }
            else
                viewModel.previousPage()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onBackPressedEnabled.collect { status ->
                callback.isEnabled = !status
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nextButtonAvailable.collect { status ->
                binding.nextButton.isEnabled = status
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        viewModel.setMaxPages(fragments.size)
    }


}