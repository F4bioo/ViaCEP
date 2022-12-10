package com.fappslab.viacep.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize

interface FormNavigation {

    fun newInstance(args: ZipcodeArgs = ZipcodeArgs()): Fragment
}

@Parcelize
data class ZipcodeArgs(
    val zipcode: String = ""
) : Parcelable
