package com.example.datingapp.data.mappers

import com.example.datingapp.data.models.remote.Profile
import com.example.datingapp.ui.models.ProfileCard

fun Profile.toProfileCard(): ProfileCard {
    return ProfileCard(
        profileId = id,
        userId = userDto.id,
        height = height,
        gender = gender,
        about = about,
        name = userDto.name,
        age = userDto.age,
        city = cityDto?.name,
        country = cityDto?.countryDto?.name,
        zodiac = zodiacSignDto?.name,
        goal = goalDto?.name,
        sportAttitude = sportAttitudeDto?.name,
        alcoholAttitude = alcoholAttitudeDto?.name,
        smokingAttitude = smokingAttitudeDto?.name,
        petAttitude = petAttitudeDto?.name,
        hobby = hobbyDtoList.map { it.name },
        imageUrls = pictureDtoList.map { it.imageLink },
        blurs = pictureDtoList.map { it.blurHash }
    )
}