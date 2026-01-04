package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.datasource.auth.local.DefaultLocalAuthDataSource
import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource
import com.peto.droidmorning.data.datasource.auth.remote.DefaultRemoteAuthDataSource
import com.peto.droidmorning.data.datasource.auth.remote.RemoteAuthDataSource
import org.koin.dsl.module

internal val dataSourceModule =
    module {
        single<LocalAuthDataSource> { DefaultLocalAuthDataSource(get()) }
        single<RemoteAuthDataSource> { DefaultRemoteAuthDataSource(get()) }
    }
