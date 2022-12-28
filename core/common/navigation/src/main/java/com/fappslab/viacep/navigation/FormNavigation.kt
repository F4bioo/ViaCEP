package com.fappslab.viacep.navigation

import androidx.fragment.app.Fragment

interface FormNavigation {

    fun newInstance(zipcode: String = "", onSaveBlock: () -> Unit = {}): Fragment
}
