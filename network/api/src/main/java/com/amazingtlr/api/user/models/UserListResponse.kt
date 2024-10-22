package com.amazingtlr.api.user.models

data class UserListResponse(val userList: List<User>)

data class User(val id: String, val login: String, val avatarUrl: String)