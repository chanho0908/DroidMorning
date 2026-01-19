package com.peto.droidmorning.di

import com.peto.droidmorning.exam.complete.vm.ExamCompleteViewModel
import com.peto.droidmorning.exam.detail.vm.ExamDetailViewModel
import com.peto.droidmorning.exam.main.vm.ExamViewModel
import com.peto.droidmorning.exam.progress.vm.ExamProgressViewModel
import com.peto.droidmorning.login.vm.LoginViewModel
import com.peto.droidmorning.main.vm.MainViewModel
import com.peto.droidmorning.questions.detail.vm.QuestionDetailViewModel
import com.peto.droidmorning.questions.list.vm.QuestionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::QuestionViewModel)
        viewModelOf(::QuestionDetailViewModel)
        viewModelOf(::ExamViewModel)
        viewModelOf(::ExamProgressViewModel)
        viewModelOf(::ExamDetailViewModel)
        viewModelOf(::ExamCompleteViewModel)
    }
