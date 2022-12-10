package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.form.presentation.extension.toAddress
import com.fappslab.viacep.form.presentation.model.AddressArgs

internal const val EMPTY_FIELD_ERROR_MESSAGE = "The \"zip code\" field must be filled in."
internal const val EMPTY_RESULT_ERROR_MESSAGE = "Zip code not found!"

class GetRemoteAddressUseCase(
    private val repository: FormRepository
) {

    suspend operator fun invoke(zipcode: String): Address = if (zipcode.isBlank()) {
        throw IllegalArgumentException(EMPTY_FIELD_ERROR_MESSAGE)
    } else repository.getRemoteAddress(zipcode.onlyNumbers()).ifEmptyThrow(zipcode)

    private fun Address.ifEmptyThrow(zipcode: String): Address {
        val address = AddressArgs(zipcode = zipcode.onlyNumbers()).toAddress()
        return if (copy(zipcode = zipcode.onlyNumbers()) == address) {
            throw IllegalArgumentException(EMPTY_RESULT_ERROR_MESSAGE)
        } else this
    }

    private fun String.onlyNumbers(): String = replace("[^\\d]".toRegex(), replacement = "")
}
