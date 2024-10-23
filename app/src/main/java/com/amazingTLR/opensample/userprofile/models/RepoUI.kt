package com.amazingTLR.opensample.userprofile.models

import com.amazingtlr.api.repo.models.Repo

data class RepoUI(
    val id: String,
    val name: String,
    val repoUrl: String,
    val stars: Int = 0,
    val language: String?,
    val description: String?
)

fun Repo.toRepoUI(): RepoUI {
    return RepoUI(
        id = id,
        name = name,
        repoUrl = repoUrl,
        stars = stars,
        language = language,
        description = description
    )
}