package com.astro.test.glenntuyu.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.SortBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by glenntuyu on 26/05/2022.
 */
@AndroidEntryPoint
class SortBottomSheetFragment: BottomSheetDialogFragment(), SortBottomSheetListener {
    companion object {
        const val SORT_BOTTOM_SHEET_TAG = "SORT_BOTTOM_SHEET_TAG"
    }

    private var viewBinding: SortBottomSheetFragmentBinding? = null
    private val viewModel: SortBottomSheetViewModel by viewModels()
    private var sortCallback: Callback? = null

    fun show(
        fragmentManager: FragmentManager,
        callback: Callback,
    ) {
        this.sortCallback = callback
        show(fragmentManager, SORT_BOTTOM_SHEET_TAG)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.sort_bottom_sheet_fragment, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        viewBinding?.sortBottomSheetRecyclerView?.let { rv ->
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = SortBottomSheetAdapter(this)
        }
    }

    private fun observeViewModel() {
        viewModel.sortLiveData.observe(this) {
            sortCallback?.getSortType(it)
            this.dismiss()
        }
    }

    override fun onItemClick(text: String) {
        viewModel.getSortType(text)
    }

    interface Callback {
        fun getSortType(type: String)
    }
}