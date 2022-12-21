package com.fappslab.viacep.form.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.viacep.arch.resultbuilder.runResultBuilder
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.domain.usecase.SetLocalAddressUseCase
import com.fappslab.viacep.form.presentation.extension.addressFormValidation
import com.fappslab.viacep.form.presentation.extension.toAddress
import com.fappslab.viacep.form.presentation.extension.toAddressArgs

class FormViewModel(
    private val zipcode: String,
    private val getRemoteAddressUseCase: GetRemoteAddressUseCase,
    private val getLocalAddressUseCase: GetLocalAddressUseCase,
    private val setLocalAddressUseCase: SetLocalAddressUseCase,
) : ViewModel<FormViewState, FormViewAction>(FormViewState()) {

    init {
        initUISate()
    }

    fun onGetRemoteAddress(zipcode: String) {
        runResultBuilder {
            getRemoteAddressUseCase(zipcode)
        }.start {
            onState { it.copy(shouldShowLoading = true) }
        }.complete {
            onState { it.copy(shouldShowLoading = false) }
        }.catch { cause ->
            onState { it.showErrorState(cause.message) }
        }.result { address ->
            onState { it.copy(address = address.toAddressArgs()) }
        }.buildIn(viewModelScope)
    }

    fun onSetLocalAddress() = state.value.run {
        if (areInputsPopulated()) runResultBuilder {
            setLocalAddressUseCase(address.toAddress())
        }.start {
            onState { it.copy(shouldShowLoading = true) }
        }.complete {
            onState { it.copy(shouldShowLoading = false) }
        }.catch { cause ->
            onState { it.showErrorState(cause.message) }
        }.result {
            onAction { FormViewAction.ClearForm }
        }.buildIn(viewModelScope)
    }

    fun onGetLocalAddress() {
        if (zipcode.isNotEmpty()) runResultBuilder {
            getLocalAddressUseCase(zipcode)
        }.start {
            onState { it.copy(shouldShowLoading = true) }
        }.complete {
            onState { it.copy(shouldShowLoading = false) }
        }.catch { cause ->
            onState { it.showErrorState(cause.message) }
        }.result { address ->
            onState { it.copy(address = address.toAddressArgs()) }
        }.buildIn(viewModelScope)
    }

    fun onTextChangedZipcode(zipcode: String) = onState {
        it.copy(address = it.address.copy(zipcode = zipcode))
    }

    fun onTextChangedStreet(street: String) = onState {
        it.copy(address = it.address.copy(street = street))
            .run { if (street.isNotBlank()) copy(streetErrorRes = null) else this }
    }

    fun onTextChangedDistrict(district: String) = onState {
        it.copy(address = it.address.copy(district = district))
            .run { if (district.isNotBlank()) copy(districtErrorRes = null) else this }
    }

    fun onTextChangedCity(city: String) = onState {
        it.copy(address = it.address.copy(city = city))
            .run { if (city.isNotBlank()) copy(cityErrorRes = null) else this }
    }

    fun onTextChangedState(state: String) = onState {
        it.copy(address = it.address.copy(state = state))
            .run { if (state.isNotBlank()) copy(stateErrorRes = null) else this }
    }

    fun onTextChangedAreaCode(areaCode: String) = onState {
        it.copy(address = it.address.copy(areaCode = areaCode))
            .run { if (areaCode.isNotBlank()) copy(areaCodeErrorRes = null) else this }
    }

    fun onCloseError() {
        onState { it.hideErrorState() }
    }

    private fun initUISate() {
        onState { it.copy(shouldEnableZipcodeInput = zipcode.isEmpty()) }
    }

    private fun areInputsPopulated(): Boolean = state.value.run {
        val (updateErrorStates, areInputsPopulated) = addressFormValidation()
        onState { updateErrorStates }
        areInputsPopulated
    }
}
