package com.amazingtlr.usecase

import com.amazingtlr.api.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class UseCaseResult<out V : Any, out D : Any> {
    data class Success<out V : Any, out D : Any>(val value: V) : UseCaseResult<V, D>() {
        override fun toString() = "Success { $value }"
    }

    data class Failure<out V : Any, out D : Any>(val cause: Throwable) : UseCaseResult<V, D>() {
        override fun toString() = "Error { $cause }"
    }
}

fun <V : Any, D : Any> NetworkResult<V>.toUseCaseResult(): UseCaseResult<V, D> = when(this) {
    is NetworkResult.Error -> UseCaseResult.Failure(cause = error)
    is NetworkResult.Success -> UseCaseResult.Success(value = data)
}

fun <V: Any, D : Any> Flow<NetworkResult<V>>.mapNetworkResultToUseCaseResult(): Flow<UseCaseResult<V, D>> =
    map { it.toUseCaseResult() }