package com.fappslab.viacep.remote.stubmockprovider

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.arch.jsonhandle.readFromJSONToModel
import com.fappslab.viacep.remote.model.AddressResponse

private const val SUCCESS_RESPONSE = "success_response.json"
private const val FAILURE_RESPONSE = "failure_response.json"

object StubResponse {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val addressResponse = readFromJSONToModel<AddressResponse>(SUCCESS_RESPONSE)
}
