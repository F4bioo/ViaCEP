package com.fappslab.viacep.logbook.presentation.extension

import com.fappslab.viacep.arch.extension.isNotNull
import com.fappslab.viacep.design.dsmodal.dsModal
import com.fappslab.viacep.logbook.presentation.LogbookFragment
import com.fappslab.viacep.navigation.FormNavigation

internal fun LogbookFragment.showEditorBottomSheet(
    shouldShowEditorBottomSheet: Boolean,
    zipcode: String,
    formNavigation: FormNavigation,
    onCloseAction: () -> Unit
) {
    if (shouldShowEditorBottomSheet) {
        takeIf {
            childFragmentManager.findFragmentByTag(javaClass.simpleName).isNotNull()
        } ?: dsModal {
            shouldBlock = true
            onFragment = {
                formNavigation.newInstance(zipcode) {
                    onCloseAction()
                    dismiss()
                }
            }
            onCloseButton = {
                onCloseAction()
                dismiss()
            }
        }.show(childFragmentManager, javaClass.simpleName)
    }
}
