package com.amazingTLR.opensample.userprofile.states

import com.amazingTLR.opensample.userprofile.models.UserProfileUI

sealed interface UserProfileState {
    data class Success(val user: UserProfileUI): UserProfileState
    data object Loading: UserProfileState
    data object Error: UserProfileState
}