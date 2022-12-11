package com.fappslab.viacep.navigation

import android.content.Context
import android.content.Intent

interface EditorNavigation {

    fun newInstance(context: Context, args: ZipcodeArgs): Intent
}
