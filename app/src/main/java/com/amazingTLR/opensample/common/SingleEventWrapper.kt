package com.amazingTLR.opensample.common

// Wrapper class to ensure that the associated event is only handled once
class SingleEventWrapper<out T>(private val content: T) {
    private var hasBeenHandled = false

    // Returns the content if it's not been handled yet
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}