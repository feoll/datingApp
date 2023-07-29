package com.example.datingapp.data.repositories

import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.utils.Resource

interface SwipeRepository {
    suspend fun getProfileSwipeList(profileId: Int): Resource<List<ProfileCard>>
}