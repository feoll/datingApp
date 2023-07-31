package com.example.datingapp.ui.fragments.profile_filler.pages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentImageBinding
import com.example.datingapp.ui.activities.MainActivity
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.fragments.registration.RegistrationFragmentDirections
import com.example.datingapp.ui.viewmodels.ProfileFillerViewModel
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isEmailValid
import com.example.datingapp.utils.isPasswordValid
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


class ImageFragment : BaseFragment<FragmentImageBinding>() {
    override fun getViewBinding() = FragmentImageBinding.inflate(layoutInflater)

    private val viewModel: ProfileFillerViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToParentViewModelEvents()
        setupListeners()
    }


    private fun subscribeToParentViewModelEvents() {
        subscribeToCreateUserState()
    }


    private fun subscribeToCreateUserState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createProfileState.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("ImageFragment", resource.data.toString())
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    is Resource.Error -> {
                        binding.createUserButton.text = getString(R.string.create_account)
                        binding.loadingBar.isVisible = false
                        viewModel.setOnBackPressedCallbackState(true)
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> {
                        binding.createUserButton.text = ""
                        binding.loadingBar.isVisible = true
                        viewModel.setOnBackPressedCallbackState(false)
                    }

                    else -> Unit
                }
            }
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

    private fun uploadFile(uri: Uri) {
        lifecycleScope.launch {
            val stream = requireContext().contentResolver.openInputStream(uri) ?: return@launch
            val request = stream.use {
                it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            }

            val filePart = MultipartBody.Part.createFormData(
                "file",
                "file",
                request
            )

            viewModel.file = filePart
        }
    }

    private fun setupListeners() {
        createAccountButtonListener()

        binding.imageBackground.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun createAccountButtonListener() = with(binding) {
        createUserButton.setOnClickListener {
            if(image.drawable == null) {
                Toast.makeText(requireContext(), "Выберите фотографию", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.createProfile()
        }
    }
}