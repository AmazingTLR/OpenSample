package com.amazingTLR.opensample.common

sealed class ApiState {
    data class Success<T>(val data: T?): ApiState()
    data object Loading: ApiState()
    data object Error: ApiState()
}

inline fun <reified T> safeCast(data: Any): T? {
    return data as? T
}