package com.peto.droidmorning.data.di

import com.peto.droidmorning.data.network.client.HttpClientFactory
import com.peto.droidmorning.data.network.supabaseClient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule =
    module {
        single<HttpClient> {
            HttpClientFactory.create(enableLogging = true)
        }

        single<SupabaseClient> { supabaseClient }

        single { get<SupabaseClient>().auth }
    }
