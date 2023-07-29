package com.example.datingapp.data.api

import com.example.datingapp.data.models.remote.NewProfile
import com.example.datingapp.data.models.remote.Profile
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface DeckApi {

    @GET("/user/{id}/profiles")
    suspend fun getUserSwipeList(
        @Path("id") id: Int
    ): Response<List<Profile>>

    @GET("/user/{id}/profile/new")
    suspend fun getNewUserProfile(
        @Path("id") id: Int
    ): Response<NewProfile>

    @Multipart
    @POST("/user/8/pictures")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    )

}