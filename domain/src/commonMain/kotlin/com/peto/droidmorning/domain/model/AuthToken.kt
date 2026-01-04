package com.peto.droidmorning.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
