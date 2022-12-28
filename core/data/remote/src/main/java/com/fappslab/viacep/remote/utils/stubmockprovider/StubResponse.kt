package com.fappslab.viacep.remote.utils.stubmockprovider

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.arch.jsonhandle.readFromJSONToModel
import com.fappslab.viacep.arch.jsonhandle.readFromJSONToString
import com.fappslab.viacep.remote.model.AddressResponse

private const val SUCCESS_RESPONSE = "success_response.json"
private const val FAILURE_RESPONSE = "failure_response.json"

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
object StubResponse {

    val addressResponse = readFromJSONToModel<AddressResponse>(SUCCESS_RESPONSE)

    val successBodyResponse = readFromJSONToString(SUCCESS_RESPONSE)

    val failureBodyResponse = readFromJSONToString(FAILURE_RESPONSE)
}
