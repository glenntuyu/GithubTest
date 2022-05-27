package com.astro.test.glenntuyu.ui.sortorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.FragmentSortOrderBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by glenntuyu on 26/05/2022.
 */
@AndroidEntryPoint
class SortOrderBottomSheetFragment: BottomSheetDialogFragment(), SortOrderBottomSheetListener {

    private var viewBinding: FragmentSortOrderBottomSheetBinding? = null
    private val viewModel: SortOrderBottomSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sort_order_bottom_sheet, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleArgs()
        initRecyclerView()
        observeViewModel()
    }

    private fun handleArgs() {
        val safeArgs: SortOrderBottomSheetFragmentArgs by navArgs()
        viewModel.setQuery(safeArgs.query)
    }

    private fun initRecyclerView() {
        viewBinding?.sortOrderBottomSheetRecyclerView?.let { rv ->
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = SortOrderBottomSheetAdapter(this)
        }
    }

    private fun observeViewModel() {
        viewModel.sortLiveData.observe(this) { type ->
            val action = SortOrderBottomSheetFragmentDirections.nextAction(type, viewModel.getQuery())
            findNavController().navigate(action)
        }
    }

    override fun onItemClick(text: String) {
        viewModel.getSortOrderType(text)
    }
}