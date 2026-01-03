package com.peto.droidmorning.data.repository

import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken

class DefaultAuthRepository(
    private val auth: Auth,
) : AuthRepository {
    override suspend fun signInWithGoogle(oauthIdToken: String): Result<Unit> {
        auth.signInWith(IDToken) {
            idToken = oauthIdToken
            provider = Google
        }
        return auth.currentUserOrNull()?.let {
            Result.success(Unit)
        } ?: Result.failure(Exception("Failed to get user info"))
    }

    override suspend fun signOut(): Result<Unit> =
        runCatching {
            auth.signOut()
        }

    override fun getCurrentUser(): Result<AuthType> =
        runCatching {
            auth
                .currentUserOrNull()
                ?.let { userInfo ->
                    AuthType.Authenticated
                } ?: AuthType.Unauthenticated
        }
}
