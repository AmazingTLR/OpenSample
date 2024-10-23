package com.amazingTLR.opensample.userprofile

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amazingTLR.opensample.R
import com.amazingTLR.opensample.common.ApiState
import com.amazingTLR.opensample.common.SingleEventWrapper
import com.amazingTLR.opensample.common.safeCast
import com.amazingTLR.opensample.common.screen.ErrorScreen
import com.amazingTLR.opensample.common.screen.LoadingScreen
import com.amazingTLR.opensample.userprofile.composable.UserProfileCard
import com.amazingtlr.api.user.models.UserProfile

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val userProfileState by viewModel.userProfileStateFlow.collectAsStateWithLifecycle()
    val userRepoListStateFlow by viewModel.userRepoListStateFlow.collectAsStateWithLifecycle()

    val events by viewModel.events.collectAsStateWithLifecycle()

    handleEvents(events, LocalContext.current)

    when (userProfileState) {
        is ApiState.Loading -> LoadingScreen(modifier = modifier)

        is ApiState.Success<*> -> {
            (userProfileState as ApiState.Success<*>).data?.let { safeCast<UserProfile>(it) }?.let { userProfile ->
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    UserProfileCard(user = userProfile, modifier = modifier)
                    Spacer(modifier = modifier.size(16.dp))
                    UserRepoListScreen(
                        userRepoListStateFlow = userRepoListStateFlow,
                        nbOfRepo = userProfile.nbOfRepo,
                        onRepoClick = viewModel::onRepoClick,
                        onRequestForNextPage = viewModel::loadMoreRepos,
                        modifier = modifier
                    )
                }
            }
        }

        is ApiState.Error -> {
            ErrorScreen(modifier = modifier, message = stringResource(R.string.no_profile_found_error))
        }
    }
}

private fun handleEvents(events: SingleEventWrapper<UserProfileEvent>, context: Context) {
    events.getContentIfNotHandled()?.let { event ->
        when (event) {
            is UserProfileEvent.OpenUserRepoEvent -> {
                if (event.repoUrl != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.repoUrl))
                    startActivity(context, intent, null)
                }
            }
        }
    }
}

