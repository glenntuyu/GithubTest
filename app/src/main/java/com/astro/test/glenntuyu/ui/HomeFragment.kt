package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.HomeFragmentBinding
import com.astro.test.glenntuyu.ui.intent.HomeIntent
import com.astro.test.glenntuyu.ui.viewstate.HomeState
import com.astro.test.glenntuyu.util.TimerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by glenntuyu on 26/05/2022.
 */
@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var viewBinding: HomeFragmentBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
//        observeViewModel()
//        getUserList()
        viewBinding?.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.userPagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun initRecyclerView() {
        viewBinding?.homeRecyclerView?.let { rv ->
            adapter = HomeAdapter()

            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter

            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            rv.addItemDecoration(decoration)
        }
    }

    private fun HomeFragmentBinding.bindState(
        uiState: StateFlow<HomeState>,
        pagingData: Flow<PagingData<GithubUserModel>>,
        uiActions: (HomeIntent) -> Unit
    ) {
        val repoAdapter = HomeAdapter()
        homeRecyclerView.adapter = repoAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { repoAdapter.retry() },
            footer = LoadStateAdapter { repoAdapter.retry() }
        )
        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )
        bindList(
            repoAdapter = repoAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun HomeFragmentBinding.bindSearch(
        uiState: StateFlow<HomeState>,
        onQueryChanged: (HomeIntent.Search) -> Unit
    ) {
        homeEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateSearchListFromInput(onQueryChanged)
            }
        })
    }

    private fun HomeFragmentBinding.updateSearchListFromInput(onQueryChanged: (HomeIntent.Search) -> Unit) {
        TimerUtil.scheduleCanceler(runnable = {
            homeEditText.text.trim().let {
                homeRecyclerView.smoothScrollToPosition(0)
                onQueryChanged(HomeIntent.Search(query = it.toString()))
            }
        })
    }

    private fun HomeFragmentBinding.bindList(
        repoAdapter: HomeAdapter,
        uiState: StateFlow<HomeState>,
        pagingData: Flow<PagingData<GithubUserModel>>,
        onScrollChanged: (HomeIntent.Scroll) -> Unit
    ) {
        homeRetryButton.setOnClickListener { repoAdapter.retry() }
        homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(HomeIntent.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = repoAdapter.loadStateFlow
            // Only emit when REFRESH LoadState for the paging source changes.
            .distinctUntilChangedBy { it.source.refresh }
            // Only react to cases where REFRESH completes i.e., NotLoading.
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(repoAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) homeRecyclerView.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0
                // show empty list
                homeEmptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds.
                homeRecyclerView.isVisible = !isListEmpty
                // Show loading spinner during initial load or refresh.
                homeProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                homeRetryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}