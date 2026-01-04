package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.remote.RemoteAuthDataSource

class FakeRemoteAuthDataSource : RemoteAuthDataSource {
    private var shouldSucceed = true
    private var signInCallCount = 0
    private var signOutCallCount = 0

    fun setShouldSucceed(shouldSucceed: Boolean) {
        this.shouldSucceed = shouldSucceed
    }

    fun getSignInCallCount() = signInCallCount

    fun getSignOutCallCount() = signOutCallCount

    override suspend fun signIn(oauthIdToken: String): Result<Unit> {
        signInCallCount++
        return if (shouldSucceed) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Sign in failed"))
        }
    }

    override suspend fun signOut(): Result<Unit> {
        signOutCallCount++
        return if (shouldSucceed) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Sign out failed"))
        }
    }
}
