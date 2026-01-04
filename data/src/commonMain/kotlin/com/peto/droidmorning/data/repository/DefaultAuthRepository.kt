package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource
import com.peto.droidmorning.data.datasource.auth.remote.RemoteAuthDataSource
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthType

class DefaultAuthRepository(
    private val remoteDataSource: RemoteAuthDataSource,
    private val localDataSource: LocalAuthDataSource,
) : AuthRepository {
    override suspend fun authType(): AuthType =
        when (localDataSource.hasToken()) {
            true -> AuthType.Authenticated
            false -> AuthType.Unauthenticated
        }

    override suspend fun signIn(oauthIdToken: String): Result<Unit> =
        runCatching {
            remoteDataSource.signIn(oauthIdToken)?.let { authToken ->
                localDataSource.saveTokens(authToken)
            }
        }

    override suspend fun signOut(): Result<Unit> =
        runCatching {
            remoteDataSource.signOut()
            localDataSource.clear()
        }
}
