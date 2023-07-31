package com.example.datingapp.utils

import android.text.TextUtils

fun isEmailValid(email: String): Boolean {
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isNameValid(name: String): Boolean {
    return name.length > 2
}

fun isPasswordValid(password: String): Boolean {
    return password.length > 1
}