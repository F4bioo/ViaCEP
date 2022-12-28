package com.fappslab.viacep.form.presentation.viewmodel

import com.fappslab.viacep.arch.viewmodel.Action

sealed class FormViewAction : Action {
    object ClearForm : FormViewAction()
}
