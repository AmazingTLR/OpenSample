package com.amazingtlr.ktor.user

import com.amazingtlr.api.NetworkResult
import com.amazingtlr.api.repo.models.RepoListResponse
import com.amazingtlr.api.toNetworkSuccessOrError
import com.amazingtlr.api.user.UserRepository
import com.amazingtlr.api.user.models.UserListResponse
import com.amazingtlr.api.user.models.UserProfileResponse
import com.amazingtlr.ktor.getAndParse
import com.amazingtlr.ktor.user.models.KtorRepo
import com.amazingtlr.ktor.user.models.KtorUser
import com.amazingtlr.ktor.user.models.toFullUserResponse
import com.amazingtlr.ktor.user.models.toRepoResponse
import com.amazingtlr.ktor.user.models.toUserResponse
import io.ktor.client.HttpClient
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KtorUserRepository(private val httpClient: HttpClient) : UserRepository {
    private val userEndpoint = "users"
    private val paginationSinceParam = "since"
    private val paginationPageParam = "page"
    private val repoEndpoint = "repos"

    override fun observeUsers(lastUserId: String?): Flow<NetworkResult<UserListResponse>> {
        return flow {
            emit(
                httpClient.getAndParse<List<KtorUser>> {
                    url {
                        appendPathSegments(userEndpoint)
                        if (lastUserId != null) {
                            parameters.append(paginationSinceParam, lastUserId)
                        }
                    }
                }
                    .toNetworkSuccessOrError { ktorUserList ->
                        UserListResponse(
                            ktorUserList.map { ktorUser ->
                                ktorUser.toUserResponse()
                            }
                        )
                    }
            )
        }
    }

    override fun observeUserByUserLogin(userLogin: String): Flow<NetworkResult<UserProfileResponse>> =
        flow {
            emit(
                httpClient.getAndParse<KtorUser> {
                    url {
                        appendPathSegments(userEndpoint, userLogin)
                    }
                }
                    .toNetworkSuccessOrError { ktorUser ->
                        UserProfileResponse(
                            ktorUser.toFullUserResponse()
                        )
                    }
            )
        }

    override fun observeRepoByUserLogin(
        userLogin: String,
        repoPage: String,
    ): Flow<NetworkResult<RepoListResponse>> =
        flow {
            emit(
                httpClient.getAndParse<List<KtorRepo>> {
                    url {
                        appendPathSegments(userEndpoint, userLogin, repoEndpoint)
                        parameters.append(paginationPageParam, repoPage)
                    }
                }
                    .toNetworkSuccessOrError { ktorListRepo ->
                        RepoListResponse(
                            ktorListRepo.map { ktorRepo ->
                                ktorRepo.toRepoResponse()
                            }
                        )
                    }
            )
        }
}