package com.astro.test.glenntuyu.users

import androidx.paging.PagingSource
import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import com.astro.test.glenntuyu.data.repository.GithubRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import com.astro.test.glenntuyu.data.repository.UserPagingSource
import com.astro.test.glenntuyu.util.Constant.ASCENDING
import com.astro.test.glenntuyu.util.Constant.DEFAULT_QUERY
import com.astro.test.glenntuyu.utils.jsonToObject
import com.astro.test.glenntuyu.utils.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Created by glenntuyu on 28/05/2022.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserPagingSourceTest {

    private val usersCommonResponse = "users.json"
    private val githubUserResponseModel: GetGithubUserResponseModel = usersCommonResponse.jsonToObject()
    private val githubService = FakeGithubService(githubUserResponseModel)

    @Test
    fun userPagingSource() = runTest {
        val pagingSource = UserPagingSource(githubService, DEFAULT_QUERY, ASCENDING)

        val expected = PagingSource.LoadResult.Page(
            data = githubUserResponseModel.items,
            prevKey = null,
            nextKey = 2
        )
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = NETWORK_PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        actual shouldBe expected
    }
}