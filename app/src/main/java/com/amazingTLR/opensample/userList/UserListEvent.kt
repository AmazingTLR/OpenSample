package com.amazingTLR.opensample.userList

sealed interface UserListEvent {
    data class OpenUserProfileEvent(val userLogin: String?): UserListEvent
}