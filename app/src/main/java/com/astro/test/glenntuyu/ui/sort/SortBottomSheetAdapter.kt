package com.astro.test.glenntuyu.ui.sort

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.util.Constant.FEWEST_FOLLOWERS
import com.astro.test.glenntuyu.util.Constant.MOST_FOLLOWERS

/**
 * Created by glenntuyu on 26/05/2022.
 */
class SortBottomSheetAdapter(
    private val sortBottomSheetListener: SortBottomSheetListener,
) : RecyclerView.Adapter<SortItemViewHolder>() {

    private val sortList = listOf(
        MOST_FOLLOWERS,
        FEWEST_FOLLOWERS,
    )

    override fun onBindViewHolder(holder: SortItemViewHolder, position: Int) {
        holder.bind(sortList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortItemViewHolder {
        return SortItemViewHolder.create(parent, sortBottomSheetListener)
    }

    override fun getItemCount(): Int {
        return sortList.size
    }
}