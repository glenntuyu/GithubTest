package com.astro.test.glenntuyu.data.api

import com.astro.test.glenntuyu.data.model.GetGithubUserResponseModel
import com.astro.test.glenntuyu.data.model.GithubUserModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by glenntuyu on 26/05/2022.
 */
interface GithubService {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        const val IN_QUALIFIER = "in:login"

        fun create(): GithubService {
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
                .create(GithubService::class.java)
        }
    }

    @GET("search/users?s=followers&type=Users")
    suspend fun getUserList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("o") order: String,
    ): GetGithubUserResponseModel

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): GithubUserModel
}