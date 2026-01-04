package com.peto.droidmorning.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = TOKEN_DATASTORE_FILENAME,
)

actual fun createDataStore(): DataStore<Preferences> = DataStoreProvider.dataStore

private object DataStoreProvider : KoinComponent {
    private val context: Context by inject()
    val dataStore: DataStore<Preferences>
        get() = context.dataStore
}
