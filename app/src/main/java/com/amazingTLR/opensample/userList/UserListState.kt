package com.amazingTLR.opensample.userList

sealed interface UserListState {
    data class Success(val userList: List<UserUI>): UserListState
    data object Loading: UserListState
    data object Error: UserListState
}