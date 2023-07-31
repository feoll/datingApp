package com.example.datingapp.data.repositories.profile

import com.example.datingapp.data.models.remote.ProfileToCreate
import com.example.datingapp.data.models.remote.Profile
import com.example.datingapp.utils.Resource
import okhttp3.MultipartBody

interface ProfileRepository {
    suspend fun getProfileToCreate(): Resource<ProfileToCreate>

    suspend fun createProfile(profile: Profile): Resource<String>

    suspend fun uploadImage(file: MultipartBody.Part): Resource<String>

    suspend fun getMyselfProfile(): Resource<Profile>

}