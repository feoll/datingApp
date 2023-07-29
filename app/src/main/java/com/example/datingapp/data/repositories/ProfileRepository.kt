package com.example.datingapp.data.repositories

import com.example.datingapp.data.models.remote.NewProfile
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.utils.Resource

interface ProfileRepository {
    suspend fun getNewProfile(profileId: Int): Resource<NewProfile>
}