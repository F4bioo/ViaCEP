package com.fappslab.viacep.form.domain.usecase

import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository

class SetLocalAddressUseCase(
    private val repository: FormRepository
) {

    suspend operator fun invoke(address: Address): Unit =
        repository.setLocalAddress(address)
}
