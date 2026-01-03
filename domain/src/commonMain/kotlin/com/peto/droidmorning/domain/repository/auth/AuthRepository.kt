package com.peto.droidmorning.domain.repository.auth

interface AuthRepository {
    suspend fun signInWithGoogle(oauthIdToken: String): Result<Unit>

    suspend fun signOut(): Result<Unit>

    fun getCurrentUser(): Result<AuthType>
}
