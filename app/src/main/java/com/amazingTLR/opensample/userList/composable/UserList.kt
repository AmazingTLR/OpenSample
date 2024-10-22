package com.amazingTLR.opensample.userList.composable

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.common.OnBottomReached
import com.amazingTLR.opensample.common.composable.LoadingCell
import com.amazingtlr.api.user.models.User
import kotlin.math.floor

@Composable
fun UserList(
    users: List<User>,
    maxWidth: Dp,
    onUserClick: (String) -> Unit,
    onRequestForNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyColumnState = rememberLazyListState()
    var isLoadingMore by remember { mutableStateOf(false) }

    lazyColumnState.OnBottomReached(buffer = 3) {
        onRequestForNextPage()
        isLoadingMore = true
    }

    Column {
        LazyColumn(state = lazyColumnState) {
            // Estimate the number of columns in the grid based on the minimum cell width
            val minimumCellWidth = 180.dp
            val gridSize = floor(maxWidth / minimumCellWidth).toInt()

            items(
                items = users.chunked(gridSize),
                key = { it.joinToString { it.id } }
            ) { userList ->
                UserRow(
                    gridSize = gridSize,
                    data = userList,
                    onClick = onUserClick
                )
            }

            if (isLoadingMore) {
                item {
                    LoadingCell(modifier = modifier)
                }
            }
        }
    }
}