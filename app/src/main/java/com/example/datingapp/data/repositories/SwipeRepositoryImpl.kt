package com.example.datingapp.data.repositories

import android.content.Context
import com.example.datingapp.data.api.DeckApi
import com.example.datingapp.data.mappers.toProfileCard
import com.example.datingapp.data.models.remote.Profile
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isNetworkConnected
import java.lang.Exception

class SwipeRepositoryImpl(
    private val context: Context,
    private val api: DeckApi
) : SwipeRepository {
    override suspend fun getProfileSwipeList(profileId: Int): Resource<List<ProfileCard>> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error("No Internet connection")

            val responseProfileList = api.getUserSwipeList(id = profileId)
            if(responseProfileList.isSuccessful) {
                responseProfileList.body()?.let { profiles ->
                    return Resource.Success(profiles.map { it.toProfileCard() })
                }
            }

            return Resource.Error("Cannot get data")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }
    }
}