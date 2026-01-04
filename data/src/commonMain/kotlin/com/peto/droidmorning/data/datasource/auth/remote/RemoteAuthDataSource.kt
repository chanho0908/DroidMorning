package com.peto.droidmorning.data.datasource.auth.remote

interface RemoteAuthDataSource {
    suspend fun signIn(oauthIdToken: String): Result<Unit>

    suspend fun signOut(): Result<Unit>
}
