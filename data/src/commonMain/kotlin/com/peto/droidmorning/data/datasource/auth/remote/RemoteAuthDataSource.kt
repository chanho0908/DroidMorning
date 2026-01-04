package com.peto.droidmorning.data.datasource.auth.remote

import com.peto.droidmorning.domain.model.AuthToken

interface RemoteAuthDataSource {
    suspend fun signIn(oauthIdToken: String): AuthToken?

    suspend fun signOut()
}
