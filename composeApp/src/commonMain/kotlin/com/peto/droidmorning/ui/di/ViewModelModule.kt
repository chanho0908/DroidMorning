package com.peto.droidmorning.ui.di

import com.peto.droidmorning.ui.vm.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::AuthViewModel)
    }
