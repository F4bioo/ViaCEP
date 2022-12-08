package com.fappslab.viacep.form.data.source.local

import com.fappslab.viacep.local.model.AddressEntity

internal interface FormLocalDataSource {

    suspend fun getAddresses(): Result<List<AddressEntity>?>

    suspend fun setAddress(address: AddressEntity): Result<Unit>

    suspend fun deleteAddress(zipcode: String): Result<Unit>
}
