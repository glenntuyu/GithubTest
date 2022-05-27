package com.astro.test.glenntuyu.domain

/**
 * Created by glenntuyu on 27/05/2022.
 */
sealed class Result<out T: Any>
data class Success<out T: Any>(val data: T): Result<T>()
data class Fail(val throwable: Throwable): Result<Nothing>()