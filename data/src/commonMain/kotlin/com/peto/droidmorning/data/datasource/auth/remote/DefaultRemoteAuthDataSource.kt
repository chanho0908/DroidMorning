package com.peto.droidmorning.data.datasource.auth.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken

class DefaultRemoteAuthDataSource(
    client: SupabaseClient,
) : RemoteAuthDataSource {
    private val auth: Auth = client.auth

    override suspend fun signIn(oauthIdToken: String): Result<Unit> {
        auth.signInWith(IDToken) {
            idToken = oauthIdToken
            provider = Google
        }
        return auth.currentUserOrNull()?.let {
            Result.success(Unit)
        } ?: Result.failure(Exception("Failed to get user info"))
    }

    override suspend fun signOut(): Result<Unit> = runCatching { auth.signOut() }
}
