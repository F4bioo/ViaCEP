package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository

internal const val EMPTY_FIELD_ERROR_MESSAGE = "The \"zip code\" field must be filled in."
internal const val EMPTY_RESULT_ERROR_MESSAGE = "Zip code not found!"

class GetRemoteAddressUseCase(
    private val repository: FormRepository,
) {

    suspend operator fun invoke(zipcode: String): Address {
        require(zipcode.isNotBlank()) { EMPTY_FIELD_ERROR_MESSAGE }
        return repository.getRemoteAddress(zipcode.onlyNumbers()).ifEmptyThrow()
    }

    private fun Address.ifEmptyThrow(): Address {
        require(listOf(street, district, city, state, areaCode)
            .all { it.isNotEmpty() }) { EMPTY_RESULT_ERROR_MESSAGE }

        return this
    }

    private fun String.onlyNumbers(): String =
        replace("[^\\d]".toRegex(), replacement = "")
}
