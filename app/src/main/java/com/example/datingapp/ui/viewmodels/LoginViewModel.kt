package com.example.datingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.models.params.LoginParams
import com.example.datingapp.data.models.remote.AuthenticationResponse
import com.example.datingapp.data.repositories.authentication.AuthenticationRepository
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository
): ViewModel() {

    private val _loginState = MutableSharedFlow<Resource<AuthenticationResponse>>()
    val loginState = _loginState.asSharedFlow()

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        if(!isEmailValid(email)) {
            return@launch
        }
        if(password.isEmpty()) {
            return@launch
        }
        _loginState.emit(repository.login(LoginParams(email, password)))
    }

}