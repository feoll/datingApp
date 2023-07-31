package com.example.datingapp.data.api

import com.example.datingapp.data.models.params.CreateUserParams
import com.example.datingapp.data.models.params.LoginParams
import com.example.datingapp.data.models.remote.AuthenticationResponse
import com.example.datingapp.data.models.remote.ProfileToCreate
import com.example.datingapp.data.models.remote.Profile
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface DeckApi {

    @GET("/user/{id}/profiles")
    suspend fun getUserSwipeList(
        @Path("id") id: Int
    ): Response<List<Profile>>
    
    @Multipart
    @POST("/user/{id}/pictures")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Path("id") id: Int
    ): Response<String>

    @POST("/auth/register")
    suspend fun registerUser(
        @Body params: CreateUserParams
    ): Response<AuthenticationResponse>

    @POST("/auth/authenticate")
    suspend fun loginUser(
        @Body params: LoginParams
    ): Response<AuthenticationResponse>

    @Headers("Content-Type: application/json")
    @GET("/user/{id}/profile/new")
    suspend fun getProfileToCreateById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ProfileToCreate>

    @Headers("Content-Type: application/json")
    @POST("/user/{id}/profile")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body profile: Profile
    ): Response<String>



    @Headers("Content-Type: application/json")
    @GET("/user/{userId}/profile/{profileId}")
    suspend fun getProfileById(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Path("profileId") profileId: Int
    ): Response<Profile>

}