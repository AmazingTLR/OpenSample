package com.amazingtlr.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorJsonPlaceHolderClient(private val githubApiKey: String) {
    val ktorJsonPlaceHolderClient = HttpClient(Android) {
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            url("https://api.github.com/")
        }

        // Log All by default
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("HttpClient ===> $message")
                }
            }
        }

        // Comment this to disable authentication
        // You will be rate limited
        install(Auth) {
            bearer {
                loadTokens {
                    // Do not do that in a real app!
                    // Use an authentication mechanism
                    BearerTokens(githubApiKey, "")
                }
            }
        }


        // Makes JSON easier to work with
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }
    }
}

