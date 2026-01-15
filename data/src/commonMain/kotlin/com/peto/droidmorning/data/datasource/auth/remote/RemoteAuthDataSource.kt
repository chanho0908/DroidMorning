package com.peto.droidmorning.data.datasource.auth.remote

interface RemoteAuthDataSource {
    suspend fun signIn(oauthIdToken: String): String?

    suspend fun signOut()
}
