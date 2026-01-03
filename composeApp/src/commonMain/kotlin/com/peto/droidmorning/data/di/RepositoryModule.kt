package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.repository.DefaultAuthRepository
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import org.koin.dsl.module

val repositoryModule =
    module {
        single<AuthRepository> { DefaultAuthRepository(get()) }
    }
