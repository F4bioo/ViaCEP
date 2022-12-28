package com.fappslab.viacep.form.data.moddel.extension

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.local.model.AddressEntity
import com.fappslab.viacep.remote.model.AddressResponse

fun AddressResponse.toAddress() = Address(
    zipcode = zipcode.orEmpty(),
    street = street.orEmpty(),
    district = district.orEmpty(),
    city = city.orEmpty(),
    state = state.orEmpty(),
    areaCode = areaCode.orEmpty()
)

fun AddressEntity.toAddress() = Address(
    zipcode = zipcode,
    street = street.orEmpty(),
    district = district.orEmpty(),
    city = city.orEmpty(),
    state = state.orEmpty(),
    areaCode = areaCode.orEmpty()
)

fun Address.toAddressEntity() = AddressEntity(
    zipcode = zipcode,
    street = street,
    district = district,
    city = city,
    state = state,
    areaCode = areaCode
)

fun List<AddressEntity>.toAddresses() = map {
    it.toAddress()
}
