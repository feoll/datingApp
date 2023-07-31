package com.example.datingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datingapp.data.models.remote.AlcoholAttitudeDto
import com.example.datingapp.data.models.remote.CityDto
import com.example.datingapp.data.models.remote.ProfileToCreate
import com.example.datingapp.data.models.remote.GoalDto
import com.example.datingapp.data.models.remote.PetAttitudeDto
import com.example.datingapp.data.models.remote.Profile
import com.example.datingapp.data.models.remote.SportAttitudeDto
import com.example.datingapp.data.models.remote.ZodiacSignDto
import com.example.datingapp.data.repositories.profile.ProfileRepository
import com.example.datingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileFillerViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _maxPages = MutableStateFlow(0)
    val maxPages = _maxPages.asStateFlow()
    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _profileToCreateState = MutableStateFlow<Resource<ProfileToCreate>>(Resource.Undefined())
    val profileToCreateState = _profileToCreateState.asStateFlow()

    private val _createProfileState = MutableStateFlow<Resource<String>>(Resource.Undefined())
    val createProfileState = _createProfileState.asStateFlow()

    private val _onBackPressedCallbackState = MutableStateFlow(false)
    val onBackPressedCallbackState = _onBackPressedCallbackState.asStateFlow()

    var height: Int = 130
    var city: String? = null
    var country: String? = null
    var about: String? = null
    var gender: String? = null
    var smoke: String? = null
    var zodiac: String? = null
    var goal: String? = null
    var sport: String? = null
    var alcohol: String? = null
    var pet: String? = null
    var hobby: List<String>? = null
    var file: MultipartBody.Part? = null

    fun createProfile() = viewModelScope.launch {
        val height = height
        val city = city
        val country = country
        val about = about
        val gender = gender
        val smoke = smoke
        val zodiac = zodiac
        val goal = goal
        val sport = sport
        val alcohol = alcohol
        val pet = pet
        val hobby = hobby

        if (city == null
            || country == null
            || about == null
            || gender == null
            || zodiac == null
            || goal == null
            || sport == null
            || alcohol == null
            || pet == null
            || hobby == null
            || smoke == null
            ) {
            //todo show message
            return@launch
        }


        _createProfileState.emit(Resource.Loading())

        val profile = profileToCreateState.value
        if (profile is Resource.Success) {
            val cityDtoResponse = profile.data.cityDtoList.find { it.countryDto.name == country && it.name == city }
            val zodiacSignDto = profile.data.zodiacSignDtoList.find { it.name == zodiac }
            val goalDto = profile.data.goalDtoList.find { it.name == goal }
            val sportAttitudeDto = profile.data.sportAttitudeDtoList.find { it.name == sport }
            val alcoholAttitudeDto = profile.data.alcoholAttitudeDtoList.find { it.name == alcohol }
            val smokingAttitudeDto = profile.data.smokingAttitudeDtoList.find { it.name == smoke }
            val petAttitudeDto = profile.data.petAttitudeDtoList.find { it.name == pet }
            val hobbyDto = profile.data.hobbyDtoList.filter { hobby.contains(it.name) }

            val sendProfile = Profile(
                id = 0,
                height = height,
                gender = gender,
                about = about,
                userDto = profile.data.profileDto.userDto,
                cityDto = cityDtoResponse,
                zodiacSignDto = zodiacSignDto,
                goalDto = goalDto,
                sportAttitudeDto = sportAttitudeDto,
                alcoholAttitudeDto = alcoholAttitudeDto,
                smokingAttitudeDto = smokingAttitudeDto,
                petAttitudeDto = petAttitudeDto,
                hobbyDtoList = hobbyDto,
                pictureDtoList = emptyList()
            )


            val result  = repository.createProfile(sendProfile)
            if(result is Resource.Success) {
                //load image
                val file = file
                file?.let {
                    val resultImage = repository.uploadImage(file)
                    if (resultImage is Resource.Success) {
                        _createProfileState.emit(result)
                    } else if (resultImage is Resource.Error) {
                        _createProfileState.emit(result)
                    }
                }
            } else {
                _createProfileState.emit(result)
            }
        }
    }


    fun loadProfileToCreate() = viewModelScope.launch(Dispatchers.IO) {
        _profileToCreateState.emit(Resource.Loading())
        delay(1000)
        val response = repository.getProfileToCreate()
        _profileToCreateState.emit(response)
    }

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

    init {
        loadProfileToCreate()
    }
}