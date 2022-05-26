package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.ActivityMainBinding
import com.astro.test.glenntuyu.ui.sort.SortBottomSheetFragment
import com.astro.test.glenntuyu.ui.sort.SortBottomSheetFragment.Companion.SORT_BOTTOM_SHEET_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(), SortBottomSheetFragment.Callback {

    private var viewBinding: ActivityMainBinding? = null
    private var sortFilterBottomSheet: SortBottomSheetFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(viewBinding?.mainToolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment) as NavHostFragment? ?: return

        val navController = host.navController
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort_dest -> openSortBottomSheet()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSortBottomSheet() {
        sortFilterBottomSheet = SortBottomSheetFragment()
        sortFilterBottomSheet?.show(supportFragmentManager, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun getSortType(type: String) {
        Toast.makeText(
            this,
            type,
            Toast.LENGTH_LONG
        ).show()
    }
}