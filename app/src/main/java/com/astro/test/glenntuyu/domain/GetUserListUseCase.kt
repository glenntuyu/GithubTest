package com.astro.test.glenntuyu.domain

import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import com.astro.test.glenntuyu.data.repository.GithubRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 26/05/2022.
 */
class GetUserListUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
) {
    suspend fun getUserList(): GetGithubUserResponseModel {
        return githubRepository.getUserList(1, 30)
    }
}