package com.peto.droidmorning.data.datasource.auth.local

interface LocalAuthDataSource {
    suspend fun userId(): String?

    suspend fun hasUserId(): Boolean

    suspend fun save(userId: String)

    suspend fun clear()
}
