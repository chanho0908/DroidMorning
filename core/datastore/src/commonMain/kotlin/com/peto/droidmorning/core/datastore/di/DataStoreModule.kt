package com.peto.droidmorning.core.datastore.di

import com.peto.droidmorning.core.datastore.DefaultTokenDataStore
import com.peto.droidmorning.core.datastore.TokenDataStore
import com.peto.droidmorning.core.datastore.createTokenDataStore
import org.koin.dsl.module

val dataStoreModule =
    module {
        single { createTokenDataStore() }
        single<TokenDataStore> { DefaultTokenDataStore(get()) }
    }
