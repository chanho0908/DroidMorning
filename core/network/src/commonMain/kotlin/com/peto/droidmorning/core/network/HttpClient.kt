package com.peto.droidmorning.core.network

import com.peto.droidmorning.BuildKonfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object HttpClient {
    val client by lazy {
        createSupabaseClient(
            supabaseUrl = BuildKonfig.SUPABASE_URL,
            supabaseKey = BuildKonfig.SUPABASE_KEY,
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
}
