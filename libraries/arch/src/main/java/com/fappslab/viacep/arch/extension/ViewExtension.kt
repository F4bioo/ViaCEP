package com.fappslab.viacep.arch.extension

import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun EditText.moveCursorTodEnd() {
    setSelection(length())
}

fun Fragment.setOnBackPressedDispatcher(onBackPressedBlock: () -> Unit) {
    object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedBlock()
        }
    }.also { activity?.onBackPressedDispatcher?.addCallback(it) }
}

fun FragmentManager.navController(@IdRes containerId: Int): NavController {
    return (findFragmentById(containerId) as NavHostFragment).navController
}
