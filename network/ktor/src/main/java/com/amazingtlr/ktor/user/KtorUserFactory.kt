package com.amazingtlr.ktor.user

import com.amazingtlr.api.user.UserRepository
import io.ktor.client.HttpClient

object KtorUserFactory {
    fun createUserRepository(httpClient: HttpClient): UserRepository =
        KtorUserRepository(httpClient)
}