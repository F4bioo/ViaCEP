package com.fappslab.viacep.logbook.presentation.viewmodel

import com.fappslab.viacep.arch.viewmodel.ViewState
import com.fappslab.viacep.form.domain.model.Address

internal data class LogbookViewState(
    val shouldShowLoading: Boolean = false,
    val shouldShowEditorBottomSheet: Boolean = false,
    val addresses: List<Address> = listOf(),
    val zipcode: String = "",
) : ViewState
