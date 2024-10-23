package com.amazingTLR.opensample.userprofile.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.R
import com.amazingTLR.opensample.common.composable.CircularImage
import com.amazingTLR.opensample.userprofile.models.UserProfileUI

@Composable
fun UserProfileCard(
    user: UserProfileUI,
    modifier: Modifier = Modifier
) {
    val followersString = stringResource(id = R.string.followers)
    val followersText = remember {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("${user.formatedFollowers} ")
            }

            append(followersString)
        }
    }

    val followingsString = stringResource(id = R.string.followings)

    val followingText = remember {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("${user.formatedFollowing} ")
            }

            append(followingsString)
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
            Text(text = stringResource(R.string.point_separator), modifier = modifier.padding(horizontal = 4.dp))
            Text(text = followingText)
        }
    }
}


@Preview
@Composable
fun UserProfileCardPreview() {
    UserProfileCard(
        user = UserProfileUI(
            login = "amazingTLR",
            name = "Amazing TLR",
            avatarUrl = "https://avatars.githubusercontent.com/u/21097376?v=4",
            formatedFollowers = "10K",
            formatedFollowing = "10",
            nbOfRepo = 5,
        )
    )
}