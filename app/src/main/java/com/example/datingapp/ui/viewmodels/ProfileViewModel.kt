package com.example.datingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.models.remote.Profile
import com.example.datingapp.data.models.remote.ProfileToCreate
import com.example.datingapp.data.repositories.profile.ProfileRepository
import com.example.datingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {


    private val _Default_ProfileInfo = MutableStateFlow<Resource<ProfileToCreate>>(Resource.Undefined())
    val newProfile = _Default_ProfileInfo.asStateFlow()

    fun getNewProfile(profileId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _Default_ProfileInfo.emit(Resource.Loading())
        delay(1000)
//        _Default_ProfileInfo.emit(repository.getDefaultProfileInfo(profileId))
    }

    private val _myProfileState = MutableStateFlow<Resource<Profile>>(Resource.Undefined())
    val myProfileState = _myProfileState.asStateFlow()

    fun loadMyProfile() = viewModelScope.launch (Dispatchers.IO) {
        _myProfileState.emit(Resource.Loading())
        _myProfileState.emit(repository.getMyselfProfile())
    }

    init {
        loadMyProfile()
        getNewProfile(1)
    }
}