package com.fappslab.viacep.logbook.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.viacep.arch.resultbuilder.runResultBuilder
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.form.domain.usecase.DeleteLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressesUseCase

internal class LogbookViewModel(
    private val getLocalAddressesUseCase: GetLocalAddressesUseCase,
    private val deleteLocalAddressUseCase: DeleteLocalAddressUseCase,
) : ViewModel<LogbookViewState, LogbookViewAction>(LogbookViewState()) {

    fun onGetLocalAddresses() {
        runResultBuilder {
            getLocalAddressesUseCase()
        }.start {
            onState { it.copy(shouldShowLoading = true) }
        }.complete {
            onState { it.copy(shouldShowLoading = false) }
        }.result { addresses ->
            onState { it.copy(addresses = addresses) }
        }.buildIn(viewModelScope)
    }

    fun onDeleteLocalAddress(zipcode: String) {
        runResultBuilder {
            deleteLocalAddressUseCase(zipcode)
        }.start {
            onState { it.copy(shouldShowLoading = true) }
        }.complete {
            onState { it.copy(shouldShowLoading = false) }
        }.result {
            onGetLocalAddresses()
        }.buildIn(viewModelScope)
    }

    fun onCardItem(zipcode: String) {
        onState { it.copy(shouldShowEditorBottomSheet = true, zipcode = zipcode) }
    }

    fun onCloseEditorBottomSheet() {
        onState { it.copy(shouldShowEditorBottomSheet = false) }
        onGetLocalAddresses()
    }
}
