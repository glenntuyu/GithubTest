package com.astro.test.glenntuyu.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.UserViewHolderBinding

/**
 * Created by glenntuyu on 26/05/2022.
 */
class HomeAdapter(private val userListListener: UserListListener): PagingDataAdapter<GithubUserModel, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = DataBindingUtil.inflate<UserViewHolderBinding>(
            LayoutInflater.from(parent.context), UserViewHolder.LAYOUT, parent, false
        )
        return UserViewHolder.create(binding, userListListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { (holder as UserViewHolder).bind(it) }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GithubUserModel>() {
            override fun areItemsTheSame(oldItem: GithubUserModel, newItem: GithubUserModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GithubUserModel, newItem: GithubUserModel): Boolean =
                oldItem == newItem
        }
    }

}