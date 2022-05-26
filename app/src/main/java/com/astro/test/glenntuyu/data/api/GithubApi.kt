package com.astro.test.glenntuyu.data.api

import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by glenntuyu on 26/05/2022.
 */
interface GithubApi {

    @GET("search/users?q=repos:>1")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): GetGithubUserResponseModel

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApi::class.java)
        }
    }
}