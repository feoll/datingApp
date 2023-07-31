package com.example.datingapp.data.repositories.authentication

import android.content.Context
import com.example.datingapp.R
import com.example.datingapp.data.api.DeckApi
import com.example.datingapp.data.manager.SessionManager
import com.example.datingapp.data.mappers.toProfileCard
import com.example.datingapp.data.models.params.CreateUserParams
import com.example.datingapp.data.models.params.LoginParams
import com.example.datingapp.data.models.remote.AuthenticationResponse
import com.example.datingapp.utils.Resource
import com.example.datingapp.utils.isNetworkConnected
import retrofit2.Response
import java.lang.Exception

class AuthenticationRepositoryImpl(
    private val context: Context,
    private val api: DeckApi,
    private val sessionManager: SessionManager
) : AuthenticationRepository {
    override suspend fun registerUser(createUserParams: CreateUserParams): Resource<AuthenticationResponse> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error(context.getString(R.string.no_network_connection))

            val response = api.registerUser(createUserParams)
            if(response.isSuccessful) {
                response.body()?.let { auth ->
                    sessionManager.updateSession(auth.token, auth.userId, auth.profileId)
                    return Resource.Success(auth)
                }
            }

            //TODO: проверить сообщение об ошибке

            return Resource.Error("Не удалось получить данные")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Сервер не работает")
        }
    }

    override suspend fun login(param: LoginParams): Resource<AuthenticationResponse> {
        try {
            if(!isNetworkConnected(context)) return Resource.Error(context.getString(R.string.no_network_connection))

            val response = api.loginUser(param)
            if(response.isSuccessful) {
                response.body()?.let { auth ->
                    sessionManager.updateSession(auth.token, auth.userId, auth.profileId)
                    return Resource.Success(auth)
                }
            }

            //TODO: проверить сообщение об ошибке

            return Resource.Error("Не удалось получить данные")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Сервер не работает")
        }
    }
}