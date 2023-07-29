package com.example.datingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(): ViewModel() {


    private val _pageState = MutableStateFlow(0)
    val pageState = _pageState.asStateFlow()
    private var maxPages = 0

    private val _onBackPressedEnabled = MutableStateFlow(true)
    val onBackPressedEnabled = _onBackPressedEnabled.asStateFlow()
    var canGoBack = true

    private val _nextButtonAvailable = MutableStateFlow(true)
    val nextButtonAvailable = _nextButtonAvailable.asStateFlow()


    fun setPage(page: Int)  = viewModelScope.launch {
        _pageState.emit(page)
    }

    fun nextPage() = viewModelScope.launch {
        if(_pageState.value + 1 < maxPages) {
            _pageState.emit(_pageState.value + 1)
            _onBackPressedEnabled.emit(false)
            canGoBack = false
            //_nextButtonAvailable.emit(false)
        }
    }

    fun previousPage() = viewModelScope.launch {
        if(_pageState.value - 1 >= 0) {
            _pageState.emit(_pageState.value - 1)
        }
        if(_pageState.value == 0) {
            _onBackPressedEnabled.emit(true)
            canGoBack = true
            _nextButtonAvailable.emit(true)
        }
    }

    fun setMaxPages(quantity: Int) {
        maxPages = quantity
    }

    fun setActiveButton(isActive: Boolean) = viewModelScope.launch {
        _nextButtonAvailable.emit(isActive)
    }
}