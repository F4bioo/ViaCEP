package com.fappslab.viacep.editor.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fappslab.viacep.arch.args.createIntent
import com.fappslab.viacep.arch.args.putArguments
import com.fappslab.viacep.arch.args.viewArgs
import com.fappslab.viacep.arch.viewbinding.viewBinding
import com.fappslab.viacep.editor.R
import com.fappslab.viacep.editor.databinding.EditorActivityBinding
import com.fappslab.viacep.navigation.FormNavigation
import com.fappslab.viacep.navigation.ZipcodeArgs
import org.koin.android.ext.android.inject

internal class EditorActivity : AppCompatActivity(R.layout.editor_activity) {

    private val binding: EditorActivityBinding by viewBinding()
    private val formNavigation: FormNavigation by inject()
    private val args: ZipcodeArgs by viewArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(binding.containerFragment.id, formNavigation.newInstance(args))
        }
    }

    companion object {
        fun createIntent(context: Context, args: ZipcodeArgs): Intent =
            context.createIntent<EditorActivity> { putArguments(args) }
    }
}
