package com.amazingTLR.opensample.userList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazingTLR.opensample.common.SingleEventWrapper
import com.amazingTLR.opensample.userList.models.UserUI
import com.amazingTLR.opensample.userList.models.toUserUI
import com.amazingTLR.opensample.userList.states.UserListState
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
    private val mutableUserListStateFlow: MutableStateFlow<List<UserUI>> =
        MutableStateFlow(emptyList())

    val userListStateFlow: StateFlow<UserListState> by lazy {
        userListSharedFlow
            .map { userListResponse ->
                if (userListResponse.userList.isEmpty()) {
                    UserListState.Error
                } else {
                    val userList = mutableUserListStateFlow.updateAndGet {
                        (it + userListResponse.userList.map { it.toUserUI() }).distinctBy { it.id }
                    }

                    UserListState.Success(userList)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UserListState.Loading
            )
    }

    private val userListSharedFlow: SharedFlow<UserListResponse> by lazy {
        lastUserIdStateFlow.flatMapLatest { lastUserId ->
            userListUseCase(lastUserId)
                .map { useCaseResult ->
                    when (useCaseResult) {
                        is UseCaseResult.Failure -> {
                            Log.e(TAG, "Failed to fetch users", useCaseResult.cause)
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

    companion object{
        private const val TAG = "UserListViewModel"
    }
}