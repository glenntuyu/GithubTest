package com.astro.test.glenntuyu.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.astro.test.glenntuyu.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by glenntuyu on 27/05/2022.
 */
object ViewBinding {

    @JvmStatic
    @BindingAdapter("app:icon")
    fun loadIcon(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context)
                .load(it).apply(RequestOptions().circleCrop())
                .placeholder(R.drawable.ic_account)
                .into(view)
        }
    }

    @BindingAdapter("app:shouldShowView")
    @JvmStatic
    fun shouldShowView(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:showTextIfNotEmpty")
    @JvmStatic
    fun showTextIfNotEmpty(textView: TextView, text: String?) {
        textView.setTextAndCheckShow(text)
    }
}