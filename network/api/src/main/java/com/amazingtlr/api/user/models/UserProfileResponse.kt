package com.amazingtlr.api.user.models

class UserProfileResponse(val user: UserProfile)

data class UserProfile(
    val id: String,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val nbOfRepo: Int
)