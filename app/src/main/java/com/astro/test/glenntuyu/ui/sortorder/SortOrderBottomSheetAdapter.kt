package com.astro.test.glenntuyu.ui.sortorder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.util.Constant.FEWEST_FOLLOWERS
import com.astro.test.glenntuyu.util.Constant.MOST_FOLLOWERS

/**
 * Created by glenntuyu on 26/05/2022.
 */
class SortOrderBottomSheetAdapter(
    private val sortOrderBottomSheetListener: SortOrderBottomSheetListener,
) : RecyclerView.Adapter<SortOrderItemViewHolder>() {

    private var currentSortOrder: String = ""

    private val sortList = listOf(
        FEWEST_FOLLOWERS,
        MOST_FOLLOWERS,
    )

    fun setCurrentSortOrder(sortOrder: String) {
        this.currentSortOrder = sortOrder
    }

    override fun onBindViewHolder(holder: SortOrderItemViewHolder, position: Int) {
        holder.bind(sortList[position], currentSortOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortOrderItemViewHolder {
        return SortOrderItemViewHolder.create(parent, sortOrderBottomSheetListener)
    }

    override fun getItemCount(): Int {
        return sortList.size
    }
}