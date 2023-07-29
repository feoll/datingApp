package com.example.datingapp.data.models.remote


import com.google.gson.annotations.SerializedName

data class GoalDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)