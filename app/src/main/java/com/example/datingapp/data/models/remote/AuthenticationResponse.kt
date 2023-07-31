package com.example.datingapp.data.models.remote

data class AuthenticationResponse(
    val userId: Int,
    val profileId: Int?,
    val token: String
)
