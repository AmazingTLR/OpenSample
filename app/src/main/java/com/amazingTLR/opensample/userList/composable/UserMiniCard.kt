package com.amazingTLR.opensample.userList.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amazingTLR.opensample.common.composable.CircularImage
import com.amazingtlr.api.user.models.User

@Composable
fun UserMiniCard(
    user: User,
    modifier: Modifier = Modifier
) {
    CircularImage(
        url = user.avatarUrl,
        username = user.login,
        size = 100.dp,
        modifier = modifier
    )
    Spacer(modifier = modifier.height(8.dp))
    Text(
        text = user.login,
        fontSize = 16.sp
    )
}