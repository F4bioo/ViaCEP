package com.fappslab.viacep.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fappslab.viacep.R
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.databinding.ActivityMainBinding
import com.fappslab.viacep.form.presentation.FormFragment
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val formViewModel: FormViewModel by viewModel()
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(binding.containerFragment.id, FormFragment())
        }
    }

    override fun onBackPressed() {
        formViewModel.onFinishView()
    }
}
