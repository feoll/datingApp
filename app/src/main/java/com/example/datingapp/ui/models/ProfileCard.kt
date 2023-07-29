package com.example.datingapp.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileCard(
    val profileId: Int,
    val userId: Int,
    val height: Int?,
    val gender: String?,
    val name: String,
    val age: Int?,
    val about: String?,
    val country: String?,
    val city: String?,
    val zodiac: String?,
    val goal: String?,
    val sportAttitude: String?,
    val alcoholAttitude: String?,
    val smokingAttitude: String?,
    val petAttitude: String?,
    val hobby: List<String>,
    val imageUrls: List<String>,
    val blurs: List<String>,
    var selectedImage: Int = 0
): Parcelable