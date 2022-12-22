package com.fappslab.viacep.form.presentation.viewmodel

import androidx.annotation.StringRes
import com.fappslab.viacep.arch.viewmodel.State
import com.fappslab.viacep.form.presentation.model.AddressArgs

data class FormViewState(
    val address: AddressArgs = AddressArgs(),
    val shouldEnableZipcodeInput: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String? = null,
    @StringRes val streetErrorRes: Int? = null,
    @StringRes val districtErrorRes: Int? = null,
    @StringRes val cityErrorRes: Int? = null,
    @StringRes val stateErrorRes: Int? = null,
    @StringRes val areaCodeErrorRes: Int? = null
) : State {

    fun showErrorState(errorMessage: String?) = copy(
        shouldShowError = true,
        errorMessage = errorMessage
    )

    fun hideErrorState() = copy(
        shouldShowError = false,
        errorMessage = null
    )
}
