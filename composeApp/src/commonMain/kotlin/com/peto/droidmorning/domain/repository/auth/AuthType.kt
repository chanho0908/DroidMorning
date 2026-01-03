package com.peto.droidmorning.domain.repository.auth

import com.peto.droidmorning.domain.model.User

sealed interface AuthType {
    data class Authenticated(
        val user: User,
    ) : AuthType

    data object Unauthenticated : AuthType
}
