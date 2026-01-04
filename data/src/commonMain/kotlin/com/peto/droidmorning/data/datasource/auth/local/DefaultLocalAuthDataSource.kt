package com.peto.droidmorning.data.datasource.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource
import kotlinx.coroutines.flow.first

class DefaultLocalAuthDataSource(
    private val dataStore: DataStore<Preferences>,
) : LocalAuthDataSource {
    override suspend fun hasToken(): Boolean =
        dataStore.data.first().let { preferences ->
            preferences[KEY_ACCESS_TOKEN] != null && preferences[KEY_REFRESH_TOKEN] != null
        }

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
            preferences.remove(KEY_REFRESH_TOKEN)
        }
    }

    companion object Companion {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
