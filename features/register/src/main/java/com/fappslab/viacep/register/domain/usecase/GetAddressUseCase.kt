package com.fappslab.viacep.register.domain.usecase

import com.fappslab.viacep.register.domain.model.Address
import com.fappslab.viacep.register.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

internal class GetAddressUseCase(
    private val repository: RegisterRepository
) {

    operator fun invoke(zipcode: String): Flow<Address> =
        repository.getAddress(zipcode.sanitize())

    private fun String.sanitize(): String =
        replace("[^\\d]".toRegex(), replacement = "")
}
