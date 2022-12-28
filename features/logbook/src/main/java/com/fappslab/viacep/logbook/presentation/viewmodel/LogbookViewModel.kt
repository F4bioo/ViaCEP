package com.fappslab.viacep.logbook.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.viacep.arch.resultbuilder.ResultBuilder.Companion.runAsyncSafely
import com.fappslab.viacep.arch.resultbuilder.launchIn
import com.fappslab.viacep.arch.resultbuilder.onCompletion
import com.fappslab.viacep.arch.resultbuilder.onStart
import com.fappslab.viacep.arch.resultbuilder.onSuccess
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.form.domain.usecase.DeleteLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressesUseCase

internal class LogbookViewModel(
    private val getLocalAddressesUseCase: GetLocalAddressesUseCase,
    private val deleteLocalAddressUseCase: DeleteLocalAddressUseCase,
) : ViewModel<LogbookViewState, LogbookViewAction>(LogbookViewState()) {

    fun onGetLocalAddresses() {
        runAsyncSafely {
            getLocalAddressesUseCase()
        }.onStart {
            onState { it.copy(shouldShowLoading = true) }
        }.onCompletion {
            onState { it.copy(shouldShowLoading = false) }
        }.onSuccess { addresses ->
            onState { it.copy(addresses = addresses) }
        }.launchIn(viewModelScope)
    }

    fun onDeleteLocalAddress(zipcode: String) {
        runAsyncSafely {
            deleteLocalAddressUseCase(zipcode)
        }.onStart {
            onState { it.copy(shouldShowLoading = true) }
        }.onCompletion {
            onState { it.copy(shouldShowLoading = false) }
        }.onSuccess {
            onGetLocalAddresses()
        }.launchIn(viewModelScope)
    }

    fun onCardItem(zipcode: String) {
        onState { it.copy(shouldShowEditorBottomSheet = true, zipcode = zipcode) }
    }

    fun onCloseEditorBottomSheet() {
        onState { it.copy(shouldShowEditorBottomSheet = false) }
        onGetLocalAddresses()
    }
}
