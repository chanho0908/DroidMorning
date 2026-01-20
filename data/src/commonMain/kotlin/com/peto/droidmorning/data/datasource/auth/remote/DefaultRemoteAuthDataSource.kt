package com.peto.droidmorning.data.datasource.auth.remote

import com.peto.droidmorning.core.network.AuthClient

class DefaultRemoteAuthDataSource(
    private val authClient: AuthClient,
) : RemoteAuthDataSource {
    override suspend fun signIn(oauthIdToken: String): String? = authClient.signInWithGoogleIdToken(oauthIdToken)

    override suspend fun signOut() = authClient.signOut()
}
