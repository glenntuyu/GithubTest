package com.astro.test.glenntuyu.di

import com.astro.test.glenntuyu.data.api.GithubApi
import com.astro.test.glenntuyu.data.repository.GithubRepository
import com.astro.test.glenntuyu.data.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by glenntuyu on 26/05/2022.
 */
@Module
@InstallIn(SingletonComponent::class)
class GithubRepositoryModule {

    @Provides
    internal fun provideRepository(
        githubApi: GithubApi,
    ): GithubRepository {
        return GithubRepositoryImpl(githubApi)
    }
}