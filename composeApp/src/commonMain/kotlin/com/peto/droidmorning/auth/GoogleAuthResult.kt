package com.peto.droidmorning.auth

sealed interface GoogleAuthResult {
    data class Success(
        val idToken: String,
    ) : GoogleAuthResult

    data object Failure : GoogleAuthResult

    data object Cancelled : GoogleAuthResult
}
