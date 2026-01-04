package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource

class FakeLocalAuthDataSource : LocalAuthDataSource {
    private var accessToken: String? = null
    private var refreshToken: String? = null

    override suspend fun hasToken(): Boolean = accessToken != null && refreshToken != null

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    override suspend fun clear() {
        accessToken = null
        refreshToken = null
    }
}
