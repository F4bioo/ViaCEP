package com.fappslab.viacep.form.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.viacep.arch.extension.isNull
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.form.R
import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.domain.usecase.SetLocalAddressUseCase
import kotlinx.coroutines.launch

class FormViewModel(
    private val getRemoteAddressUseCase: GetRemoteAddressUseCase,
    private val setLocalAddressUseCase: SetLocalAddressUseCase
) : ViewModel<FormViewState, FormViewAction>(FormViewState()) {

    fun onRequestAddress(zipcode: String) {
        viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                getRemoteAddressUseCase(zipcode)
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = ::getAddressFailure,
                onSuccess = ::getAddressSuccess
            )
        }
    }

    private fun getAddressFailure(cause: Throwable) {
        onState { it.errorUpdate(shouldShowError = true, errorMessage = cause.message) }
    }

    private fun getAddressSuccess(address: Address) {
        onState {
            it.copy(
                zipcode = address.zipcode,
                street = address.street,
                district = address.district,
                city = address.city,
                state = address.state,
                areaCode = address.areaCode,
            )
        }
    }

    private fun saveAddress(address: Address) {
        viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                setLocalAddressUseCase(address)
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = ::setAddressFailure,
                onSuccess = { setAddressSuccess() }
            )
        }
    }

    private fun setAddressFailure(cause: Throwable) {
        onState { it.errorUpdate(shouldShowError = true, errorMessage = cause.message) }
    }

    private fun setAddressSuccess() {
        onAction { FormViewAction.ClearForm }
    }

    fun onTextChangedZipcode(zipcode: String) {
        onState { it.copy(zipcode = zipcode) }
    }

    fun onTextChangedStreet(street: String) {
        onState { it.copy(street = street) }
    }

    fun onTextChangedDistrict(district: String) {
        onState { it.copy(district = district) }
    }

    fun onTextChangedCity(city: String) {
        onState { it.copy(city = city) }
    }

    fun onTextChangedState(state: String) {
        onState { it.copy(state = state) }
    }

    fun onTextChangedAreaCode(areaCode: String) {
        onState { it.copy(areaCode = areaCode) }
    }

    fun onSaveLocalAddress() {
        if (areInputsPopulated()) {
            state.value.apply {
                Address(
                    zipcode = zipcode.orEmpty(),
                    street = street.orEmpty(),
                    district = district.orEmpty(),
                    city = city.orEmpty(),
                    state = state.orEmpty(),
                    areaCode = areaCode.orEmpty()
                ).also(::saveAddress)
            }
        }
    }

    fun onCloseError() {
        onState { it.copy(shouldShowError = false, errorMessage = null) }
    }

    fun onFinishView() {
        onAction { FormViewAction.FinishView }
    }

    private fun areInputsPopulated(): Boolean {
        val streetErrorRes = R.string.form_check_field_street_blank_error
            .takeIf { state.value.street.isNullOrEmpty() }
        val districtErrorRes = R.string.form_check_field_district_blank_error
            .takeIf { state.value.district.isNullOrEmpty() }
        val cityErrorRes = R.string.form_check_field_city_blank_error
            .takeIf { state.value.city.isNullOrEmpty() }
        val stateErrorRes = R.string.form_check_field_state_blank_error
            .takeIf { state.value.state.isNullOrEmpty() }
        val areaCodeErrorRes = R.string.form_check_field_area_code_blank_error
            .takeIf { state.value.areaCode.isNullOrEmpty() }

        onState {
            it.copy(
                streetErrorRes = streetErrorRes,
                districtErrorRes = districtErrorRes,
                cityErrorRes = cityErrorRes,
                stateErrorRes = stateErrorRes,
                areaCodeErrorRes = areaCodeErrorRes,
            )
        }

        return listOf(
            streetErrorRes,
            districtErrorRes,
            cityErrorRes,
            stateErrorRes,
            areaCodeErrorRes,
        ).all { it.isNull() }
    }
}
