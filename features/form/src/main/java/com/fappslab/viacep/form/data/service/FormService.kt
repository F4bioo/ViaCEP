package com.fappslab.viacep.form.data.service

import com.fappslab.viacep.remote.model.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface FormService {

    @GET("ws/{zipcode}")
    suspend fun getAddress(
        @Path("zipcode") zipcode: String
    ): AddressResponse
}
