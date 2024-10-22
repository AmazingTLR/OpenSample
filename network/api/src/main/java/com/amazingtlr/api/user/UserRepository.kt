package com.amazingtlr.api.user

import com.amazingtlr.api.NetworkResult
import com.amazingtlr.api.repo.models.RepoListResponse
import com.amazingtlr.api.user.models.UserListResponse
import com.amazingtlr.api.user.models.UserProfileResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUsers(lastUserId: String?): Flow<NetworkResult<UserListResponse>>

    fun observeUserByUserLogin(userLogin: String): Flow<NetworkResult<UserProfileResponse>>

    fun observeRepoByUserLogin(
        userLogin: String,
        repoPage: String
    ): Flow<NetworkResult<RepoListResponse>>
}