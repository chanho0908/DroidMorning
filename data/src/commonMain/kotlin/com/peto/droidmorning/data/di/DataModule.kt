package com.peto.droidmorning.data.di

import org.koin.dsl.module

val dataModule =
    module {
        includes(
            networkModule,
            dataSourceModule,
            dataStoreModule,
            repositoryModule,
        )
    }
