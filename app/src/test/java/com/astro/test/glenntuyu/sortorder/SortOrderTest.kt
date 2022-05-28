package com.astro.test.glenntuyu.sortorder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.astro.test.glenntuyu.ui.sortorder.SortOrderBottomSheetViewModel
import com.astro.test.glenntuyu.util.Constant.ASCENDING
import com.astro.test.glenntuyu.util.Constant.DESCENDING
import com.astro.test.glenntuyu.util.Constant.FEWEST_FOLLOWERS
import com.astro.test.glenntuyu.util.Constant.MOST_FOLLOWERS
import com.astro.test.glenntuyu.utils.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by glenntuyu on 28/05/2022.
 */
class SortOrderTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sortOrderBottomSheetViewModel: SortOrderBottomSheetViewModel

    @Before
    fun setUp() {
        sortOrderBottomSheetViewModel = SortOrderBottomSheetViewModel()
    }

    @Test
    fun `Test sort order Most Followers`() {
        `When view call getSortOrderType`(MOST_FOLLOWERS)
        `Then assert sortOrderType is descending`()
    }

    private fun `When view call getSortOrderType`(type: String) {
        sortOrderBottomSheetViewModel.getSortOrderType(type)
    }

    private fun `Then assert sortOrderType is descending`() {
        val data = sortOrderBottomSheetViewModel.sortLiveData.value!!
        data shouldBe DESCENDING
    }

    @Test
    fun `Test sort order Fewest Followers`() {
        `When view call getSortOrderType`(FEWEST_FOLLOWERS)
        `Then assert sortOrderType is ascscending`()
    }

    private fun `Then assert sortOrderType is ascscending`() {
        val data = sortOrderBottomSheetViewModel.sortLiveData.value!!
        data shouldBe ASCENDING
    }
}