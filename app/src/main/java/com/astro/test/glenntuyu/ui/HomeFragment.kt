package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.FragmentHomeBinding
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
class HomeFragment: Fragment(), UserListListener {

    private var viewBinding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleArgs()
        toastSortOrder()
        initEditText()
        viewBinding?.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.userPagingDataFlow,
            uiActions = viewModel.accept
        )
        viewModel.startSearch()
    }

    private fun handleArgs() {
        val safeArgs: HomeFragmentArgs by navArgs()
        viewModel.setSortOrder(safeArgs.sortOrder)
        viewModel.setQuery(safeArgs.query)
    }

    private fun toastSortOrder() {
        Toast.makeText(context, "Sort with order ${viewModel.getSortOrder()}", Toast.LENGTH_SHORT).show()
    }

    private fun initEditText() {
        viewBinding?.homeEditText?.setText(viewModel.getInitialQuery())
    }

    private fun FragmentHomeBinding.bindState(
        uiState: StateFlow<HomeState>,
        pagingData: Flow<PagingData<GithubUserModel>>,
        uiActions: (HomeIntent) -> Unit
    ) {
        val homeAdapter = HomeAdapter(this@HomeFragment)
        homeRecyclerView.adapter = homeAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { homeAdapter.retry() },
            footer = LoadStateAdapter { homeAdapter.retry() },
        )
        bindSearch(
            onQueryChanged = uiActions,
        )
        bindList(
            adapter = homeAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions,
        )
    }

    private fun FragmentHomeBinding.bindSearch(
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

    private fun FragmentHomeBinding.updateSearchListFromInput(onQueryChanged: (HomeIntent.Search) -> Unit) {
        TimerUtil.scheduleCanceler(runnable = {
            homeEditText.text.trim().let {
                val query = it.toString()
                homeRecyclerView.smoothScrollToPosition(0)
                onQueryChanged(HomeIntent.Search(query = query))
            }
        })
    }

    private fun FragmentHomeBinding.bindList(
        adapter: HomeAdapter,
        uiState: StateFlow<HomeState>,
        pagingData: Flow<PagingData<GithubUserModel>>,
        onScrollChanged: (HomeIntent.Scroll) -> Unit
    ) {
        homeRetryButton.setOnClickListener { adapter.retry() }
        homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(HomeIntent.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
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
            pagingData.collectLatest(adapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) homeRecyclerView.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                homeEmptyList.isVisible = isListEmpty
                homeRecyclerView.isVisible = !isListEmpty
                homeProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                homeRetryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    fun putQueryToArguments() {
        val action = HomeFragmentDirections.moveToSortOrder(viewModel.getQuery())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onUserItemClicked(username: String) {
        val action = HomeFragmentDirections.moveToUserDetail(username)
        findNavController().navigate(action)
    }
}