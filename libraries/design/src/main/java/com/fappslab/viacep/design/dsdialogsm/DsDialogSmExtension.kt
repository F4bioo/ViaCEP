package com.fappslab.viacep.design.dsdialogsm

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.viacep.arch.extension.isNotNull

private const val TAG_DEFAULT = "DsDialogSm"

// Common
@Suppress("unused")
fun LifecycleOwner.dsDialogSm(
    block: DsDialogSm.() -> Unit = {}
): DsDialogSm = DsDialogSm().apply(block)

fun DsDialogSm.build(
    shouldShow: Boolean,
    manager: FragmentManager
) {
    manager.hide()
    if (shouldShow) {
        show(manager, TAG_DEFAULT)
    }
}

// Support
private fun FragmentManager.hide() {
    if (isShowing()) {
        val dialog = findFragmentByTag(TAG_DEFAULT)
        if (dialog is DsDialogSm) dialog.dismissAllowingStateLoss()
    }
}

private fun FragmentManager.isShowing(): Boolean {
    executePendingTransactions()
    return findFragmentByTag(TAG_DEFAULT).isNotNull()
}
