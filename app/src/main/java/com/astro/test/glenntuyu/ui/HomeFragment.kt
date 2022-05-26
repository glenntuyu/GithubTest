package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.HomeFragmentBinding

/**
 * Created by glenntuyu on 26/05/2022.
 */
class HomeFragment: Fragment() {

    private var viewBinding: HomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        return viewBinding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}