package com.fappslab.viacep.editor.navigation

import android.content.Context
import android.content.Intent
import com.fappslab.viacep.editor.presentation.EditorActivity
import com.fappslab.viacep.navigation.EditorNavigation
import com.fappslab.viacep.navigation.ZipcodeArgs

internal class EditorNavigationImpl : EditorNavigation {

    override fun newInstance(context: Context, args: ZipcodeArgs): Intent =
        EditorActivity.newInstance(context, args)
}
