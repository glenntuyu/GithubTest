package com.astro.test.glenntuyu.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserModel(
    @SerializedName("id") val id: Long = -1,
    @SerializedName("login") val login: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
    @SerializedName("followers") var followers: Int = -1,
    @SerializedName("following") var following: Int = -1,
)