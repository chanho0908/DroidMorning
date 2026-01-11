package com.peto.droidmorning

import com.peto.droidmorning.data.di.dataModule
import com.peto.droidmorning.di.appNavigationModule
import com.peto.droidmorning.di.platformModule
import com.peto.droidmorning.di.viewModelModule
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
                    dataModule,
                    viewModelModule,
                    appNavigationModule,
                ),
        )
    }
}
