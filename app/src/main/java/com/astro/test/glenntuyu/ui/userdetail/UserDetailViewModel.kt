package com.astro.test.glenntuyu.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.domain.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by glenntuyu on 27/05/2022.
 */
@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
): ViewModel() {

    companion object {
        private const val ERROR_MESSAGE_USERNAME_IS_EMPTY = "\uD83D\uDE28 Wooops Username is empty"
    }

    private val onGetUserDetailMutableLiveData = MutableLiveData<UserDetailDataView>()
    val onGetUserDetailLiveData: LiveData<UserDetailDataView> = onGetUserDetailMutableLiveData

    private val onThrowMessageMutableLiveData = MutableLiveData<String>()
    val onThrowMessageLiveData: LiveData<String> = onThrowMessageMutableLiveData

    fun getUserDetail(username: String?) {
        username?.let {
            getUserDetailUseCase.execute(
                ::onGetUserDetailSuccess,
                ::onGetUserDetailError,
                username
            )
        } ?: run {
            onThrowMessageMutableLiveData.postValue(ERROR_MESSAGE_USERNAME_IS_EMPTY)
        }
    }

    private fun onGetUserDetailSuccess(model: GithubUserModel) {
        val dataView = model.toUserDetailDataView()
        onGetUserDetailMutableLiveData.postValue(dataView)
    }

    private fun onGetUserDetailError(throwable: Throwable) {
        onThrowMessageMutableLiveData.postValue(throwable.message)
    }
}