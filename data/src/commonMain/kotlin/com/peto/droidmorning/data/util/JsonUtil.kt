package com.peto.droidmorning.data.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

object JsonUtil {
    val defaultJson: Json = Json { ignoreUnknownKeys = true }

    fun <T> decode(
        data: String,
        serializer: KSerializer<T>,
    ): T = defaultJson.decodeFromString(serializer, data)
}
