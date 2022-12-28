package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository


class GetLocalAddressUseCase(
    private val repository: FormRepository
) {

    suspend operator fun invoke(zipcode: String): Address =
        repository.getLocalAddress(zipcode)
}
