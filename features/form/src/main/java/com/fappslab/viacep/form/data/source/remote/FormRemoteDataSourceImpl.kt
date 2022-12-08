package com.fappslab.viacep.form.data.source.remote

import com.fappslab.viacep.arch.extension.runFetch
import com.fappslab.viacep.form.data.service.FormService
import com.fappslab.viacep.remote.extension.orParseHttpError
import com.fappslab.viacep.remote.model.AddressResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class FormRemoteDataSourceImpl(
    private val service: FormService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FormRemoteDataSource {

    override suspend fun getAddress(zipcode: String): Result<AddressResponse> =
        dispatcher.runFetch {
            service.getAddress(zipcode)
        }.orParseHttpError()
}
