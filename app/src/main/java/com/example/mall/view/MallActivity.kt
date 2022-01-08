package com.example.mall.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.mall.R
import com.example.mall.databinding.ActivityMallBinding

class MallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMallBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavView()
    }

    private fun initNavView() {
        navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(binding.navView, navController)
    }
}