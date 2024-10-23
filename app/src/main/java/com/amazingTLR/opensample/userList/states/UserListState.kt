package com.amazingTLR.opensample.userList.states

import com.amazingTLR.opensample.userList.models.UserUI

sealed interface UserListState {
    data class Success(val userList: List<UserUI>): UserListState
    data object Loading: UserListState
    data object Error: UserListState
}