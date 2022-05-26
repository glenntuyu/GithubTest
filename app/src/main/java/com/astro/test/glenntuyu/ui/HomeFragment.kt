package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

        observeViewModel()
        viewModel.getUserList()
    }

    private fun observeViewModel() {
        viewModel.userListLiveData.observe(this::submitList)
    }

    private fun <T> LiveData<T>.observe(observer: Observer<T>) {
        observe(viewLifecycleOwner, observer)
    }

    private fun submitList(list: List<GithubUserModel>) {
        Log.d("HomeFragment", list.size.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}