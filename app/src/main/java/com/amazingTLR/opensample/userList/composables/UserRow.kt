package com.amazingTLR.opensample.userList.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.userList.models.UserUI

@Composable
fun UserRow(
    gridSize: Int,
    data: List<UserUI>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        data.forEach { user ->
            Column(
                modifier = modifier
                    .weight(1f)
                    .clickable {
                        onClick(user.login)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                UserMiniCard(user = user)
            }
        }

        // Fill the row will placeholders to have items with the same width
        repeat((data.size.rangeUntil(gridSize)).count()) {
            UserPlaceholder(
                modifier = modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun UserPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.aspectRatio(2 / 1f),
    )
}