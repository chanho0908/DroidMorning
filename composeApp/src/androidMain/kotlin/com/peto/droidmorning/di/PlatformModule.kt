package com.peto.droidmorning.di

import com.peto.droidmorning.BuildKonfig
import com.peto.droidmorning.auth.GoogleAuthClient
import com.peto.droidmorning.auth.GoogleCredentialManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule =
    module {
        single {
            GoogleCredentialManager(
                context = androidContext(),
                serverClientId = BuildKonfig.GOOGLE_CLIENT_ID,
            )
        }

        single { GoogleAuthClient(get()) }
    }
