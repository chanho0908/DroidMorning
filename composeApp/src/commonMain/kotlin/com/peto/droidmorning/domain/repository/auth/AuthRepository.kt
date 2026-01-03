package com.peto.droidmorning.domain.repository.auth

import io.github.jan.supabase.auth.user.UserInfo

interface AuthRepository {
    suspend fun signInWithGoogle(oauthIdToken: String): Result<UserInfo>

    suspend fun signOut(): Result<Unit>

    fun getCurrentUser(): Result<AuthType>
}
