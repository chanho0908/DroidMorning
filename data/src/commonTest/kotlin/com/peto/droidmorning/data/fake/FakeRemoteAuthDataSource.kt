package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.remote.RemoteAuthDataSource

class FakeRemoteAuthDataSource : RemoteAuthDataSource {
    override suspend fun signIn(oauthIdToken: String): String = "test-user-id-123"

    override suspend fun signOut() {}
}
