package com.amazingTLR.opensample.userList.models

import com.amazingtlr.api.user.models.User

data class UserUI(
    val id: String,
    val login: String,
    val avatarUrl: String
)

fun User.toUserUI(): UserUI {
    return UserUI(id = id, login = login, avatarUrl = avatarUrl)
}