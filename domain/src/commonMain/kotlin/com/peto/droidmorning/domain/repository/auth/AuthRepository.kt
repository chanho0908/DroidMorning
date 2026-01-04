package com.peto.droidmorning.domain.repository.auth

interface AuthRepository {
    suspend fun authType(): AuthType

    suspend fun signIn(oauthIdToken: String): Result<Unit>

    suspend fun signOut(): Result<Unit>
}
