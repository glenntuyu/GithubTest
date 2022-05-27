package com.astro.test.glenntuyu.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserModel(
    @SerializedName("id") var id: Long,
    @SerializedName("login") var login: String,
    @SerializedName("avatar_url") var avatarUrl: String,
    @SerializedName("html_url") var htmlUrl: String,
)