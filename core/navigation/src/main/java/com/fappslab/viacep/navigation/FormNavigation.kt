package com.fappslab.viacep.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize

interface FormNavigation {

    fun newInstance(formType: FormType): Fragment
}

@Parcelize
enum class FormType : Parcelable {
    Add, Edit
}
