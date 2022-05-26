package com.astro.test.glenntuyu.domain

import androidx.paging.PagingData
import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by glenntuyu on 26/05/2022.
 */
class GetUserListUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
) {
     fun getUserList(query: String): Flow<PagingData<GithubUserModel>> {
        return githubRepository.getUserList(query)
    }
}