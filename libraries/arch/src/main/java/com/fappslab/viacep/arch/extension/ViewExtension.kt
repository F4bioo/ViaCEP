package com.fappslab.viacep.arch.extension

import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

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
