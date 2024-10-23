package com.amazingTLR.opensample.userprofile.states

import com.amazingTLR.opensample.userprofile.models.RepoUI

sealed interface RepoListState {
    data class Success(val repoList: List<RepoUI>): RepoListState
    data object Loading: RepoListState
    data object Error: RepoListState
}