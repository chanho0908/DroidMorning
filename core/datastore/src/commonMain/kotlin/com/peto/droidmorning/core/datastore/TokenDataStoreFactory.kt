package com.peto.droidmorning.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createTokenDataStore(): DataStore<Preferences>

internal const val TOKEN_DATASTORE_FILENAME = "token.preferences_pb"
