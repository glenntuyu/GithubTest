package com.astro.test.glenntuyu.ui.sortorder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.SortItemViewHolderBinding
import com.astro.test.glenntuyu.util.Constant.ASCENDING
import com.astro.test.glenntuyu.util.Constant.DESCENDING
import com.astro.test.glenntuyu.util.Constant.FEWEST_FOLLOWERS
import com.astro.test.glenntuyu.util.Constant.MOST_FOLLOWERS

/**
 * Created by glenntuyu on 26/05/2022.
 */
class SortOrderItemViewHolder(
    private val binding: SortItemViewHolderBinding,
    private val sortOrderBottomSheetListener: SortOrderBottomSheetListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String, currentSortOrder: String) {
        bindListener(text)
        bindText(text, currentSortOrder)
    }

    private fun bindText(text: String, currentSortOrder: String) {
       val currentType = getCurrentType(currentSortOrder)
        binding.sortItemText.text = text
        setTextColor(text, currentType)
    }

    private fun getCurrentType(currentSortOrder: String): String {
        return when(currentSortOrder) {
            ASCENDING -> FEWEST_FOLLOWERS
            DESCENDING -> MOST_FOLLOWERS
            else -> ""
        }
    }

    private fun setTextColor(text: String, currentType: String) {
        if (text == currentType) {
            binding.sortItemText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.teal_200))
        }
    }

    private fun bindListener(text: String) {
        binding.sortItemLayout.setOnClickListener {
            sortOrderBottomSheetListener.onItemClick(text)
        }
    }

    companion object {
        fun create(parent: ViewGroup, sortOrderBottomSheetListener: SortOrderBottomSheetListener): SortOrderItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sort_item_view_holder, parent, false)
            val binding = SortItemViewHolderBinding.bind(view)
            return SortOrderItemViewHolder(binding, sortOrderBottomSheetListener)
        }
    }
}