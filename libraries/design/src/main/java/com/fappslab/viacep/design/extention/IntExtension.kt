package com.fappslab.viacep.design.extention

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Int.toColor(context: Context) =
    ContextCompat.getColor(context, this)

fun Int.toColorStateList() =
    ColorStateList.valueOf(this)

fun Int.toDrawable(context: Context) =
    AppCompatResources.getDrawable(context, this)
