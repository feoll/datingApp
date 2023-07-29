package com.example.datingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.repositories.SwipeRepository
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: SwipeRepository
): ViewModel() {

    init {
        loadProfiles(1)
    }

    private val _profilesState = MutableStateFlow<Resource<List<ProfileCard>>>(Resource.Undefined())
    val profilesState = _profilesState.asStateFlow()

    var lastVisibleProfile: ProfileCard? = null

    val noCategoryData = MutableStateFlow<Boolean>(false)

    fun loadProfiles(profileId: Int) = viewModelScope.launch {
        _profilesState.emit(Resource.Loading())
        _profilesState.emit(repository.getProfileSwipeList(profileId = profileId))
    }

}