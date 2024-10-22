package com.amazingtlr.api

sealed interface NetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>
    data class Error<out T : Any>(val error: Exception) : NetworkResult<T>
}

inline fun <T : Any, R : Any>NetworkResult<T>.toNetworkSuccessOrError(transform: (T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(data))
        is NetworkResult.Error -> NetworkResult.Error(error)
    }
}