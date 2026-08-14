package com.sappyoak.dsanalyzer.app

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.app.config.AppEnvironment


class AppServices private constructor(
    val environment: AppEnvironment,
    val http: HttpClient,
    val json: Json
) : AutoCloseable {
    override fun close() {
        http.close()
    }

    companion object {
        fun create(): AppServices {
            val json = Json {
                allowTrailingComma = true
                decodeEnumsCaseInsensitive = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            }

            val environment = AppEnvironment.load(json)

            val http = HttpClient(CIO) {
                install(ContentNegotiation) { json(json) }
                install(HttpTimeout) {
                    connectTimeoutMillis = 15_000
                    requestTimeoutMillis = 60_000
                }

                expectSuccess = false
            }

            return AppServices(
                environment,
                http,
                json
            )
        }
    }
}