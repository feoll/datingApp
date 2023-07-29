package com.example.datingapp.data.models.remote


import com.google.gson.annotations.SerializedName

data class ZodiacSignDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)