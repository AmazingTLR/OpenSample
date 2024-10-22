package com.amazingtlr.usecase.repo

import com.amazingtlr.api.repo.models.RepoListResponse
import com.amazingtlr.api.user.UserRepository
import com.amazingtlr.usecase.UseCaseResult
import com.amazingtlr.usecase.mapNetworkResultToUseCaseResult
import kotlinx.coroutines.flow.Flow

class RepoListUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        userLogin: String,
        repoPage: String
    ): Flow<UseCaseResult<RepoListResponse, Any>> =
        userRepository.observeRepoByUserLogin(userLogin, repoPage)
            .mapNetworkResultToUseCaseResult()
}