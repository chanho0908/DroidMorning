package com.peto.droidmorning.di

import com.peto.droidmorning.login.vm.LoginViewModel
import com.peto.droidmorning.questions.detail.vm.QuestionDetailViewModel
import com.peto.droidmorning.questions.list.vm.QuestionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::QuestionViewModel)
        viewModelOf(::QuestionDetailViewModel)
    }
