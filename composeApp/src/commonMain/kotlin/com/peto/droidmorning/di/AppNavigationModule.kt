package com.peto.droidmorning.di

import com.peto.droidmorning.login.navigation.LoginNavGraphContributor
import com.peto.droidmorning.navigation.NavGraphContributor
import org.koin.dsl.module

val appNavigationModule =
    module {
        factory<NavGraphContributor> { LoginNavGraphContributor() }
    }
