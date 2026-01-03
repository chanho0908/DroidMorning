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
                    result.exception?.message ?: ERROR_LOGIN_FAILED,
                )
            }

            GoogleCredentialResult.Cancel -> {
                GoogleAuthResult.Cancelled
            }

            GoogleCredentialResult.Suspending -> {
                GoogleAuthResult.Failure(ERROR_LOGIN_SUSPENDED)
            }
        }

    actual suspend fun signOut(): Result<Unit> = credentialManager.logOut()

    companion object {
        private const val ERROR_LOGIN_FAILED = "로그인에 실패했습니다."
        private const val ERROR_LOGIN_SUSPENDED = "로그인 중단되었습니다."
    }
}
