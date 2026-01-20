package com.peto.droidmorning.core.network

import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

class SupabasePostgrestClient(
    private val postgrest: Postgrest,
) : PostgrestClient {
    override suspend fun select(
        table: String,
        filters: List<PostgrestFilter>,
        order: PostgrestOrder?,
    ): String =
        postgrest
            .from(table)
            .select(Columns.ALL) {
                filter {
                    filters.forEach { filter ->
                        eq(filter.column, filter.value)
                    }
                }
                order?.let {
                    order(
                        it.column,
                        order = if (it.descending) Order.DESCENDING else Order.ASCENDING,
                    )
                }
            }.data

    override suspend fun rpc(
        function: String,
        parameters: JsonObject?,
    ): String =
        postgrest
            .rpc(
                function = function,
                parameters = parameters ?: buildJsonObject { },
            ).data

    override suspend fun insert(
        table: String,
        body: JsonObject,
    ) {
        postgrest
            .from(table)
            .insert(JsonArray(listOf(body)))
    }

    override suspend fun update(
        table: String,
        body: JsonElement,
        filters: List<PostgrestFilter>,
    ) {
        postgrest
            .from(table)
            .update(body) {
                filter {
                    filters.forEach { filter ->
                        eq(filter.column, filter.value)
                    }
                }
            }
    }

    override suspend fun delete(
        table: String,
        filters: List<PostgrestFilter>,
    ) {
        postgrest
            .from(table)
            .delete {
                filter {
                    filters.forEach { filter ->
                        eq(filter.column, filter.value)
                    }
                }
            }
    }
}
