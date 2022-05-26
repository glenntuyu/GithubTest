package com.astro.test.glenntuyu.ui.intent

/**
 * Created by glenntuyu on 26/05/2022.
 */
sealed class HomeIntent {
    data class Search(val query: String) : HomeIntent()
    data class Scroll(val currentQuery: String) : HomeIntent()
}