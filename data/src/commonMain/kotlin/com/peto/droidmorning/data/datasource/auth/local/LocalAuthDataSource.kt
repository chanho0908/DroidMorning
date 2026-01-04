package com.peto.droidmorning.data.datasource.auth.local

import com.peto.droidmorning.domain.model.AuthToken

interface LocalAuthDataSource {
    suspend fun accessToken(): String?

    suspend fun refreshToken(): String?

    suspend fun hasToken(): Boolean

    suspend fun saveTokens(authToken: AuthToken)

    suspend fun clear()
}
