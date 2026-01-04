package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource
import com.peto.droidmorning.domain.model.AuthToken

class FakeLocalAuthDataSource : LocalAuthDataSource {
    private var _accessToken: String? = null
    private var _refreshToken: String? = null

    override suspend fun accessToken(): String? = _accessToken

    override suspend fun refreshToken(): String? = _refreshToken

    override suspend fun hasToken(): Boolean = _accessToken != null && _refreshToken != null

    override suspend fun saveTokens(authToken: AuthToken) {
        this._accessToken = authToken.accessToken
        this._refreshToken = authToken.refreshToken
    }

    override suspend fun clear() {
        _accessToken = null
        _refreshToken = null
    }
}
