package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository

internal const val ERROR_MESSAGE = "The \"zip code\" field must be filled in."

class GetRemoteAddressUseCase(
    private val repository: FormRepository
) {

    suspend operator fun invoke(zipcode: String): Address {
        return if (zipcode.isBlank()) {
            throw IllegalArgumentException(ERROR_MESSAGE)
        } else repository.getRemoteAddress(zipcode.sanitize())
    }

    private fun String.sanitize(): String =
        replace("[^\\d]".toRegex(), replacement = "")
}
