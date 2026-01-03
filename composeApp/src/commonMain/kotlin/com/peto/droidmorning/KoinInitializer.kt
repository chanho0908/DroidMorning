package com.peto.droidmorning

import com.peto.droidmorning.data.di.networkModule
import com.peto.droidmorning.data.di.repositoryModule
import com.peto.droidmorning.di.platformModule
import com.peto.droidmorning.presentation.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    extraModules: List<Module> = emptyList(),
    declaration: KoinAppDeclaration = {},
) {
    startKoin {
        declaration()

        modules(
            extraModules +
                listOf(
                    platformModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                ),
        )
    }
}
