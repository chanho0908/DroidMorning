package com.peto.droidmorning.data.datasource.auth.local

interface LocalAuthDataSource {
    suspend fun hasToken(): Boolean

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    )

    suspend fun clear()
}
