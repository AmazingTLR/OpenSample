package com.amazingTLR.opensample.userprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.R
import com.amazingTLR.opensample.common.screen.ErrorScreen
import com.amazingTLR.opensample.common.screen.LoadingScreen
import com.amazingTLR.opensample.common.utils.formatToKNotation
import com.amazingTLR.opensample.userprofile.composables.RepoList
import com.amazingTLR.opensample.userprofile.models.RepoUI
import com.amazingTLR.opensample.userprofile.states.RepoListState

@Composable
fun UserRepoListScreen(
    userRepoListState: RepoListState,
    onRepoClick: (String) -> Unit,
    onRequestForNextPage: () -> Unit,
    nbOfRepo: Int,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.repositories, nbOfRepo.formatToKNotation()) ,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.fillMaxWidth()
        )

        userRepoListState.let { repoListState ->
            when (repoListState) {
                is RepoListState.Error -> {
                    ErrorScreen(
                        modifier = modifier,
                        message = stringResource(R.string.no_repo_found_error)
                    )
                }
                is RepoListState.Loading -> LoadingScreen(modifier = modifier)

                is RepoListState.Success -> {
                    RepoList(
                        repoList = repoListState.repoList,
                        onRepoClick = onRepoClick,
                        onRequestForNextPage = onRequestForNextPage,
                        hasMore = repoListState.repoList.size < nbOfRepo,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun UserRepoListScreenPreview() {
    UserRepoListScreen(
        userRepoListState = RepoListState.Success(
            listOf(
                RepoUI(
                    id = "1",
                    name = "Repo Name",
                    description = "Description",
                    language = "Kotlin",
                    stars = 100,
                    repoUrl = "",
                ),
                RepoUI(
                    id = "2",
                    name = "Repo Name",
                    description = "Description",
                    language = "Kotlin",
                    stars = 100,
                    repoUrl = "",
                )
            )
        ),
        nbOfRepo = 5,
        onRepoClick = {},
        onRequestForNextPage = {},
        modifier = Modifier
    )
}