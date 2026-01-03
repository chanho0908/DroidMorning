package com.peto.droidmorning.auth

expect class GoogleAuthClient {
    suspend fun signIn(): GoogleAuthResult

    suspend fun signOut(): Result<Unit>
}
