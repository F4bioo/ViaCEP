package com.fappslab.viacep.logbook.presentation.viewmodel

import com.fappslab.viacep.arch.viewmodel.Action

internal sealed class LogbookViewAction : Action {
    data class Card(val zipcode: String) : LogbookViewAction()
}
