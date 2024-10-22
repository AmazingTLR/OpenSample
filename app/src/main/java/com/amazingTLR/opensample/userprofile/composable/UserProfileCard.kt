package com.amazingTLR.opensample.userprofile.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.common.composable.CircularImage
import com.amazingTLR.opensample.common.utils.formatToKNotation
import com.amazingtlr.api.user.models.UserProfile

@Composable
fun UserProfileCard(
    user: UserProfile,
    modifier: Modifier = Modifier
) {
    val followersText = remember {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(user.followers.formatToKNotation())
            }

            append(" Followers")
        }
    }

    val followingText = remember {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(user.following.formatToKNotation())
            }

            append(" Following")
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularImage(
            url = user.avatarUrl,
            username = user.login,
            size = 150.dp,
            modifier = modifier
        )
        Text(
            text = user.name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = user.login,
            style = MaterialTheme.typography.titleMedium
        )
        Row {
            Text(text = followersText)
            Text("•", modifier = modifier.padding(horizontal = 4.dp))
            Text(text = followingText)
        }
    }
}


@Preview
@Composable
fun UserProfileCardPreview() {
    UserProfileCard(
        user = UserProfile(
            id = "1",
            login = "amazingTLR",
            name = "Amazing TLR",
            avatarUrl = "https://avatars.githubusercontent.com/u/21097376?v=4",
            followers = 1000,
            following = 10,
            nbOfRepo = 5,
        )
    )
}