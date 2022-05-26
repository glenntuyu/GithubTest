package com.astro.test.glenntuyu.data.repository

import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel

/**
 * Created by glenntuyu on 26/05/2022.
 */
interface GithubRepository {
    suspend fun getUserList(page: Int, pageSize: Int): GetGithubUserResponseModel
}