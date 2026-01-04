package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.local.createTokenDataStore
import org.koin.dsl.module

internal val dataStoreModule =
    module {
        single { createTokenDataStore() }
    }
