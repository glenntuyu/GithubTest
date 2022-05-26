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
class UserViewHolder(private val binding: UserViewHolderBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: GithubUserModel) {
        Glide.with(itemView.context)
            .load(data.avatarUrl)
            .transform(RoundedCorners(6))
            .placeholder(R.drawable.ic_account)
            .into(binding.userIcon)
        binding.userName.setTextAndCheckShow(data.login)
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_view_holder

        fun create(binding: UserViewHolderBinding): UserViewHolder {
            return UserViewHolder(binding)
        }
    }
}