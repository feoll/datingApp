package com.example.datingapp.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.ui.adapters.card.ProfileImageViewPagerAdapter
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.models.Image
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.ui.viewmodels.ProfileViewModel
import com.example.datingapp.utils.Resource
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val imageAdapter by lazy { ProfileImageViewPagerAdapter() }

    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfileInformation()
        setupHobbyChips(profile.hobby)
        setupMoreAboutMeChips(profile)
        setupGoalChip(profile.goal)
        fillImageAdapter(profile)
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
        binding.imageViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerIndicator.setViewPager(binding.imageViewPager)
    }

    private fun initProfileInformation() {
        profile.let { profile ->
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
            binding.place.text = "${profile.country}, ${profile.city}"
            binding.place.isVisible = true
        }
    }
    private fun setupListeners() {
        binding.editProfile.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(profile)
            findNavController().navigate(direction)
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

    private val profile = ProfileCard(
        1,
        1,
        178,
        "Female",
        "Алина",
        18,
        "Звезда кантри-музыки. Обожаю гулять вечером по парку. Занимаюсь спортом и программирую на Sping Java. Программирую примерно 40 лет потому что потому, хе-хе",
        "Беларусь",
        "Минск",
        "Овен",
        "Найти партнера жизни",
        "Не занимаюсь спортом",
        "Иногда пьющий",
        "Регулярно курящий",
        "Аллергичен на животных",
        listOf("Уборка", "Игры", "Танцы", "Фотография", "Кино"),
        listOf(
            "https://sun9-79.userapi.com/impg/TejZrgXyWcVMbm8OGMLdOd6vbI7pZSKRxGtV1w/Hk4rWw5PzSw.jpg?size=1440x1439&quality=95&sign=41838d9379c00e56251dbeed0ac3a2ed&type=album",
            "https://sun9-65.userapi.com/impg/v2XRhsGsKvSD2xGDsb_WS0mfBrk0yy0jjLOayw/TPSj4eMIyuU.jpg?size=720x957&quality=95&sign=6d3123d435a89809ec1f00e7b56814ed&type=album",
            "https://sun9-22.userapi.com/impg/g0gRc-BI72VGLtjSO5g4mVLkgcu9ZCt4OnswWw/hN8Jr06ZWbM.jpg?size=2560x2560&quality=95&sign=c84ce23015fd829fb39c9ba284d6cc1e&type=album"
        ),
        listOf(
            "L6B3{E5i9:rp03rCR?cH}+t7T1j;",
            "L8I4ChNG1a~V00nORis9GCr?^QM{",
            "L9AJ+irq~UM|?c\$|n%jZx]WBWCRj"
        )
    )
}