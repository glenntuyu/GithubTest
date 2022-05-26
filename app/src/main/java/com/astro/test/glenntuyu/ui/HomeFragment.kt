package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.HomeFragmentBinding
import com.astro.test.glenntuyu.ui.intent.MainIntent
import com.astro.test.glenntuyu.ui.viewstate.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
        observeViewModel()
        getUserList()
    }

    private fun initRecyclerView() {
        viewBinding?.homeRecyclerView?.let { rv ->
            adapter = HomeAdapter()

            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
//                        buttonFetchUser.visibility = View.GONE
//                        progressBar.visibility = View.VISIBLE
                    }

                    is MainState.Users -> {
//                        progressBar.visibility = View.GONE
//                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
//                        progressBar.visibility = View.GONE
//                        buttonFetchUser.visibility = View.VISIBLE
//                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private suspend fun renderList(users: Flow<PagingData<GithubUserModel>>) {
        users.collectLatest { list ->
            viewBinding?.homeRecyclerView?.visibility = View.VISIBLE
            adapter?.submitData(list)
        }
    }

    private fun getUserList() {
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchUser)
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