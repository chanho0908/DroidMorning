package com.peto.droidmorning.data.datasource.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.peto.droidmorning.domain.model.AuthToken
import kotlinx.coroutines.flow.first

class DefaultLocalAuthDataSource(
    private val dataStore: DataStore<Preferences>,
) : LocalAuthDataSource {
    override suspend fun accessToken(): String? = getPreferences()[KEY_ACCESS_TOKEN]

    override suspend fun refreshToken(): String? = getPreferences()[KEY_REFRESH_TOKEN]

    override suspend fun hasToken(): Boolean =
        getPreferences().let { preferences ->
            preferences[KEY_ACCESS_TOKEN] != null &&
                preferences[KEY_REFRESH_TOKEN] != null
        }

    override suspend fun saveTokens(authToken: AuthToken) {
        dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = authToken.accessToken
            preferences[KEY_REFRESH_TOKEN] = authToken.refreshToken
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
            preferences.remove(KEY_REFRESH_TOKEN)
        }
    }

    private suspend fun getPreferences(): Preferences = dataStore.data.first()

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
