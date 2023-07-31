package com.example.datingapp.ui.fragments.profile_filler.pages

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.example.datingapp.databinding.FragmentCategoryBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.states.ButtonState
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isNameValid
import com.example.datingapp.utils.setupChipToGroup
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val viewModel: ProfileFillerViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupListeners()
        subscribeToProfileFillerEvents()
    }

    private fun subscribeToProfileFillerEvents() {
        subscribeToProfileCreateEvent()
    }

    private fun subscribeToProfileCreateEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileToCreateState.collect { resource ->
                if(resource is Resource.Success) {
                    setupChipToGroup(resource.data.goalDtoList.map { it.name }, binding.goalGroup)
                    setupChipToGroup(resource.data.hobbyDtoList.map { it.name }, binding.hobbyGroup)
                    setupChipToGroup(resource.data.alcoholAttitudeDtoList.map { it.name }, binding.alcoholGroup)
                    setupChipToGroup(resource.data.smokingAttitudeDtoList.map { it.name }, binding.smokeGroup)
                    setupChipToGroup(resource.data.petAttitudeDtoList.map { it.name }, binding.petGroup)
                    setupChipToGroup(resource.data.sportAttitudeDtoList.map { it.name }, binding.sportGroup)
                    setupChipToGroup(resource.data.zodiacSignDtoList.map { it.name }, binding.zodiacGroup)
                }
            }
        }
    }

    private fun setupListeners() {
        setupNextButtonClickListener()
        setupGoalGroupListener()
    }

    private fun setupGoalGroupListener() = with(binding) {
        goalGroup.setOnCheckedChangeListener { _, _ ->
            nextButton.isEnabled = goalGroup.checkedChipIds.isNotEmpty()
        }
    }

    private fun setupNextButtonClickListener() = with(binding) {
        nextButton.setOnClickListener {
            if(goalGroup.checkedChipIds.isNotEmpty()) {

                viewModel.hobby = hobbyGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.toList()

                viewModel.smoke = smokeGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.first()

                viewModel.zodiac = zodiacGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.first()

                viewModel.goal = goalGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.first()

                viewModel.sport = sportGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.first()

                viewModel.alcohol = alcoholGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.first()

                viewModel.pet = petGroup.children
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }.first()

                viewModel.setPage(5)
            }
        }
    }
    private fun initUI() = with(binding) {
        nextButton.isEnabled = goalGroup.checkedChipIds.isNotEmpty()
    }

    override fun getViewBinding() = FragmentCategoryBinding.inflate(layoutInflater)
}