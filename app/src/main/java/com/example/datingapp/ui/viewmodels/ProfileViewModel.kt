package com.example.datingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.models.remote.NewProfile
import com.example.datingapp.data.repositories.ProfileRepository
import com.example.datingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {


    init {
        getNewProfile(1)
    }

    private val _newProfile = MutableStateFlow<Resource<NewProfile>>(Resource.Undefined())
    val newProfile = _newProfile.asStateFlow()

    fun getNewProfile(profileId: Int) = viewModelScope.launch {
        _newProfile.emit(Resource.Loading())
        delay(1000)
        _newProfile.emit(repository.getNewProfile(profileId))
    }
}