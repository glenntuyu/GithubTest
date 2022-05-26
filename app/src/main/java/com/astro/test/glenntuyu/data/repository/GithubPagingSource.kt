package com.astro.test.glenntuyu.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.astro.test.glenntuyu.data.api.GithubApi
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.data.repository.GithubRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by glenntuyu on 26/05/2022.
 */

private const val STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GithubApi,
) : PagingSource<Int, GithubUserModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUserModel> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getUserList(page, NETWORK_PAGE_SIZE)
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