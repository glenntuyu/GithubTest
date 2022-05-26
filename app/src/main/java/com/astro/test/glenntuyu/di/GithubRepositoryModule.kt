package com.astro.test.glenntuyu.di

import com.astro.test.glenntuyu.data.api.GithubService
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
        githubService: GithubService,
    ): GithubRepository {
        return GithubRepositoryImpl(githubService)
    }
}