package com.fappslab.viacep.logbook.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.form.domain.usecase.DeleteLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressesUseCase
import kotlinx.coroutines.launch

internal class LogbookViewModel(
    private val getLocalAddressesUseCase: GetLocalAddressesUseCase,
    private val deleteLocalAddressUseCase: DeleteLocalAddressUseCase,
) : ViewModel<LogbookViewState, LogbookViewAction>(LogbookViewState()) {

    fun onGetLocalAddresses() {
        viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                getLocalAddressesUseCase()
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = {},
                onSuccess = { addresses -> onState { it.copy(addresses = addresses) } }
            )
        }
    }

    fun onDeleteLocalAddress(zipcode: String) {
        viewModelScope.launch {
            onState {
                it.copy(shouldShowLoading = true)
            }.runCatching {
                deleteLocalAddressUseCase(zipcode)
            }.apply {
                onState { it.copy(shouldShowLoading = false) }
            }.fold(
                onFailure = {},
                onSuccess = { onGetLocalAddresses() }
            )
        }
    }

    fun onCardItem(zipcode: String) {
        onState { it.copy(shouldShowEditorBottomSheet = true, zipcode = zipcode) }
    }

    fun onCloseEditorBottomSheet() {
        onState { it.copy(shouldShowEditorBottomSheet = false) }
        onGetLocalAddresses()
    }
}
