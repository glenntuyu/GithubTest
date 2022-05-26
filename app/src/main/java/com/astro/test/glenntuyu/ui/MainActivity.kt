package com.astro.test.glenntuyu.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.astro.test.glenntuyu.R
import com.astro.test.glenntuyu.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(viewBinding?.mainToolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment) as NavHostFragment? ?: return

        val navController = host.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}