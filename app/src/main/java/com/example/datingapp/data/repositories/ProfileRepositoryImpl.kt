package com.example.datingapp.data.repositories

import android.content.Context
import com.example.datingapp.data.api.DeckApi
import com.example.datingapp.data.mappers.toProfileCard
import com.example.datingapp.data.models.remote.NewProfile
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isNetworkConnected
import java.lang.Exception

class ProfileRepositoryImpl(
    private val context: Context,
    private val api: DeckApi
) : ProfileRepository {
    override suspend fun getNewProfile(profileId: Int): Resource<NewProfile> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error("No Internet connection")

            val responseProfileList = api.getNewUserProfile(id = profileId)
            if(responseProfileList.isSuccessful) {
                responseProfileList.body()?.let { profile ->
                    return Resource.Success(profile)
                }
            }

            return Resource.Error("Cannot get data")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }
    }
}