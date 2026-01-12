package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource

class FakeLocalAuthDataSource : LocalAuthDataSource {
    private var userIdValue: String? = null

    override suspend fun userId(): String? = userIdValue

    override suspend fun hasUserId(): Boolean = userIdValue != null

    override suspend fun save(userId: String) {
        this.userIdValue = userId
    }

    override suspend fun clear() {
        userIdValue = null
    }
}
