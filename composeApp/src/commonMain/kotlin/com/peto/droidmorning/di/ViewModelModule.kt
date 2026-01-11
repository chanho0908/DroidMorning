package com.peto.droidmorning.di

import com.peto.droidmorning.login.vm.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::LoginViewModel)
    }
