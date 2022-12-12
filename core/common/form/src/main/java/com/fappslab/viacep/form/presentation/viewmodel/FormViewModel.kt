package com.fappslab.viacep.form.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.viacep.arch.extension.toZipcodeFormatter
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.domain.usecase.SetLocalAddressUseCase
import com.fappslab.viacep.form.presentation.extension.addressFormValidation
import com.fappslab.viacep.form.presentation.extension.toAddress
import com.fappslab.viacep.form.presentation.extension.toAddressArgs
import kotlinx.coroutines.launch

class FormViewModel(
    private val getRemoteAddressUseCase: GetRemoteAddressUseCase,
    private val getLocalAddressUseCase: GetLocalAddressUseCase,
    private val setLocalAddressUseCase: SetLocalAddressUseCase
) : ViewModel<FormViewState, FormViewAction>(FormViewState()) {

    private val address get() = state.value.address

    fun onGetRemoteAddress(zipcode: String) {
        viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                getRemoteAddressUseCase(zipcode)
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = { cause -> onState { it.showErrorState(cause.message) } },
                onSuccess = { address -> onState { it.copy(address = address.toAddressArgs()) } }
            )
        }
    }

    fun onSetLocalAddress() {
        if (areInputsPopulated()) viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                setLocalAddressUseCase(address.toAddress())
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = { cause -> onState { it.showErrorState(cause.message) } },
                onSuccess = { onAction { FormViewAction.ClearForm } }
            )
        }
    }

    fun onGetLocalAddress(zipcode: String) {
        if (zipcode.isNotEmpty()) viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                getLocalAddressUseCase(zipcode)
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = { cause -> onState { it.showErrorState(cause.message) } },
                onSuccess = { address -> onState { it.copy(address = address.toAddressArgs()) } }
            )
        }
    }

    fun onTextChangedZipcode(zipcode: String) {
        onState { it.copy(address = address.copy(zipcode = zipcode.toZipcodeFormatter())) }
    }

    fun onTextChangedStreet(street: String) = onState {
        it.copy(address = address.copy(street = street.trim()))
            .run { if (street.isNotBlank()) copy(streetErrorRes = null) else this }
    }

    fun onTextChangedDistrict(district: String) = onState {
        it.copy(address = address.copy(district = district.trim()))
            .run { if (district.isNotBlank()) copy(districtErrorRes = null) else this }
    }

    fun onTextChangedCity(city: String) = onState {
        it.copy(address = address.copy(city = city.trim()))
            .run { if (city.isNotBlank()) copy(cityErrorRes = null) else this }
    }

    fun onTextChangedState(state: String) = onState {
        it.copy(address = address.copy(state = state.trim()))
            .run { if (state.isNotBlank()) copy(stateErrorRes = null) else this }
    }

    fun onTextChangedAreaCode(areaCode: String) = onState {
        it.copy(address = address.copy(areaCode = areaCode.trim()))
            .run { if (areaCode.isNotBlank()) copy(areaCodeErrorRes = null) else this }
    }

    fun onCloseError() {
        onState { it.hideErrorState() }
    }

    fun onFinishView() {
        onAction { FormViewAction.FinishView }
    }

    private fun areInputsPopulated(): Boolean = state.value.run {
        val (updateErrorStates, areInputsPopulated) = addressFormValidation()
        onState { updateErrorStates }
        areInputsPopulated
    }
}
