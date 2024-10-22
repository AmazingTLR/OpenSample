package com.amazingTLR.opensample.userprofile.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazingTLR.opensample.common.composable.DynamicTextField
import com.amazingTLR.opensample.R
import com.amazingtlr.api.repo.models.Repo

@Composable
fun RepoCard(
    repo: Repo,
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clickable { onRepoClick(repo.repoUrl) },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DynamicTextField(
            defaultValue = repo.name,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        DynamicTextField(
            defaultValue = repo.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Row {
            DynamicTextField(
                defaultValue = repo.language,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                modifier = Modifier.padding(end = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "star icon",
                    modifier = Modifier.size(14.dp),
                    tint = Color.Gray,
                )
                Spacer(modifier = Modifier.width(2.dp))
                DynamicTextField(
                    defaultValue = repo.stars.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                )
            }
        }

        HorizontalDivider(thickness = 0.5.dp)
    }
}

@Preview
@Composable
fun RepoCardPreview() {
    RepoCard(
        repo = Repo(
            id = "1",
            name = "Repo Name",
            description = "Description",
            language = "Kotlin",
            stars = 100,
            repoUrl = "",
        ),
        onRepoClick = {}
    )
}