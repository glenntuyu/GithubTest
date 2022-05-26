package com.astro.test.glenntuyu.data.api

import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import retrofit2.http.*

/**
 * Created by glenntuyu on 26/05/2022.
 */
interface GithubApi {

    @GET("search/users?q=repos:>1")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): GetGithubUserResponseModel
}