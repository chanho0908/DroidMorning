package com.peto.droidmorning.data.datasource.auth.remote

import com.peto.droidmorning.domain.model.AuthToken
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken

class DefaultRemoteAuthDataSource(
    private val auth: Auth,
) : RemoteAuthDataSource {
    override suspend fun signIn(oauthIdToken: String): AuthToken? {
        auth.signInWith(IDToken) {
            idToken = oauthIdToken
            provider = Google
        }
        return auth.currentSessionOrNull()?.let {
            AuthToken(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken,
            )
        }
    }

    override suspend fun signOut() = auth.signOut()
}
