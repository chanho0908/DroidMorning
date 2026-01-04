package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource
import com.peto.droidmorning.domain.model.AuthToken

class FakeLocalAuthDataSource : LocalAuthDataSource {
    private var accessTokenValue: String? = null
    private var refreshTokenValue: String? = null

    override suspend fun accessToken(): String? = accessTokenValue

    override suspend fun refreshToken(): String? = refreshTokenValue

    override suspend fun hasToken(): Boolean = accessTokenValue != null && refreshTokenValue != null

    override suspend fun saveTokens(authToken: AuthToken) {
        this.accessTokenValue = authToken.accessToken
        this.refreshTokenValue = authToken.refreshToken
    }

    override suspend fun clear() {
        accessTokenValue = null
        refreshTokenValue = null
    }
}
