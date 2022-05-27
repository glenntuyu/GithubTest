package com.astro.test.glenntuyu.ui

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.UserViewHolderBinding
import com.astro.test.glenntuyu.util.setTextAndCheckShow
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * Created by glenntuyu on 26/05/2022.
 */
class UserViewHolder(private val binding: UserViewHolderBinding, private val userListListener: UserListListener): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: GithubUserModel) {
        bindUserIcon(data)
        bindUserName(data)
        bindUserUrl(data)
        bindListener(data)
    }

    private fun bindUserIcon(data: GithubUserModel) {
        Glide.with(itemView.context)
            .load(data.avatarUrl)
            .transform(RoundedCorners(6))
            .placeholder(R.drawable.ic_account)
            .into(binding.userIcon)
    }

    private fun bindUserName(data: GithubUserModel) {
        binding.userName.setTextAndCheckShow(data.login)
    }

    private fun bindUserUrl(data: GithubUserModel) {
        binding.userUrl.setTextAndCheckShow(data.htmlUrl)
    }

    private fun bindListener(data: GithubUserModel) {
        binding.userItemLayout.setOnClickListener {
            userListListener.onUserItemClicked(data.id)
        }
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_view_holder

        fun create(binding: UserViewHolderBinding, userListListener: UserListListener): UserViewHolder {
            return UserViewHolder(binding, userListListener)
        }
    }
}