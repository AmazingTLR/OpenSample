package com.amazingtlr.usecase.user

import com.amazingtlr.api.user.UserRepository
import com.amazingtlr.api.user.models.UserListResponse
import com.amazingtlr.usecase.UseCaseResult
import com.amazingtlr.usecase.mapNetworkResultToUseCaseResult
import kotlinx.coroutines.flow.Flow

class UserListUseCase(private val userRepository: UserRepository) {
    operator fun invoke(lastUserId: String? = null): Flow<UseCaseResult<UserListResponse, Any>> =
        userRepository.observeUsers(lastUserId).mapNetworkResultToUseCaseResult()
}
