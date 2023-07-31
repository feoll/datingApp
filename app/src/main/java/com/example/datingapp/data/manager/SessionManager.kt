package com.example.datingapp.data.manager

interface SessionManager {
    suspend fun updateSession(token: String, userId: Int, profileId: Int?)
    suspend fun updateToken(token: String)
    suspend fun updateUserId(id: Int)
    suspend fun updateProfileId(id: Int)
    suspend fun getToken(): String?
    suspend fun getUserId(): Int?
    suspend fun getProfileId(): Int?
    suspend fun clearSession()
}