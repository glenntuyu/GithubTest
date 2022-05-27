package com.astro.test.glenntuyu.ui.userdetail

import com.astro.test.glenntuyu.data.model.GithubUserModel

/**
 * Created by glenntuyu on 27/05/2022.
 */

fun GithubUserModel.toUserDetailDataView() = UserDetailDataView(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    followers = followers.toString(),
    following = following.toString(),
)