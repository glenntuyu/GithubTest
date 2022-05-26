package com.astro.test.glenntuyu.data.repository

import com.astro.test.glenntuyu.data.api.GithubApi
import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel

/**
 * Created by glenntuyu on 26/05/2022.
 */
class GithubRepositoryImpl(
    private val githubApi: GithubApi
): GithubRepository {

    override suspend fun getUserList(page: Int, pageSize: Int): GetGithubUserResponseModel {
        return githubApi.getUserList(page, pageSize)
    }
}