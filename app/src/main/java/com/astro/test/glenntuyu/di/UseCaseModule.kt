package com.astro.test.glenntuyu.di

import com.astro.test.glenntuyu.data.repository.GithubRepository
import com.astro.test.glenntuyu.domain.GetUserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by glenntuyu on 26/05/2022.
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetUserListUseCase(
        repository: GithubRepository
    ): GetUserListUseCase {
        return GetUserListUseCase(repository)
    }
}