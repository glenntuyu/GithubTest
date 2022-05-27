package com.astro.test.glenntuyu.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(viewBinding?.mainToolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment) as NavHostFragment? ?: return

        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort_order_dest -> setCurrentQuery()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentQuery() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)
        (navHostFragment?.childFragmentManager?.fragments?.get(0) as? HomeFragment)?.putQueryToArguments()
    }
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.main_fragment).navigateUp(appBarConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}