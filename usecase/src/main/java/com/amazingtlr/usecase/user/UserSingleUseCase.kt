package com.amazingtlr.usecase.user

import com.amazingtlr.api.user.UserRepository
import com.amazingtlr.api.user.models.UserProfileResponse
import com.amazingtlr.usecase.UseCaseResult
import com.amazingtlr.usecase.mapNetworkResultToUseCaseResult
import kotlinx.coroutines.flow.Flow

class UserSingleUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userLogin: String) : Flow<UseCaseResult<UserProfileResponse, Any>> =
        userRepository.observeUserByUserLogin(userLogin).mapNetworkResultToUseCaseResult()
}