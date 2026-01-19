package com.peto.droidmorning.di

import com.peto.droidmorning.exam.complete.navigation.ExamCompleteNavGraphContributor
import com.peto.droidmorning.exam.progress.navigation.ExamProgressNavGraphContributor
import com.peto.droidmorning.login.navigation.LoginNavGraphContributor
import com.peto.droidmorning.main.navigation.MainNavGraphContributor
import com.peto.droidmorning.navigation.NavGraphContributor
import com.peto.droidmorning.questions.detail.navigation.QuestionDetailNavGraph
import org.koin.core.qualifier.named
import org.koin.dsl.module

val navigationModule =
    module {
        single<NavGraphContributor>(named("login")) { LoginNavGraphContributor() }
        single<NavGraphContributor>(named("main")) { MainNavGraphContributor() }
        single<NavGraphContributor>(named("QuestionDetail")) { QuestionDetailNavGraph() }
        single<NavGraphContributor>(named("ExamProgress")) { ExamProgressNavGraphContributor() }
        single<NavGraphContributor>(named("ExamComplete")) { ExamCompleteNavGraphContributor() }
    }
