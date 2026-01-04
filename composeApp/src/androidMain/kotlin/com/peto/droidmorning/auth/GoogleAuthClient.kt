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
                GoogleAuthResult.Failure
            }

            GoogleCredentialResult.Cancel -> {
                GoogleAuthResult.Cancelled
            }

            GoogleCredentialResult.Suspending -> {
                GoogleAuthResult.Failure
            }
        }

    actual suspend fun signOut(): Result<Unit> = credentialManager.logOut()
}
