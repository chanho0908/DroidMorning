package com.peto.droidmorning.core.network.di

import com.peto.droidmorning.core.network.AuthClient
import com.peto.droidmorning.core.network.HttpClient
import com.peto.droidmorning.core.network.PostgrestClient
import com.peto.droidmorning.core.network.SupabaseAuthClient
import com.peto.droidmorning.core.network.SupabasePostgrestClient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import org.koin.dsl.module

val networkModule =
    module {
        single<SupabaseClient> { HttpClient.client }

        single<Auth> { get<SupabaseClient>().auth }

        single<Postgrest> { get<SupabaseClient>().postgrest }

        single<AuthClient> { SupabaseAuthClient(get()) }

        single<PostgrestClient> { SupabasePostgrestClient(get()) }
    }
