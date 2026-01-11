package com.peto.droidmorning.di

import com.peto.droidmorning.login.navigation.LoginNavGraphContributor
import com.peto.droidmorning.main.navigation.MainNavGraphContributor
import com.peto.droidmorning.navigation.NavGraphContributor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val navigationModule =
    module {
        single<NavGraphContributor>(named("login")) { LoginNavGraphContributor() }
        single<NavGraphContributor>(named("main")) { MainNavGraphContributor() }
    }
