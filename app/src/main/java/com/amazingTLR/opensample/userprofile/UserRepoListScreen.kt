package com.amazingTLR.opensample.userprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.common.ApiState
import com.amazingTLR.opensample.common.safeCast
import com.amazingTLR.opensample.common.screen.ErrorScreen
import com.amazingTLR.opensample.common.screen.LoadingScreen
import com.amazingTLR.opensample.common.utils.formatToKNotation
import com.amazingTLR.opensample.userprofile.composable.RepoList
import com.amazingtlr.api.repo.models.Repo

@Composable
fun UserRepoListScreen(
    userRepoListStateFlow: ApiState,
    onRepoClick: (String) -> Unit,
    onRequestForNextPage: () -> Unit,
    nbOfRepo: Int,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Repositories • ${nbOfRepo.formatToKNotation()}",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.fillMaxWidth()
        )

        when (userRepoListStateFlow) {
            is ApiState.Loading -> LoadingScreen(modifier = modifier)

            is ApiState.Success<*> -> {

                //Avoiding unsafe cast and null values. Shouldn't be needed
                userRepoListStateFlow.data?.let { safeCast<List<Repo>>(it) }?.let { repoList ->
                    RepoList(
                        repoList = repoList,
                        onRepoClick = onRepoClick,
                        onRequestForNextPage = onRequestForNextPage,
                        hasMore = repoList.size < nbOfRepo,
                        modifier = modifier
                    )
                }
            }

            is ApiState.Error -> {
                val error = (userRepoListStateFlow as ApiState.Error).message
                ErrorScreen(modifier = modifier, message = error)
            }
        }
    }
}

@Composable
@Preview
fun UserRepoListScreenPreview() {
    UserRepoListScreen(
        userRepoListStateFlow = ApiState.Success(
            listOf(
                Repo(
                    id = "1",
                    name = "Repo Name",
                    description = "Description",
                    language = "Kotlin",
                    stars = 100,
                    repoUrl = "",
                ),
                Repo(
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