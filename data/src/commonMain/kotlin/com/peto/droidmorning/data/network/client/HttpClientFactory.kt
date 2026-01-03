package com.peto.droidmorning.data.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(enableLogging: Boolean = false): HttpClient =
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json { prettyPrint = true },
                )
            }

            if (enableLogging) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }

            defaultRequest {
                url("https://api.example.com/")
                headers {
                    append("Content-Type", "application/json")
                }
            }
        }
}
