package com.peto.droidmorning.data.datasource.auth.local

import com.peto.droidmorning.core.datastore.TokenDataStore

class DefaultLocalAuthDataSource(
    private val tokenDataStore: TokenDataStore,
) : LocalAuthDataSource {
    override suspend fun userId(): String? = tokenDataStore.userId()

    override suspend fun hasUserId(): Boolean = tokenDataStore.hasUserId()

    override suspend fun save(userId: String) {
        tokenDataStore.save(userId)
    }

    override suspend fun clear() {
        tokenDataStore.clear()
    }
}
