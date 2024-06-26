package com.astro.test.glenntuyu.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.domain.GetUserListUseCase
import com.astro.test.glenntuyu.ui.intent.HomeIntent
import com.astro.test.glenntuyu.ui.state.HomeState
import com.astro.test.glenntuyu.util.Constant.ASCENDING
import com.astro.test.glenntuyu.util.Constant.DEFAULT_QUERY
import com.astro.test.glenntuyu.util.Constant.LAST_QUERY_SCROLLED
import com.astro.test.glenntuyu.util.Constant.LAST_SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by glenntuyu on 26/05/2022.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private var sortOrder = ASCENDING
    private var initialQuery = savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
    val state: StateFlow<HomeState>
    val userPagingDataFlow: Flow<PagingData<GithubUserModel>>
    val accept: (HomeIntent) -> Unit

    init {
        val lastQueryScrolled: String = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<HomeIntent>()
        val searches = actionStateFlow
            .filterIsInstance<HomeIntent.Search>()
            .distinctUntilChanged()
        val queriesScrolled = actionStateFlow
            .filterIsInstance<HomeIntent.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(HomeIntent.Scroll(currentQuery = lastQueryScrolled)) }

        userPagingDataFlow = searches
            .flatMapLatest {
                getUserListUseCase.getUserList(it.query, sortOrder)
            }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            HomeState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = HomeState()
            )

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }
    }

    fun setSortOrder(sortOrder: String) {
        this.sortOrder = sortOrder
    }

    fun getSortOrder(): String {
        return sortOrder
    }

    fun setQuery(query: String) {
        initialQuery = query
    }

    fun getInitialQuery(): String {
        return initialQuery
    }

    fun getQuery(): String {
        return state.value.query
    }

    fun startSearch() {
        accept.invoke(HomeIntent.Search(query = initialQuery))
    }

    override fun onCleared() {
        setLastSearchQuery()
        super.onCleared()
    }

    private fun setLastSearchQuery() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
    }
}