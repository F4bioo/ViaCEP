package com.fappslab.viacep.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fappslab.viacep.R
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.databinding.ActivityMainBinding
import com.fappslab.viacep.navigation.FormNavigation
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()
    private val formNavigation: FormNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(binding.containerFragment.id, formNavigation.newInstance())
        }
    }
}
