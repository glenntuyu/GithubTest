package com.astro.test.glenntuyu.domain

import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.data.repository.GithubRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 26/05/2022.
 */
class GetUserDetailUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
): UseCase<GithubUserModel, String>() {

    override suspend fun executeOnBackground(param: String): GithubUserModel {
        return githubRepository.getUserDetail(param)
    }

}