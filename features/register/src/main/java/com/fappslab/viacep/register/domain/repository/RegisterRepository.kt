package com.fappslab.viacep.register.domain.repository

import com.fappslab.viacep.register.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    fun getAddress(zipcode: String): Flow<Address>
}
