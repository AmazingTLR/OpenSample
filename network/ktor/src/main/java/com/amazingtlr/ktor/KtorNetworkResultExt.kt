package com.amazingtlr.ktor

import com.amazingtlr.api.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T: Any> HttpClient.getAndParse(block: HttpRequestBuilder.() -> Unit): NetworkResult<T> {
    return try {
        get(block).toNetworkResult()
    } catch (e: Exception) {
        NetworkResult.Error(e)
    }
}

suspend inline fun <reified T : Any> HttpResponse.toNetworkResult(): NetworkResult<T> {
    //TODO: Handle other status codes
    return when (status.value) {
        200 -> NetworkResult.Success(body())
        else -> NetworkResult.Error(NetworkException("Something went wrong!"))
    }
}

class NetworkException(message: String) : Exception(message)