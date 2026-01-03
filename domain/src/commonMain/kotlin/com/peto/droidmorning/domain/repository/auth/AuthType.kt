package com.peto.droidmorning.domain.repository.auth

sealed interface AuthType {
    data object Authenticated : AuthType

    data object Unauthenticated : AuthType
}
