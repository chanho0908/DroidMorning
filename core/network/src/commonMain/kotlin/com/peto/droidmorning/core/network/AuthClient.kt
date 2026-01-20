package com.peto.droidmorning.core.network

interface AuthClient {
    suspend fun signInWithGoogleIdToken(idToken: String): String?

    suspend fun signOut()

    fun currentUserId(): String?
}
