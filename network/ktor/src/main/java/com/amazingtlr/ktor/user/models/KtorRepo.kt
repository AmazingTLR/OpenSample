package com.amazingtlr.ktor.user.models

import com.amazingtlr.api.repo.models.Repo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorRepo(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("html_url") val repoUrl: String,
    @SerialName("stargazers_count") val stars: Int = 0,
    @SerialName("language") val language: String?,
    @SerialName("description") val description: String?
)

fun KtorRepo.toRepoResponse(): Repo {
    return Repo(
        id = id,
        name = name,
        repoUrl = repoUrl,
        stars = stars,
        language = language,
        description = description
    )
}