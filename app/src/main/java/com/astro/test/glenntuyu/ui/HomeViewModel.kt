package com.astro.test.glenntuyu.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.domain.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by glenntuyu on 26/05/2022.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
): ViewModel() {

    private val userListMutableLiveData = MutableLiveData<List<GithubUserModel>>()
    val userListLiveData: LiveData<List<GithubUserModel>>
        get() = userListMutableLiveData

    fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            userListMutableLiveData.postValue(getUserListUseCase.getUserList().items)
        }
    }
}