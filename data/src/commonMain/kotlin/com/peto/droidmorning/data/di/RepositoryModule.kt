package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.repository.DefaultAnswerRepository
import com.peto.droidmorning.data.repository.DefaultAuthRepository
import com.peto.droidmorning.data.repository.DefaultExamRepository
import com.peto.droidmorning.data.repository.DefaultQuestionRepository
import com.peto.droidmorning.domain.repository.AnswerRepository
import com.peto.droidmorning.domain.repository.ExamRepository
import com.peto.droidmorning.domain.repository.QuestionRepository
import com.peto.droidmorning.domain.repository.auth.AuthRepository
import org.koin.dsl.module

internal val repositoryModule =
    module {
        single<AuthRepository> { DefaultAuthRepository(get(), get()) }
        single<QuestionRepository> { DefaultQuestionRepository(get()) }
        single<AnswerRepository> { DefaultAnswerRepository(get()) }
        single<ExamRepository> { DefaultExamRepository(get()) }
    }
