package com.fappslab.viacep.design.extention

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.setDarkTheme() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}

fun FragmentActivity.setStatusBarTransparent() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}
