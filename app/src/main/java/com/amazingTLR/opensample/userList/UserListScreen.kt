package com.amazingTLR.opensample.userList

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.amazingTLR.opensample.R
import com.amazingTLR.opensample.Routes
import com.amazingTLR.opensample.common.SingleEventWrapper
import com.amazingTLR.opensample.common.screen.ErrorScreen
import com.amazingTLR.opensample.common.screen.LoadingScreen
import com.amazingTLR.opensample.userList.composables.UserList
import com.amazingTLR.opensample.userList.states.UserListState

@Composable
fun UserListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val userListStateFlow by viewModel.userListStateFlow.collectAsStateWithLifecycle()
        val events by viewModel.events.collectAsStateWithLifecycle()

        handleEvents(events, navController)

        userListStateFlow.let { userState ->
            when (userState) {
                UserListState.Error -> ErrorScreen(modifier = modifier, message = stringResource(R.string.no_users_found_error))
                UserListState.Loading -> LoadingScreen(modifier = modifier)
                is UserListState.Success -> {
                    UserList(
                        users = userState.userList,
                        maxWidth = maxWidth,
                        onUserClick = viewModel::onUserClick,
                        onRequestForNextPage = viewModel::loadMoreUsers,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

private fun handleEvents(events: SingleEventWrapper<UserListEvent>, navController: NavController) {
    events.getContentIfNotHandled()?.let { event ->
        when (event) {
            is UserListEvent.OpenUserProfileEvent -> {
                if (event.userLogin != null) {
                    navController.navigate("${Routes.userProfileScreen}/${event.userLogin}") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}








