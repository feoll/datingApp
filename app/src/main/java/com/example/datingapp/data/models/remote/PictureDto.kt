package com.example.datingapp.data.models.remote


import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageLink")
    val imageLink: String,
    @SerializedName("blurHash")
    val blurHash: String
)