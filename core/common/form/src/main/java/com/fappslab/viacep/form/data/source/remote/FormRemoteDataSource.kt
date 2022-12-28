package com.fappslab.viacep.form.data.source.remote

import com.fappslab.viacep.remote.model.AddressResponse

internal interface FormRemoteDataSource {

    suspend fun getAddress(zipcode: String): Result<AddressResponse>
}
