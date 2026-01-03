package com.peto.droidmorning.domain.repository.auth

import com.peto.droidmorning.domain.model.User

interface AuthRepository {
    suspend fun signInWithGoogle(oauthIdToken: String): Result<User>

    suspend fun signOut(): Result<Unit>

    fun getCurrentUser(): Result<AuthType>
}
