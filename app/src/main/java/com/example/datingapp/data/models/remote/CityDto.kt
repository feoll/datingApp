package com.example.datingapp.data.models.remote


import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("countryDto")
    val countryDto: CountryDto,
    @SerializedName("name")
    val name: String
)