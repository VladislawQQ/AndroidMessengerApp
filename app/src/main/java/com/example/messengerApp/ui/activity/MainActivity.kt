package com.example.messengerApp.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.messengerApp.R
import com.example.messengerApp.databinding.ActivityMainBinding
import com.example.messengerApp.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    override fun setIncomingArguments(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) return
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_nav_graph, intent.extras)
    }
}