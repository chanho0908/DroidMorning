package com.peto.droidmorning.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.peto.droidmorning.auth.GoogleCredentialResult

class GoogleCredentialManager(
    private val context: Context,
    serverClientId: String,
) {
    private val credentialManager = CredentialManager.create(context)

    private val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption
            .Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverClientId)
            .build()

    private val signInWithGoogleOption: GetSignInWithGoogleOption =
        GetSignInWithGoogleOption
            .Builder(serverClientId = serverClientId)
            .build()

    private val credentialRequestWithGoogleIdOption: GetCredentialRequest =
        buildCredentialRequest(googleIdOption)

    private val credentialRequestWithSignIn: GetCredentialRequest =
        buildCredentialRequest(signInWithGoogleOption)

    suspend fun getGoogleCredentialResult(): GoogleCredentialResult {
        val googleIdOptionResponseResult: Result<GetCredentialResponse> =
            getCredentialResponseResult(credentialRequestWithGoogleIdOption)
        val googleIdOptionCredentialResult: GoogleCredentialResult =
            handleGoogleCredentialResponseResult(googleIdOptionResponseResult)

        return if (googleIdOptionCredentialResult is GoogleCredentialResult.Suspending) {
            val signInRequestResult: Result<GetCredentialResponse> =
                getCredentialResponseResult(credentialRequestWithSignIn)
            val signInCredentialResult: GoogleCredentialResult =
                handleGoogleCredentialResponseResult(signInRequestResult)
            signInCredentialResult
        } else {
            googleIdOptionCredentialResult
        }
    }

    suspend fun logOut(): Result<Unit> {
        val clearCredentialStateRequest = ClearCredentialStateRequest()
        return runCatching {
            credentialManager.clearCredentialState(clearCredentialStateRequest)
        }
    }

    private fun handleGoogleCredentialResponseResult(result: Result<GetCredentialResponse>): GoogleCredentialResult =
        result.fold(
            onSuccess = { handleGoogleCredentialResponse(it) },
            onFailure = { handleGoogleCredentialException(it) },
        )

    private fun handleGoogleCredentialResponse(response: GetCredentialResponse): GoogleCredentialResult {
        val credential = response.credential

        if (credential !is CustomCredential ||
            credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            return GoogleCredentialResult.Failure(NoCredentialException())
        }

        return try {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)
            GoogleCredentialResult.Success(googleIdTokenCredential.idToken)
        } catch (e: Throwable) {
            handleGoogleCredentialException(e)
        }
    }

    private fun handleGoogleCredentialException(exception: Throwable): GoogleCredentialResult =
        when (exception) {
            is GetCredentialCancellationException -> GoogleCredentialResult.Cancel
            is GetCredentialException -> GoogleCredentialResult.Suspending
            else -> GoogleCredentialResult.Failure(exception)
        }

    private fun buildCredentialRequest(option: CredentialOption): GetCredentialRequest =
        GetCredentialRequest
            .Builder()
            .addCredentialOption(option)
            .build()

    private suspend fun getCredentialResponseResult(request: GetCredentialRequest): Result<GetCredentialResponse> =
        runCatching { credentialManager.getCredential(context, request) }
}
