package com.amazingTLR.opensample.userprofile.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.amazingTLR.opensample.common.OnBottomReached
import com.amazingTLR.opensample.common.composable.LoadingCell
import com.amazingTLR.opensample.userprofile.models.RepoUI

@Composable
fun RepoList(
    repoList: List<RepoUI>,
    onRepoClick: (String) -> Unit,
    onRequestForNextPage: () -> Unit,
    hasMore: Boolean,
    modifier: Modifier = Modifier
) {
    val lazyColumnState = rememberLazyListState()
    var isLoadingMore by remember { mutableStateOf(false) }

    lazyColumnState.OnBottomReached(buffer = 3, hasMore = hasMore) {
        onRequestForNextPage()
        isLoadingMore = true
    }

    Column {
        LazyColumn(state = lazyColumnState) {
            items(repoList) { repo ->
                RepoCard(repo = repo, onRepoClick = onRepoClick, modifier = modifier)
            }

            if (isLoadingMore && hasMore) {
                item {
                    LoadingCell(modifier = modifier)
                }
            }
        }
    }
}
