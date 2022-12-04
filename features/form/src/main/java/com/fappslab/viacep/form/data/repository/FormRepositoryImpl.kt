package com.fappslab.viacep.form.data.repository

import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.data.source.FormDataSource
import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository

internal class FormRepositoryImpl(
    private val dataSource: FormDataSource
) : FormRepository {

    override suspend fun getRemoteAddress(zipcode: String): Address =
        dataSource.getAddress(zipcode).map { it.toAddress() }.getOrThrow()
}
