package com.fappslab.viacep.form.data.source.local

import com.fappslab.viacep.local.model.AddressEntity

internal interface FormLocalDataSource {

    suspend fun getAddress(zipcode: String): Result<AddressEntity>

    suspend fun setAddress(address: AddressEntity): Result<Unit>

    suspend fun getAddresses(): Result<List<AddressEntity>>

    suspend fun deleteAddress(zipcode: String): Result<Unit>
}
