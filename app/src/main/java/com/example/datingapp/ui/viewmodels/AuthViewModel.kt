package com.example.datingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.manager.SessionManager
import com.example.datingapp.utils.NAVIGATE_TO_PROFILE
import com.example.datingapp.utils.NAVIGATE_TO_PROFILE_FILLER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    private val _navigate = MutableSharedFlow<Int>()
    val navigate = _navigate.asSharedFlow()

    init {
        loadNavigation()
    }

    private fun loadNavigation() = viewModelScope.launch(Dispatchers.IO) {

        //todo: delete update
        sessionManager.updateProfileId(19)
        val token = sessionManager.getToken()
        val userId = sessionManager.getUserId()
        val profileId = sessionManager.getProfileId()

        if(profileId != null && userId != null && token != null) {
            _navigate.emit(NAVIGATE_TO_PROFILE)
        }

        if(profileId == null && userId != null && token != null) {
            _navigate.emit(NAVIGATE_TO_PROFILE_FILLER)
        }
    }


}