package com.amazingtlr.ktor

import com.amazingtlr.api.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T : Any> HttpResponse.toNetworkResult(): NetworkResult<T> {
    return when (status.value) {
        200 -> NetworkResult.Success(body())
        else -> NetworkResult.Error(NetworkException("Something went wrong!"))
    }
}

class NetworkException(message: String) : Exception(message)