package com.astro.test.glenntuyu.users

import com.astro.test.glenntuyu.data.api.GithubService
import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import com.astro.test.glenntuyu.data.model.GithubUserModel

/**
 * Created by glenntuyu on 28/05/2022.
 */
class FakeGithubService(private val getGithubUserResponseModel: GetGithubUserResponseModel): GithubService {

    override suspend fun getUserList(
        query: String,
        page: Int,
        pageSize: Int,
        order: String
    ): GetGithubUserResponseModel {
        return getGithubUserResponseModel
    }

    override suspend fun getUserDetail(username: String): GithubUserModel {
        return getGithubUserResponseModel.items[0]
    }
}