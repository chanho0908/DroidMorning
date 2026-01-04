package com.peto.droidmorning.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(): DataStore<Preferences>

internal const val TOKEN_DATASTORE_FILENAME = "token.preferences_pb"
