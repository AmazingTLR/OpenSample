package com.amazingTLR.opensample.userList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazingTLR.opensample.common.ApiState
import com.amazingTLR.opensample.common.SingleEventWrapper
import com.amazingtlr.api.user.models.User
import com.amazingtlr.api.user.models.UserListResponse
import com.amazingtlr.usecase.UseCaseResult
import com.amazingtlr.usecase.user.UserListUseCase
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
class UserListViewModel @Inject constructor(private val userListUseCase: UserListUseCase) :
    ViewModel() {
    private var mutableEvents =
        MutableStateFlow(SingleEventWrapper(UserListEvent.OpenUserProfileEvent(null)))
    var events: StateFlow<SingleEventWrapper<UserListEvent>> = mutableEvents.asStateFlow()

    private val lastUserIdStateFlow = MutableStateFlow<String?>(null)
    private val mutableUserListStateFlow: MutableStateFlow<List<User>> =
        MutableStateFlow(emptyList())

    val userListStateFlow: StateFlow<ApiState> by lazy {
        userListSharedFlow
            .map { userListResponse ->
                if (userListResponse.userList.isEmpty()) {
                    ApiState.Error("No users found")
                } else {
                    val userList = mutableUserListStateFlow.updateAndGet {
                        (it + userListResponse.userList).distinctBy { it.id }
                    }

                    ApiState.Success(userList)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ApiState.Loading
            )
    }

    private val userListSharedFlow: SharedFlow<UserListResponse> by lazy {
        lastUserIdStateFlow.flatMapLatest { lastUserId ->
            userListUseCase(lastUserId)
                .map { useCaseResult ->
                    when (useCaseResult) {
                        is UseCaseResult.Failure -> {
                            Log.e("UserListViewModel", "Failed to fetch users", useCaseResult.cause)
                            UserListResponse(emptyList())
                        }

                        is UseCaseResult.Success -> useCaseResult.value
                    }
                }
        }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                replay = 1
            )
    }

    fun onUserClick(userLogin: String) =
        mutableEvents.update { SingleEventWrapper(UserListEvent.OpenUserProfileEvent(userLogin)) }

    fun loadMoreUsers() {
        userListSharedFlow.replayCache.lastOrNull()?.let { userListResponse ->
            userListResponse.userList.lastOrNull()?.id?.let { lastUserId ->
                lastUserIdStateFlow.tryEmit(lastUserId)
            }
        }
    }
}