package com.peto.droidmorning.domain.repository.auth

import io.github.jan.supabase.auth.user.UserInfo

sealed interface AuthType {
    data class Authenticated(
        val userInfo: UserInfo,
    ) : AuthType

    data object Unauthenticated : AuthType
}
