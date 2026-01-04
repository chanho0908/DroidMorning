package com.peto.droidmorning.domain.repository.auth

interface AuthRepository {
    suspend fun authType(): AuthType
}
