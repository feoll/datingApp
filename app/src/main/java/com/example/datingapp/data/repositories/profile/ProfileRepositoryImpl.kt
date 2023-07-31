package com.example.datingapp.data.repositories.profile

import android.content.Context
import com.example.datingapp.R
import com.example.datingapp.data.api.DeckApi
import com.example.datingapp.data.manager.SessionManager
import com.example.datingapp.data.models.remote.ProfileToCreate
import com.example.datingapp.data.models.remote.Profile
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isNetworkConnected
import okhttp3.MultipartBody
import java.lang.Exception

class ProfileRepositoryImpl(
    private val context: Context,
    private val api: DeckApi,
    private val sessionManager: SessionManager
) : ProfileRepository {
    override suspend fun getProfileToCreate(): Resource<ProfileToCreate> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error(context.getString(R.string.no_network_connection))

            val userId = sessionManager.getUserId()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            val token = sessionManager.getToken()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            val response = api.getProfileToCreateById(token = "Bearer " + token, id = userId)
            if(response.isSuccessful) {
                response.body()?.let { information ->
                    return Resource.Success(information)
                }
            }

            //TODO: проверить сообщение об ошибке

            return Resource.Error("Cannot get data")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }
    }

    override suspend fun createProfile(profile: Profile): Resource<String> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error(context.getString(R.string.no_network_connection))

            val userId = sessionManager.getUserId()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            val token = sessionManager.getToken()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            //todo save id profile

            val response = api.createProfile(token = "Bearer " + token, id = userId, profile = profile)
            if(response.isSuccessful) {
                response.body()?.let { information ->
                    return Resource.Success(information)
                }
            }

            //TODO: проверить сообщение об ошибке

            return Resource.Error("Cannot get data")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }
    }

    override suspend fun uploadImage(file: MultipartBody.Part): Resource<String> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error(context.getString(R.string.no_network_connection))

            val userId = sessionManager.getUserId()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            val token = sessionManager.getToken()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            val response = api.uploadImage(token = "Bearer " + token, id = userId, file = file)
            if(response.isSuccessful) {
                response.body()?.let { information ->
                    return Resource.Success(information)
                }
            }

            //TODO: проверить сообщение об ошибке

            return Resource.Error("Cannot get data")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }
    }

    override suspend fun getMyselfProfile(): Resource<Profile> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error(context.getString(R.string.no_network_connection))

            val userId = sessionManager.getUserId()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            val token = sessionManager.getToken()
                ?: return Resource.Error(context.getString(R.string.not_authorized))

            //todo remove profile id
            val profileId = 19

            val response = api.getProfileById(token = "Bearer " + token, userId = userId, profileId = profileId)
            if(response.isSuccessful) {
                response.body()?.let { profile ->
                    return Resource.Success(profile)
                }
            }

            //TODO: проверить сообщение об ошибке

            return Resource.Error("Cannot get data")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong")
        }
    }


}