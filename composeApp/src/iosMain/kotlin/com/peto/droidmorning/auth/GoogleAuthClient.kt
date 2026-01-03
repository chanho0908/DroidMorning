package com.peto.droidmorning.auth

actual class GoogleAuthClient {
    actual suspend fun signIn(): GoogleAuthResult = GoogleAuthResult.Failure("iOS에서는 아직 지원되지 않습니다")

    actual suspend fun signOut(): Result<Unit> = Result.success(Unit)
}
