package com.peto.droidmorning.auth

actual class GoogleAuthClient(
    private val credentialManager: GoogleCredentialManager,
) {
    actual suspend fun signIn(): GoogleAuthResult =
        when (val result = credentialManager.getGoogleCredentialResult()) {
            is GoogleCredentialResult.Success -> {
                GoogleAuthResult.Success(result.idToken)
            }
            is GoogleCredentialResult.Failure -> {
                GoogleAuthResult.Failure(
                    result.exception?.message ?: "로그인에 실패했습니다",
                )
            }
            GoogleCredentialResult.Cancel -> {
                GoogleAuthResult.Cancelled
            }
            GoogleCredentialResult.Suspending -> {
                GoogleAuthResult.Failure("로그인 중단됨")
            }
        }

    actual suspend fun signOut(): Result<Unit> = credentialManager.logOut()
}
