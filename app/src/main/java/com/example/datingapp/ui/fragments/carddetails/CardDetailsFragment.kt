package com.example.datingapp.ui.fragments.carddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentCardDetailsBinding
import com.example.datingapp.ui.adapters.card.ProfileImageViewPagerAdapter
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.models.Image
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.ui.viewmodels.CardViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class CardDetailsFragment : BaseFragment<FragmentCardDetailsBinding>() {

    private val args by navArgs<CardDetailsFragmentArgs>()
    private val viewModel by activityViewModels<CardViewModel>()
    private val imageAdapter by lazy { ProfileImageViewPagerAdapter() }

    override fun getViewBinding() = FragmentCardDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfileInformation()
        setupHobbyChips(args.profile.hobby)
        setupMoreAboutMeChips(args.profile)
        setupGoalChip(args.profile.goal)
        fillImageAdapter(args.profile)
        setupImageViewPager()
        setupListeners()
    }

    private fun fillImageAdapter(profile: ProfileCard) {
        val images = mutableListOf<Image>()
        profile.imageUrls.forEachIndexed { index, url ->
            images.add(Image(url, profile.blurs[index]))
        }
        imageAdapter.setList(images)
    }

    private fun setupImageViewPager() {
        binding.imageViewPager.adapter = imageAdapter
        binding.imageViewPager.doOnPreDraw {
            binding.imageViewPager.currentItem = args.profile.selectedImage
        }
        binding.imageViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerIndicator.setViewPager(binding.imageViewPager)
    }

    private fun initProfileInformation() {
        args.profile.let { profile ->
            binding.name.text = profile.name
            profile.height?.let {
                binding.height.text = "${it}см"
                binding.height.isVisible = true
            }
            profile.about?.let {
                binding.description.text = it
                binding.description.isVisible = true
                binding.aboutTv.isVisible = true
            }
            profile.age?.let {
                binding.age.text = it.toString()
                binding.age.isVisible = true
            }
            profile.gender?.let {
                binding.gender.text = if (it == "Female") "Девушка" else "Парень"
                binding.gender.isVisible = true
            }
            binding.place.text = "${args.profile.country}, ${args.profile.city}"
            binding.place.isVisible = true
        }
    }

    private fun setupListeners() {
        binding.likeButton.setOnClickListener {
            viewModel.lastVisibleProfile = args.nextProfile
            findNavController().navigateUp()
        }
        binding.dislikeButton.setOnClickListener {
            viewModel.lastVisibleProfile = args.nextProfile
            findNavController().navigateUp()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun setupHobbyChips(list: List<String>) {
        setupChips(list, binding.hobbyTv, binding.hobbyGroup)
    }

    private fun setupMoreAboutMeChips(profile: ProfileCard) {
        val list = mutableListOf<String>()
        profile.smokingAttitude?.let {
            list.add(it)
        }
        profile.alcoholAttitude?.let {
            list.add(it)
        }
        profile.sportAttitude?.let {
            list.add(it)
        }
        profile.petAttitude?.let {
            list.add(it)
        }
        setupChips(list, binding.moreAboutMeTv, binding.moreAboutMeGroup)
    }

    private fun setupGoalChip(goal: String?) {
        if(goal == null)
            setupChips(emptyList(), binding.goalTv, binding.goalGroup)
        else
            setupChips(listOf(goal), binding.goalTv, binding.goalGroup)
    }

    private fun setupChips(list: List<String>, title: TextView, group: ChipGroup) {
        if (list.isEmpty()) {
            title.visibility = View.GONE
            group.visibility = View.GONE
            return
        }
        title.visibility = View.VISIBLE
        group.visibility = View.VISIBLE
        list.forEach {
            val chip = LayoutInflater.from(context).inflate(
                R.layout.card_details_chip,
                group,
                false
            ) as Chip
            chip.text = it
            group.addView(chip)
        }
    }
}