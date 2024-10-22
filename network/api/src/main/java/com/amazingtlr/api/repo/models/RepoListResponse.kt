package com.amazingtlr.api.repo.models

data class RepoListResponse(val repoList: List<Repo>)

data class Repo(
    val id: String,
    val name: String,
    val repoUrl: String,
    val stars: Int = 0,
    val language: String?,
    val description: String?
)