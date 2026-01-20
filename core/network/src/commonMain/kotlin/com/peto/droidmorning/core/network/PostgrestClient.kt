package com.peto.droidmorning.core.network

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

interface PostgrestClient {
    suspend fun select(
        table: String,
        filters: List<PostgrestFilter> = emptyList(),
        order: PostgrestOrder? = null,
    ): String

    suspend fun rpc(
        function: String,
        parameters: JsonObject? = null,
    ): String

    suspend fun insert(
        table: String,
        body: JsonObject,
    )

    suspend fun update(
        table: String,
        body: JsonElement,
        filters: List<PostgrestFilter> = emptyList(),
    )

    suspend fun delete(
        table: String,
        filters: List<PostgrestFilter> = emptyList(),
    )
}
