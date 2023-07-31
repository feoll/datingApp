package com.example.datingapp.ui.fragments.editprofile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.datingapp.R
import com.example.datingapp.data.api.DeckApi
import com.example.datingapp.databinding.FragmentProfileEditBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.ui.viewmodels.ProfileViewModel
import com.example.datingapp.utils.BlurHashDecoder
import com.example.datingapp.utils.Resource
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>() {
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val args by navArgs<ProfileEditFragmentArgs>()

    override fun getViewBinding() =  FragmentProfileEditBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newProfile.collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        setupChips(resource.data.hobbyDtoList.map { it.name }, binding.hobbyTv, binding.hobbyGroup)
                        setupChips(resource.data.alcoholAttitudeDtoList.map { it.name }, binding.alcoholTv, binding.alcoholGroup)
                        setupChips(resource.data.smokingAttitudeDtoList.map { it.name }, binding.smokingTv, binding.smokingGroup)
                        setupChips(resource.data.petAttitudeDtoList.map { it.name }, binding.petTv, binding.petGroup)
                        setupChips(resource.data.sportAttitudeDtoList.map { it.name }, binding.sportTv, binding.sportGroup)
                        setupChips(resource.data.zodiacSignDtoList.map { it.name }, binding.zodiacTv, binding.zodiacGroup)
                        setupChips(resource.data.goalDtoList.map { it.name }, binding.goalTv, binding.goalGroup)

                        fillChip(binding.hobbyGroup, args.profile.hobby)
                        fillChip(binding.alcoholGroup, args.profile.alcoholAttitude!!)
                        fillChip(binding.smokingGroup, args.profile.smokingAttitude!!)
                        fillChip(binding.petGroup, args.profile.petAttitude!!)
                        fillChip(binding.sportGroup, args.profile.sportAttitude!!)
                        fillChip(binding.zodiacGroup, args.profile.zodiac!!)
                        fillChip(binding.goalGroup, args.profile.goal!!)

                        val blurHash = BlurHashDecoder.blurHashBitmap(binding.image.resources, args.profile.blurs[0])
                        Glide.with(binding.image)
                            .load(args.profile.imageUrls[0].replace("localhost", "192.168.0.197"))
                            .placeholder(blurHash)
                            .into(binding.image)

                        binding.progressBar.isVisible = false
                        binding.scrollView.isVisible = true
                        binding.saveProfile.isVisible = true

                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.scrollView.isVisible = false
                        binding.saveProfile.isVisible = false
                    }
                    else -> Unit
                }
            }
        }

        binding.descriptionEt.setText(args.profile.about)
        binding.ageEt.setText(args.profile.age.toString())
        binding.nameEt.setText(args.profile.name)


        binding.image.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.image.setImageURI(uri)
            uploadFile(uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }


    private fun fillChip(group: ChipGroup, names: List<String>) {
        for(i in 0 until group.childCount) {
            val chip = group.getChildAt(i) as Chip
            if(names.contains(chip.text)) {
                chip.isChecked = true
            }
        }
    }

    private fun fillChip(group: ChipGroup, names: String) {
        for(i in 0 until group.childCount) {
            val chip = group.getChildAt(i) as Chip
            if(names.contains(chip.text)) {
                chip.isChecked = true
            }
        }
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
                R.layout.profile_edit_chip,
                group,
                false
            ) as Chip
            chip.text = it
            group.addView(chip)
        }
    }

    private fun uploadFile(uri: Uri) {
        lifecycleScope.launch {
            Log.d("MyActivity", "On upload")

            val stream = requireContext().contentResolver.openInputStream(uri) ?: return@launch
            val request = stream.use {
                it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            }


            val filePart = MultipartBody.Part.createFormData(
                "file",
                "file",
                request
            )
            try {
//                api.uploadImage(filePart)
            }
            catch(e: Exception) { // if something happens to the network
                e.printStackTrace()
                return@launch
            }
            Log.d("MyActivity", "On finish upload file")
        }
    }

    @Inject
    lateinit var api: DeckApi

}