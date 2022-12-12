package com.fappslab.viacep.form.presentation.extension

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.presentation.model.AddressArgs

internal fun Address.toAddressArgs() =
    AddressArgs(
        zipcode = zipcode,
        street = street,
        district = district,
        city = city,
        state = state,
        areaCode = areaCode
    )

internal fun AddressArgs.toAddress() =
    Address(
        zipcode = zipcode,
        street = street,
        district = district,
        city = city,
        state = state,
        areaCode = areaCode
    )
