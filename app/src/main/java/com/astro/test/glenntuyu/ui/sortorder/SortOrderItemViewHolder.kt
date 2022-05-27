package com.astro.test.glenntuyu.ui.sortorder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.SortItemViewHolderBinding

/**
 * Created by glenntuyu on 26/05/2022.
 */
class SortOrderItemViewHolder(
    private val binding: SortItemViewHolderBinding,
    private val sortOrderBottomSheetListener: SortOrderBottomSheetListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String) {
        binding.sortItemLayout.setOnClickListener {
            sortOrderBottomSheetListener.onItemClick(text)
        }
        binding.sortItemText.text = text
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