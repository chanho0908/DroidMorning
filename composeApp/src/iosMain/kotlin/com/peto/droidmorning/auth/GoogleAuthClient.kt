package com.peto.droidmorning.auth

import cocoapods.GoogleSignIn.GIDSignIn
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
actual class GoogleAuthClient {
    actual suspend fun signIn(): GoogleAuthResult =
        suspendCoroutine { continuation ->
            val rootViewController = getRootViewController()

            if (rootViewController == null) {
                continuation.resume(GoogleAuthResult.Failure)
                return@suspendCoroutine
            }

            signInWithGoogle(rootViewController, continuation)
        }

    actual suspend fun signOut(): Result<Unit> =
        runCatching {
            GIDSignIn.sharedInstance.signOut()
        }

    private fun signInWithGoogle(
        presentingViewController: UIViewController,
        continuation: kotlin.coroutines.Continuation<GoogleAuthResult>,
    ) {
        GIDSignIn.sharedInstance.signInWithPresentingViewController(
            presentingViewController = presentingViewController,
        ) { result, error ->
            continuation.resume(
                handleGoogleSignInResult(result, error),
            )
        }
    }

    private fun handleGoogleSignInResult(
        result: cocoapods.GoogleSignIn.GIDSignInResult?,
        error: NSError?,
    ): GoogleAuthResult =
        when {
            error != null -> handleGoogleSignInError(error)

            result?.user?.idToken?.tokenString != null ->
                GoogleAuthResult.Success(result.user.idToken!!.tokenString)

            else -> GoogleAuthResult.Failure
        }

    private fun handleGoogleSignInError(error: NSError): GoogleAuthResult =
        when (error.code) {
            -5L -> GoogleAuthResult.Cancelled
            else -> GoogleAuthResult.Failure
        }

    private fun getRootViewController(): UIViewController? =
        UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController
}
