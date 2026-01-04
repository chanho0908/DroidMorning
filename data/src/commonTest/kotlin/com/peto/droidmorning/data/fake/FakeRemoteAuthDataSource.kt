package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.remote.RemoteAuthDataSource
import com.peto.droidmorning.domain.model.AuthToken

class FakeRemoteAuthDataSource : RemoteAuthDataSource {
    override suspend fun signIn(oauthIdToken: String): AuthToken =
        AuthToken(
            accessToken = "newAccessToken",
            refreshToken = "newRefreshToken",
        )

    override suspend fun signOut() {}
}
