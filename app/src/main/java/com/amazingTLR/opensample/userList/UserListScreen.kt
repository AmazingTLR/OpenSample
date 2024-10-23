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
import com.amazingTLR.opensample.common.ApiState
import com.amazingTLR.opensample.common.SingleEventWrapper
import com.amazingTLR.opensample.common.safeCast
import com.amazingTLR.opensample.common.screen.ErrorScreen
import com.amazingTLR.opensample.common.screen.LoadingScreen
import com.amazingTLR.opensample.userList.composable.UserList
import com.amazingtlr.api.user.models.User

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

        when (userListStateFlow) {
            is ApiState.Loading -> LoadingScreen(modifier = modifier)

            is ApiState.Success<*> -> {
                //Avoiding unsafe cast and null values. Shouldn't be needed
                (userListStateFlow as ApiState.Success<*>).data?.let { safeCast<List<User>>(it) }?.let { users ->
                    UserList(
                        users = users,
                        maxWidth = maxWidth,
                        onUserClick = viewModel::onUserClick,
                        onRequestForNextPage = viewModel::loadMoreUsers,
                        modifier = modifier
                    )
                }
            }

            is ApiState.Error -> {
                ErrorScreen(modifier = modifier, message = stringResource(R.string.no_users_found_error))
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








