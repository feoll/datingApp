package com.example.datingapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datingapp.data.common.SESSION_MANAGER
import com.example.datingapp.data.common.SESSION_MANAGER_PROFILE_ID
import com.example.datingapp.data.common.SESSION_MANAGER_TOKEN
import com.example.datingapp.data.common.SESSION_MANAGER_USER_ID
import kotlinx.coroutines.flow.first

class SessionManagerImpl(
    private val context: Context
) : SessionManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SESSION_MANAGER)
    override suspend fun updateSession(token: String, userId: Int, profileId: Int?) {
        val tokenKey = stringPreferencesKey(SESSION_MANAGER_TOKEN)
        val userIdKey = intPreferencesKey(SESSION_MANAGER_USER_ID)
        val profileIdKey = intPreferencesKey(SESSION_MANAGER_PROFILE_ID)
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
            preferences[userIdKey] = userId
            profileId?.let { id ->
                preferences[profileIdKey] = id
            }
        }
    }

    override suspend fun updateToken(token: String) {
        val key = stringPreferencesKey(SESSION_MANAGER_TOKEN)
        context.dataStore.edit { preferences ->
            preferences[key] = token
        }
    }

    override suspend fun updateUserId(id: Int) {
        val key = intPreferencesKey(SESSION_MANAGER_USER_ID)
        context.dataStore.edit { preferences ->
            preferences[key] = id
        }
    }

    override suspend fun updateProfileId(id: Int) {
        val key = intPreferencesKey(SESSION_MANAGER_PROFILE_ID)
        context.dataStore.edit { preferences ->
            preferences[key] = id
        }
    }

    override suspend fun getToken(): String? {
        val key = stringPreferencesKey(SESSION_MANAGER_TOKEN)
        val preferences = context.dataStore.data.first()
        return preferences[key]
    }

    override suspend fun getUserId(): Int? {
        val key = intPreferencesKey(SESSION_MANAGER_USER_ID)
        val preferences = context.dataStore.data.first()
        return preferences[key]
    }

    override suspend fun getProfileId(): Int? {
        val key = intPreferencesKey(SESSION_MANAGER_PROFILE_ID)
        val preferences = context.dataStore.data.first()
        return preferences[key]
    }

    override suspend fun clearSession() {
        context.dataStore.edit {
            it.clear()
        }
    }
}