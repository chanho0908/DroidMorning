package com.peto.droidmorning.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class DefaultTokenDataStore(
    private val dataStore: DataStore<Preferences>,
) : TokenDataStore {
    override suspend fun userId(): String? = preferences()[KEY_USER_ID]

    override suspend fun hasUserId(): Boolean = preferences()[KEY_USER_ID] != null

    override suspend fun save(userId: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
        }
    }

    private suspend fun preferences(): Preferences = dataStore.data.first()

    private companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
    }
}
