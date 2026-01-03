package com.peto.droidmorning.di

import com.peto.droidmorning.auth.GoogleAuthClient
import org.koin.dsl.module

actual val platformModule =
    module {
        single { GoogleAuthClient() }
    }
