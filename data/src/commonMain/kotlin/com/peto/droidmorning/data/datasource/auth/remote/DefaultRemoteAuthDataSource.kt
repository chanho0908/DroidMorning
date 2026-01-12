package com.peto.droidmorning.data.datasource.auth.remote

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken

class DefaultRemoteAuthDataSource(
    private val auth: Auth,
) : RemoteAuthDataSource {
    override suspend fun signIn(oauthIdToken: String): String? {
        auth.signInWith(IDToken) {
            idToken = oauthIdToken
            provider = Google
        }
        return auth.currentSessionOrNull()?.user?.id
    }

    override suspend fun signOut() = auth.signOut()
}
