package com.peto.droidmorning.auth

sealed interface GoogleAuthResult {
    data class Success(
        val idToken: String,
    ) : GoogleAuthResult

    data class Failure(
        val message: String,
    ) : GoogleAuthResult

    data object Cancelled : GoogleAuthResult
}
