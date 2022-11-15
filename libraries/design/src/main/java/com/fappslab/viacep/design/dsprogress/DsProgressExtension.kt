package com.fappslab.viacep.design.dsprogress

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.viacep.arch.extension.isNotNull

private const val TAG_DEFAULT = "DsProgress"

// Common
@Suppress("unused")
fun LifecycleOwner.dsProgress(
    block: DsProgress.() -> Unit = {}
): DsProgress = DsProgress().apply(block)

fun DsProgress.build(
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
        if (dialog is DsProgress) dialog.dismissAllowingStateLoss()
    }
}

private fun FragmentManager.isShowing(): Boolean {
    executePendingTransactions()
    return findFragmentByTag(TAG_DEFAULT).isNotNull()
}
