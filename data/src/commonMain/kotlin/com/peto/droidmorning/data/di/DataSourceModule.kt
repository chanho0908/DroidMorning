package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.datasource.answer.remote.DefaultRemoteAnswerDataSource
import com.peto.droidmorning.data.datasource.answer.remote.RemoteAnswerDataSource
import com.peto.droidmorning.data.datasource.auth.local.DefaultLocalAuthDataSource
import com.peto.droidmorning.data.datasource.auth.local.LocalAuthDataSource
import com.peto.droidmorning.data.datasource.auth.remote.DefaultRemoteAuthDataSource
import com.peto.droidmorning.data.datasource.auth.remote.RemoteAuthDataSource
import com.peto.droidmorning.data.datasource.question.remote.DefaultRemoteQuestionDataSource
import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import org.koin.dsl.module

internal val dataSourceModule =
    module {
        single<LocalAuthDataSource> { DefaultLocalAuthDataSource(get()) }
        single<RemoteAuthDataSource> { DefaultRemoteAuthDataSource(get()) }
        single<RemoteQuestionDataSource> { DefaultRemoteQuestionDataSource(get(), get()) }
        single<RemoteAnswerDataSource> { DefaultRemoteAnswerDataSource(get(), get()) }
    }
