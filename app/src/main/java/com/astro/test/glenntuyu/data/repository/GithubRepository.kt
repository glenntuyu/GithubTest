package com.astro.test.glenntuyu.data.repository

import androidx.paging.PagingData
import com.astro.test.glenntuyu.data.model.GithubUserModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by glenntuyu on 26/05/2022.
 */
interface GithubRepository {
    fun getUserList(query: String, order: String): Flow<PagingData<GithubUserModel>>
    suspend fun getUserDetail(username: String): GithubUserModel
}