package com.fappslab.viacep.form.presentation.extension

import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.fappslab.viacep.arch.extension.isNull
import com.fappslab.viacep.design.dsdialogsm.build
import com.fappslab.viacep.design.dsdialogsm.dsDialogSm
import com.fappslab.viacep.form.R
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import com.fappslab.viacep.design.R as DS

internal fun Fragment.showErrorDialog(
    shouldShowError: Boolean,
    message: String?,
    onCloseAction: () -> Unit
) {
    dsDialogSm {
        titleRes = DS.string.common_error_title
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
    val streetErrorRes = R.string.form_check_field_street_blank_error
        .takeIf { address.street.isBlank() }
    val districtErrorRes = R.string.form_check_field_district_blank_error
        .takeIf { address.district.isBlank() }
    val cityErrorRes = R.string.form_check_field_city_blank_error
        .takeIf { address.city.isBlank() }
    val stateErrorRes = R.string.form_check_field_state_blank_error
        .takeIf { address.state.isBlank() }
    val areaCodeErrorRes = R.string.form_check_field_area_code_blank_error
        .takeIf { address.areaCode.isBlank() }

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
