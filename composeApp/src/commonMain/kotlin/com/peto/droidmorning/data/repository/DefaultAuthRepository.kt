package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.mapper.toDomainModel
import com.peto.droidmorning.domain.model.User
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken

class DefaultAuthRepository(
    private val auth: Auth,
) : AuthRepository {
    override suspend fun signInWithGoogle(oauthIdToken: String): Result<User> {
        auth.signInWith(IDToken) {
            idToken = oauthIdToken
            provider = Google
        }
        return auth.currentUserOrNull()?.let { userInfo ->
            Result.success(userInfo.toDomainModel())
        } ?: Result.failure(Exception("Failed to get user info"))
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        auth.signOut()
    }

    override fun getCurrentUser(): Result<AuthType> =
        runCatching {
            auth
                .currentUserOrNull()
                ?.let { userInfo ->
                    AuthType.Authenticated(userInfo.toDomainModel())
                } ?: AuthType.Unauthenticated
        }
}
