package com.peto.droidmorning.di

import com.peto.droidmorning.login.vm.LoginViewModel
import com.peto.droidmorning.question.vm.QuestionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::QuestionViewModel)
    }
