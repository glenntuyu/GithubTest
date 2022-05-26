package com.astro.test.glenntuyu.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.astro.test.glenntuyu.data.api.GithubService
import com.astro.test.glenntuyu.data.api.GithubService.Companion.IN_QUALIFIER
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.data.repository.GithubRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by glenntuyu on 26/05/2022.
 */

private const val STARTING_PAGE_INDEX = 1

class UserPagingSource(
    private val service: GithubService,
    private val query: String,
    private val order: String,
) : PagingSource<Int, GithubUserModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUserModel> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = service.getUserList(apiQuery, page, NETWORK_PAGE_SIZE, order)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                page + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubUserModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}