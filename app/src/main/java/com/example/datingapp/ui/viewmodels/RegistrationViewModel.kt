package com.example.datingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.models.params.CreateUserParams
import com.example.datingapp.data.models.remote.AuthenticationResponse
import com.example.datingapp.data.repositories.authentication.AuthenticationRepository
import com.example.datingapp.ui.states.ButtonState
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {


    private val _maxPages = MutableStateFlow(0)
    val maxPages = _maxPages.asStateFlow()
    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    var registrationName: String? = null
    var registrationAge: Int = 18
    var registrationEmail: String? = null
    var registrationPassword: String? = null
    var registrationRepeatPassword: String? = null

    private val _registrationUserStatus = MutableSharedFlow<Resource<AuthenticationResponse>>()
    val registrationUserStatus = _registrationUserStatus.asSharedFlow()


    private val _onBackPressedCallbackState = MutableStateFlow(false)
    val onBackPressedCallbackState = _onBackPressedCallbackState.asStateFlow()

    fun setOnBackPressedCallbackState(isActive: Boolean) = viewModelScope.launch {
        _onBackPressedCallbackState.emit(isActive)
    }


    fun setPage(page: Int) = viewModelScope.launch {
        _currentPage.emit(page)
    }

    fun nextPage() = viewModelScope.launch {
        if (_currentPage.value + 1 < maxPages.value) {
            _currentPage.emit(_currentPage.value + 1)
        }
    }

    fun previousPage() = viewModelScope.launch {
        if (_currentPage.value - 1 >= 0) {
            _currentPage.emit(_currentPage.value - 1)
        }
    }

    fun setMaxPages(count: Int) = viewModelScope.launch {
        _maxPages.emit(count)
    }

    fun createUser() = viewModelScope.launch(Dispatchers.IO) {
        val email = registrationEmail?.trim()
        val password = registrationPassword
        val repeatPassword = registrationRepeatPassword
        val name = registrationName?.trim()
        val age = registrationAge

        if (email == null || password == null || repeatPassword == null || name == null || age == null) {
            Log.d("RegistrationViewModel", "Не все поля заполнены")
            Log.d("RegistrationViewModel", email.toString())
            Log.d("RegistrationViewModel", password.toString())
            Log.d("RegistrationViewModel", repeatPassword.toString())
            Log.d("RegistrationViewModel", name.toString())
            Log.d("RegistrationViewModel", age.toString())
            _registrationUserStatus.emit(Resource.Error("Не все данные заполнены"))
            return@launch
        }

        //TODO добавить валидацию

        if (!isEmailValid(email)) {
            _registrationUserStatus.emit(Resource.Error("Неверный формат почты"))
            return@launch
        }

        if (password != repeatPassword) {
            _registrationUserStatus.emit(Resource.Error("Пароли не совпадают"))
            return@launch
        }

        _registrationUserStatus.emit(Resource.Loading())

        delay(5000)

        val response = repository.registerUser(
            CreateUserParams(
                email = email,
                password = password,
                name = name,
                age = age
            )
        )

        _registrationUserStatus.emit(response)
    }
}