package com.fappslab.viacep.form.presentation.extension

import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.fappslab.viacep.design.R
import com.fappslab.viacep.design.dsdialogsm.build
import com.fappslab.viacep.design.dsdialogsm.dsDialogSm

internal fun Fragment.showErrorDialog(
    shouldShowError: Boolean,
    message: String?,
    onCloseAction: () -> Unit
) {
    dsDialogSm {
        titleRes = R.string.common_error_title
        messageText = message
        isCancelable = false
        buttonClose = onCloseAction::invoke
    }.build(shouldShow = shouldShowError, childFragmentManager)
}

internal fun EditText.errorState(@StringRes errorMessageRes: Int?) {
    error = errorMessageRes?.let { context.getString(it) }
}

internal fun EditText.clear() =
    text?.clear()
