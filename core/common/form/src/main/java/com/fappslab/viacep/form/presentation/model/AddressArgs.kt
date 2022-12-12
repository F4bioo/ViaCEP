package com.fappslab.viacep.form.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressArgs(
    val zipcode: String = "",
    val street: String = "",
    val district: String = "",
    val city: String = "",
    val state: String = "",
    val areaCode: String = ""
) : Parcelable
