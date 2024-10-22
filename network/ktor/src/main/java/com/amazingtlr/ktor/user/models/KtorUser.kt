package com.amazingtlr.ktor.user.models

import com.amazingtlr.api.user.models.User
import com.amazingtlr.api.user.models.UserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorUser(
    @SerialName("id") val id: String,
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("name") val name: String? = null,
    @SerialName("followers") val followers: Int = 0,
    @SerialName("following") val following: Int = 0,
    @SerialName("public_repos") val nbOfRepo: Int = 0
)

fun KtorUser.toUserResponse(): User {
    return User(id = id, login = login, avatarUrl = avatarUrl)
}

fun KtorUser.toFullUserResponse(): UserProfile {
    return UserProfile(
        id = id,
        login = login,
        name = name ?: "",
        avatarUrl = avatarUrl,
        followers = followers,
        following = following,
        nbOfRepo = nbOfRepo
    )
}