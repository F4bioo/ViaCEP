package com.fappslab.viacep.form.data.repository

import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.data.moddel.extension.toAddressEntity
import com.fappslab.viacep.form.data.moddel.extension.toAddresses
import com.fappslab.viacep.form.data.source.local.FormLocalDataSource
import com.fappslab.viacep.form.data.source.remote.FormRemoteDataSource
import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository

internal class FormRepositoryImpl(
    private val remoteDataSource: FormRemoteDataSource,
    private val localDataSource: FormLocalDataSource
) : FormRepository {

    override suspend fun getRemoteAddress(zipcode: String): Address =
        remoteDataSource.getAddress(zipcode).mapCatching { it.toAddress() }.getOrThrow()

    override suspend fun getLocalAddresses(): List<Address> =
        localDataSource.getAddresses().mapCatching { it.toAddresses() }.getOrThrow()

    override suspend fun setLocalAddress(address: Address): Unit =
        localDataSource.setAddress(address.toAddressEntity()).getOrThrow()

    override suspend fun deleteLocalAddress(zipcode: String): Unit =
        localDataSource.deleteAddress(zipcode).getOrThrow()
}
