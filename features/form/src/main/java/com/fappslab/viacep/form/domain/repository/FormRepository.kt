package com.fappslab.viacep.form.domain.repository

import com.fappslab.viacep.form.domain.model.Address

interface FormRepository {

    suspend fun getRemoteAddress(zipcode: String): Address

    suspend fun getLocalAddress(zipcode: String): Address

    suspend fun setLocalAddress(address: Address)

    suspend fun getLocalAddresses(): List<Address>

    suspend fun deleteLocalAddress(zipcode: String)
}
