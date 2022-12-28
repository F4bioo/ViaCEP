package com.fappslab.viacep.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.fappslab.viacep.R
import com.fappslab.viacep.arch.extension.navController
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupFragmentContainer()
    }

    private fun setupFragmentContainer() {
        val navController = supportFragmentManager.navController(R.id.container_fragment)
        binding.bottomMenu.setupWithNavController(navController)
    }
}
