package com.example.datingapp.data.repositories.authentication

import com.example.datingapp.data.models.params.CreateUserParams
import com.example.datingapp.data.models.params.LoginParams
import com.example.datingapp.data.models.remote.AuthenticationResponse
import com.example.datingapp.utils.Resource
import retrofit2.Response

interface AuthenticationRepository {
    suspend fun registerUser(createUserParams: CreateUserParams): Resource<AuthenticationResponse>

    suspend fun login(param: LoginParams): Resource<AuthenticationResponse>
}