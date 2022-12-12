package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.repository.FormRepository

class DeleteLocalAddressUseCase(
    private val repository: FormRepository
) {

    suspend operator fun invoke(zipcode: String): Unit =
        repository.deleteLocalAddress(zipcode)
}
