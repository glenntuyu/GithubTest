package com.astro.test.glenntuyu.ui.state

import com.astro.test.glenntuyu.util.Constant.DEFAULT_QUERY

/**
 * Created by glenntuyu on 26/05/2022.
 */
data class HomeState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)