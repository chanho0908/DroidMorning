package com.peto.droidmorning.data.di

import com.peto.droidmorning.BuildKonfig
import com.peto.droidmorning.data.network.client.HttpClientFactory
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.client.HttpClient
import org.koin.dsl.module

internal val networkModule =
    module {
        single<HttpClient> {
            HttpClientFactory.create(enableLogging = true)
        }

        single<SupabaseClient> {
            createSupabaseClient(
                supabaseUrl = BuildKonfig.SUPABASE_URL,
                supabaseKey = BuildKonfig.SUPABASE_KEY,
            ) {
                install(Auth)
                install(Postgrest)
            }
        }

        single<Auth> { get<SupabaseClient>().auth }

        single<Postgrest> { get<SupabaseClient>().postgrest }
    }
