package com.fappslab.viacep.form.data.source.local

import com.fappslab.viacep.arch.extension.runFetch
import com.fappslab.viacep.local.database.FormDatabase
import com.fappslab.viacep.local.extension.orParseCacheError
import com.fappslab.viacep.local.model.AddressEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class FormLocalDataSourceImpl(
    private val database: FormDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FormLocalDataSource {

    override suspend fun getAddresses(): Result<List<AddressEntity>?> =
        dispatcher.runFetch {
            database.formDao().getAddresses()
        }.orParseCacheError()

    override suspend fun setAddress(address: AddressEntity): Result<Unit> =
        dispatcher.runFetch {
            database.formDao().setAddress(address)
        }.orParseCacheError()

    override suspend fun deleteAddress(zipcode: String): Result<Unit> =
        dispatcher.runFetch {
            database.formDao().deleteAddress(zipcode)
        }.orParseCacheError()
}
