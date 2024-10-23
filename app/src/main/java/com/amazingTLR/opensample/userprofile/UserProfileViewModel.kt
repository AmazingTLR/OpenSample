package com.amazingTLR.opensample.userprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazingTLR.opensample.common.ApiState
import com.amazingTLR.opensample.common.SingleEventWrapper
import com.amazingTLR.opensample.di.ScreenArgModule
import com.amazingTLR.opensample.userList.UserListViewModel
import com.amazingTLR.opensample.userList.UserListViewModel.Companion
import com.amazingtlr.api.repo.models.Repo
import com.amazingtlr.api.repo.models.RepoListResponse
import com.amazingtlr.usecase.UseCaseResult
import com.amazingtlr.usecase.repo.RepoListUseCase
import com.amazingtlr.usecase.user.UserSingleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userSingleUseCase: UserSingleUseCase,
    private val repoListUseCase: RepoListUseCase,
    @ScreenArgModule.UserLogin private val userLogin: String?,
) : ViewModel() {
    private var mutableEvents =
        MutableStateFlow(SingleEventWrapper(UserProfileEvent.OpenUserRepoEvent(null)))
    var events: StateFlow<SingleEventWrapper<UserProfileEvent>> = mutableEvents.asStateFlow()

    private val lastRepoPageStateFlow = MutableStateFlow(1)
    private val mutableRepoListStateFlow: MutableStateFlow<List<Repo>> =
        MutableStateFlow(emptyList())

    val userProfileStateFlow: StateFlow<ApiState> by lazy {
        userSingleUseCase(userLogin!!)
            .map { useCaseResult ->
                when (useCaseResult) {
                    is UseCaseResult.Failure -> {
                        Log.e(TAG, "Failed to fetch user profile", useCaseResult.cause)
                        ApiState.Error
                    }

                    is UseCaseResult.Success -> {
                        ApiState.Success(useCaseResult.value.user)
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ApiState.Loading
            )
    }

    val userRepoListStateFlow: StateFlow<ApiState> by lazy {
        repoListSharedFlow
            .map { repoListResponse ->
                if (repoListResponse.repoList.isEmpty()) {
                    ApiState.Error
                } else {
                    val userList = mutableRepoListStateFlow.updateAndGet {
                        (it + repoListResponse.repoList).distinctBy { it.id }
                    }

                    ApiState.Success(userList)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ApiState.Loading
            )
    }

    private val repoListSharedFlow: SharedFlow<RepoListResponse> by lazy {
        lastRepoPageStateFlow.flatMapLatest { lastRepoPage ->
            repoListUseCase(userLogin!!, lastRepoPage.toString())
                .map { useCaseResult ->
                    when (useCaseResult) {
                        is UseCaseResult.Failure -> {
                            Log.e(
                                TAG,
                                "Failed to fetch user's repo",
                                useCaseResult.cause
                            )
                            RepoListResponse(emptyList())
                        }

                        is UseCaseResult.Success -> useCaseResult.value
                    }
                }
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )
    }

    fun onRepoClick(repoUrl: String) =
        mutableEvents.update { SingleEventWrapper(UserProfileEvent.OpenUserRepoEvent(repoUrl)) }

    fun loadMoreRepos() {
        lastRepoPageStateFlow.update {
            it + 1
        }
    }

    companion object {
        private const val TAG = "UserProfileViewModel"
    }

}