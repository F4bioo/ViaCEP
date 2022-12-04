package com.fappslab.viacep.form.data.source

import com.fappslab.viacep.remote.model.AddressResponse

internal interface FormDataSource {

    suspend fun getAddress(zipcode: String): Result<AddressResponse>
}
