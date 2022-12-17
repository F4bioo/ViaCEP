package com.fappslab.viacep.logbook.presentation.viewmodel

import com.fappslab.viacep.arch.viewmodel.ViewAction

internal sealed class LogbookViewAction : ViewAction {
    data class Card(val zipcode: String) : LogbookViewAction()
}
