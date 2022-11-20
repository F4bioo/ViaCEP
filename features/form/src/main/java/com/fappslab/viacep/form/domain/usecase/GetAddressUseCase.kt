package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository
import kotlinx.coroutines.flow.Flow

internal class GetAddressUseCase(
    private val repository: FormRepository
) {

    operator fun invoke(zipcode: String): Flow<Address> =
        repository.getAddress(zipcode.sanitize())

    private fun String.sanitize(): String =
        replace("[^\\d]".toRegex(), replacement = "")
}
