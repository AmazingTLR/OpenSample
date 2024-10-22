package com.amazingTLR.opensample.userprofile

sealed interface UserProfileEvent {
    data class OpenUserRepoEvent(val repoUrl: String?): UserProfileEvent
}