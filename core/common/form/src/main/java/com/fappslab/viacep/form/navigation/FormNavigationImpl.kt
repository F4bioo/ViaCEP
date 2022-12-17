package com.fappslab.viacep.form.navigation

import androidx.fragment.app.Fragment
import com.fappslab.viacep.form.presentation.FormFragment
import com.fappslab.viacep.navigation.FormNavigation

internal class FormNavigationImpl : FormNavigation {

    override fun newInstance(zipcode: String, onSaveBlock: () -> Unit): Fragment =
        FormFragment.newInstance(zipcode, onSaveBlock = onSaveBlock::invoke)
}
