package com.fappslab.viacep.form.presentation.viewmodel

import androidx.annotation.StringRes
import com.fappslab.viacep.arch.viewmodel.ViewState

data class FormViewState(
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String? = null,
    val zipcode: String? = null,
    val street: String? = null,
    val district: String? = null,
    val city: String? = null,
    val state: String? = null,
    val areaCode: String? = null,
    @StringRes val streetErrorRes: Int? = null,
    @StringRes val districtErrorRes: Int? = null,
    @StringRes val cityErrorRes: Int? = null,
    @StringRes val stateErrorRes: Int? = null,
    @StringRes val areaCodeErrorRes: Int? = null
) : ViewState {

    fun errorUpdate(
        shouldShowError: Boolean,
        errorMessage: String?
    ) = copy(
        shouldShowError = shouldShowError,
        errorMessage = errorMessage
    )
}
