package com.peto.droidmorning

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class Greeting {
    private val platform = getPlatform()
    private val client = HttpClient()

    // fun greet(): String = "Hello, ${platform.name}!"
    suspend fun greet(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}
