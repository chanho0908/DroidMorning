package com.peto.droidmorning

import com.peto.droidmorning.core.datastore.di.dataStoreModule
import com.peto.droidmorning.core.network.di.networkModule
import com.peto.droidmorning.data.di.dataModule
import com.peto.droidmorning.di.navigationModule
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
                    networkModule,
                    dataStoreModule,
                    dataModule,
                    viewModelModule,
                    navigationModule,
                ),
        )
    }
}
