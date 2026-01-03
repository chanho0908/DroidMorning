package com.peto.droidmorning.data.repository

import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo

class DefaultAuthRepository(
    private val auth: Auth,
) : AuthRepository {
    override suspend fun signInWithGoogle(oauthIdToken: String): Result<UserInfo> {
        auth.signInWith(IDToken) {
            idToken = oauthIdToken
            provider = Google
        }
        return auth.currentUserOrNull()?.let { user ->
            Result.success(user)
        } ?: Result.failure(Exception("Failed to get user info"))
    }

    override suspend fun signOut(): Result<Unit> =
        runCatching {
            auth.signOut()
            Result.success(Unit)
        }.getOrThrow()

    override fun getCurrentUser(): Result<AuthType> =
        runCatching {
            auth
                .currentUserOrNull()
                ?.let {
                    AuthType.Authenticated(it)
                } ?: AuthType.Unauthenticated
        }
}
