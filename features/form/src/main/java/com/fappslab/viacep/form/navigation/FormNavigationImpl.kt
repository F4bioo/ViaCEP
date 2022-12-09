package com.fappslab.viacep.form.navigation

import androidx.fragment.app.Fragment
import com.fappslab.viacep.form.presentation.FormFragment
import com.fappslab.viacep.navigation.FormNavigation
import com.fappslab.viacep.navigation.FormType

internal class FormNavigationImpl : FormNavigation {

    override fun newInstance(formType: FormType): Fragment =
        FormFragment.newInstance(formType)
}
