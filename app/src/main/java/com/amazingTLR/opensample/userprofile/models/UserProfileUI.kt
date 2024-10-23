package com.amazingTLR.opensample.userprofile.models

import com.amazingTLR.opensample.common.utils.formatToKNotation
import com.amazingtlr.api.user.models.UserProfile

data class UserProfileUI(
    val login: String,
    val name: String,
    val avatarUrl: String,
    val formatedFollowers: String,
    val formatedFollowing: String,
    val nbOfRepo: Int
)

fun UserProfile.toUserProfileUI(): UserProfileUI {
    return UserProfileUI(
        login = login,
        name = name,
        avatarUrl = avatarUrl,
        formatedFollowers = followers.formatToKNotation(),
        formatedFollowing = following.formatToKNotation(),
        nbOfRepo = nbOfRepo
    )
}