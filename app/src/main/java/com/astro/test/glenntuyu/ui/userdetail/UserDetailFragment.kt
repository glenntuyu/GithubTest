package com.astro.test.glenntuyu.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var viewBinding: FragmentUserDetailBinding? = null
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        getUserDetail()
    }

    private fun observeViewModel() {
        viewModel.onGetUserDetailLiveData.observe(viewLifecycleOwner, this::setUserDetail)
        viewModel.onThrowMessageLiveData.observe(viewLifecycleOwner, this::onThrowMessage)
    }

    private fun setUserDetail(dataView: UserDetailDataView) {
        viewBinding?.dataView = dataView
    }

    private fun onThrowMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun getUserDetail() {
        val safeArgs: UserDetailFragmentArgs by navArgs()
        viewModel.getUserDetail(safeArgs.username)
    }
}