package com.peto.droidmorning.data.network

import com.peto.droidmorning.BuildKonfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabaseClient =
    createSupabaseClient(
        supabaseUrl = BuildKonfig.SUPABASE_URL,
        supabaseKey = BuildKonfig.SUPABASE_KEY,
    ) {
        install(Auth)
        install(Postgrest)
    }

// https://scmstnxmabjgmuxmozzg.supabase.co/auth/v1/callback
