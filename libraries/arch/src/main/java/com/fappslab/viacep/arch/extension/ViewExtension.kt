package com.fappslab.viacep.arch.extension

import android.widget.EditText

fun EditText.moveCursorTodEnd() {
    setSelection(length())
}
