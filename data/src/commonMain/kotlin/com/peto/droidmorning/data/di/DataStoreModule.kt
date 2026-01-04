package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.local.createDataStore
import org.koin.dsl.module

internal val dataStoreModule =
    module {
        single { createDataStore() }
    }
