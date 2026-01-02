package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.network.client.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule =
    module {
        single<HttpClient> {
            HttpClientFactory.create(enableLogging = true)
        }
    }
