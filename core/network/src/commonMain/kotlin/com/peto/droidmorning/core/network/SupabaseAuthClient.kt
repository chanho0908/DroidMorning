package com.peto.droidmorning.core.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken

class SupabaseAuthClient(
    private val auth: Auth,
) : AuthClient {
    override suspend fun signInWithGoogleIdToken(idToken: String): String? {
        auth.signInWith(IDToken) {
            this.idToken = idToken
            provider = Google
        }
        return currentUserId()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun currentUserId(): String? = auth.currentSessionOrNull()?.user?.id
}
