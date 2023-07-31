package com.example.datingapp.data.models.remote


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("id")
    val id: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("about")
    val about: String?,
    @SerializedName("userDto")
    val userDto: UserDto,
    @SerializedName("cityDto")
    val cityDto: CityDto?,
    @SerializedName("zodiacSignDto")
    val zodiacSignDto: ZodiacSignDto?,
    @SerializedName("goalDto")
    val goalDto: GoalDto?,
    @SerializedName("sportAttitudeDto")
    val sportAttitudeDto: SportAttitudeDto?,
    @SerializedName("alcoholAttitudeDto")
    val alcoholAttitudeDto: AlcoholAttitudeDto?,
    @SerializedName("smokingAttitudeDto")
    val smokingAttitudeDto: SmokingAttitudeDto?,
    @SerializedName("petAttitudeDto")
    val petAttitudeDto: PetAttitudeDto?,
    @SerializedName("hobbyDtoList")
    val hobbyDtoList: List<HobbyDto>,
    @SerializedName("pictureDtoList")
    val pictureDtoList: List<PictureDto>
)