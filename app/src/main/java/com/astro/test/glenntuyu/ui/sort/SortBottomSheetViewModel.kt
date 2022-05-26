package com.astro.test.glenntuyu.ui.sort

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astro.test.glenntuyu.util.Constant.ASCENDING
import com.astro.test.glenntuyu.util.Constant.DESCENDING
import com.astro.test.glenntuyu.util.Constant.FEWEST_FOLLOWERS
import com.astro.test.glenntuyu.util.Constant.MOST_FOLLOWERS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by glenntuyu on 26/05/2022.
 */
@HiltViewModel
class SortBottomSheetViewModel @Inject constructor(): ViewModel() {

    private val sortMutableLiveData = MutableLiveData<String>()
    val sortLiveData: LiveData<String>
        get() = sortMutableLiveData

    fun getSortType(text: String) {
        if (text == MOST_FOLLOWERS) {
            sortMutableLiveData.postValue(DESCENDING)
        } else if (text == FEWEST_FOLLOWERS) {
            sortMutableLiveData.postValue(ASCENDING)
        }
    }
}