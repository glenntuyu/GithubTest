package com.astro.test.glenntuyu.util

import android.view.View
import android.widget.TextView

/**
 * Created by glenntuyu on 26/05/2022.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun TextView.setTextAndCheckShow(text: String?) {
    if (text.isNullOrEmpty()) {
        gone()
    } else {
        setText(text)
        visible()
    }
}