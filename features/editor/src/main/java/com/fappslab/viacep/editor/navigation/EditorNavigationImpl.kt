package com.fappslab.viacep.editor.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.fappslab.viacep.editor.presentation.EditorBottomSheet
import com.fappslab.viacep.navigation.EditorNavigation
import com.fappslab.viacep.navigation.ZipcodeArgs

internal class EditorNavigationImpl : EditorNavigation {

    override fun newInstance(host: FragmentActivity, args: ZipcodeArgs) {
        EditorBottomSheet.newInstance(host, args)
    }

    override fun newInstance(host: Fragment, args: ZipcodeArgs) {
        EditorBottomSheet.newInstance(host, args)
    }
}
