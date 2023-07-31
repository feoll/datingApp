package com.example.datingapp.data.models.params

data class CreateUserParams(
    val email: String,
    val password: String,
    val name: String,
    val age: Int
)
