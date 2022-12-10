package com.fappslab.viacep.form.presentation.extension

import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.fappslab.viacep.arch.extension.isNull
import com.fappslab.viacep.design.R
import com.fappslab.viacep.design.dsdialogsm.build
import com.fappslab.viacep.design.dsdialogsm.dsDialogSm
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState

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

internal fun FormViewState.addressFormValidation(): Pair<FormViewState, Boolean> {
    val streetErrorRes = com.fappslab.viacep.form.R.string.form_check_field_street_blank_error
        .takeIf { address.street.isEmpty() }
    val districtErrorRes = com.fappslab.viacep.form.R.string.form_check_field_district_blank_error
        .takeIf { address.district.isEmpty() }
    val cityErrorRes = com.fappslab.viacep.form.R.string.form_check_field_city_blank_error
        .takeIf { address.city.isEmpty() }
    val stateErrorRes = com.fappslab.viacep.form.R.string.form_check_field_state_blank_error
        .takeIf { address.state.isEmpty() }
    val areaCodeErrorRes = com.fappslab.viacep.form.R.string.form_check_field_area_code_blank_error
        .takeIf { address.areaCode.isEmpty() }

    return copy(
        streetErrorRes = streetErrorRes,
        districtErrorRes = districtErrorRes,
        cityErrorRes = cityErrorRes,
        stateErrorRes = stateErrorRes,
        areaCodeErrorRes = areaCodeErrorRes,
    ) to listOf(
        streetErrorRes,
        districtErrorRes,
        cityErrorRes,
        stateErrorRes,
        areaCodeErrorRes,
    ).all { it.isNull() }
}
