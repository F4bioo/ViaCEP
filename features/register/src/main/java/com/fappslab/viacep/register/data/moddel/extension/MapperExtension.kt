package com.fappslab.viacep.register.data.moddel.extension

import com.fappslab.viacep.register.domain.model.Address
import com.fappslab.viacep.remote.model.AddressResponse

fun AddressResponse.toAddress() = Address(
    zipcode = zipcode.orEmpty(),
    street = street.orEmpty(),
    district = district.orEmpty(),
    city = city.orEmpty(),
    state = state.orEmpty(),
    areaCode = areaCode.orEmpty()
)
