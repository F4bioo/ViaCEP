package com.fappslab.viacep.form.domain.repository

import com.fappslab.viacep.form.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface FormRepository {

    fun getAddress(zipcode: String): Flow<Address>
}
