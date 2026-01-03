package com.peto.droidmorning.presentation.di

import com.peto.droidmorning.presentation.vm.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::AuthViewModel)
    }
