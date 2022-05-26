package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getUserList()
    }

    private fun initRecyclerView() {
        viewBinding?.homeRecyclerView?.let { rv ->
            adapter = HomeAdapter()

            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
        }
    }

    private fun observeViewModel() {
        viewModel.userListLiveData.observe(viewLifecycleOwner) {
            adapter?.submitData(lifecycle, it)
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