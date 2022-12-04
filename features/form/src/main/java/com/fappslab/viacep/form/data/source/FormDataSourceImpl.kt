package com.fappslab.viacep.form.data.source

import com.fappslab.viacep.arch.extension.runFetch
import com.fappslab.viacep.form.data.service.FormService
import com.fappslab.viacep.remote.extension.orParseHttpError
import com.fappslab.viacep.remote.model.AddressResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class FormDataSourceImpl(
    private val service: FormService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FormDataSource {

    override suspend fun getAddress(zipcode: String): Result<AddressResponse> =
        dispatcher.runFetch {
            service.getAddress(zipcode)
        }.orParseHttpError()
}
