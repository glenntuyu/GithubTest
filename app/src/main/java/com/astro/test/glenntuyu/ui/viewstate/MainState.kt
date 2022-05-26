package com.astro.test.glenntuyu.ui.viewstate

import androidx.paging.PagingData
import com.astro.test.glenntuyu.data.model.GithubUserModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by glenntuyu on 26/05/2022.
 */
sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Users(val user: Flow<PagingData<GithubUserModel>>) : MainState()
    data class Error(val error: String?) : MainState()
}