package com.astro.test.glenntuyu.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.astro.test.glenntuyu.data.api.GithubService
import com.astro.test.glenntuyu.data.model.GithubUserModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by glenntuyu on 26/05/2022.
 */
class GithubRepositoryImpl(
    private val githubService: GithubService
): GithubRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getUserList(query: String, order: String): Flow<PagingData<GithubUserModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { UserPagingSource(githubService, query, order) }
        ).flow
    }
}