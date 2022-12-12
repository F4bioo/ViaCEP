package com.fappslab.viacep.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface EditorNavigation {

    fun newInstance(host: FragmentActivity, args: ZipcodeArgs)
    fun newInstance(host: Fragment, args: ZipcodeArgs)
}
