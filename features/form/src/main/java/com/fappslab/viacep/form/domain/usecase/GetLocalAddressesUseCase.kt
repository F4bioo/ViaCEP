package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository

class GetLocalAddressesUseCase(
    private val repository: FormRepository
) {

    suspend operator fun invoke(): List<Address> =
        repository.getLocalAddresses()
}
