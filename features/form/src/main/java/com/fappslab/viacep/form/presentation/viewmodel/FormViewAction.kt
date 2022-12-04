package com.fappslab.viacep.form.presentation.viewmodel

import com.fappslab.viacep.arch.viewmodel.ViewAction

sealed class FormViewAction : ViewAction {
    object ClearForm : FormViewAction()
    object FinishView : FormViewAction()
}
