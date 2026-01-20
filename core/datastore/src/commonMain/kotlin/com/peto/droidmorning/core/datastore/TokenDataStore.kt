package com.peto.droidmorning.core.datastore

interface TokenDataStore {
    suspend fun userId(): String?

    suspend fun hasUserId(): Boolean

    suspend fun save(userId: String)

    suspend fun clear()
}
