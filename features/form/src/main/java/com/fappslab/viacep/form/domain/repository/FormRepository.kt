package com.fappslab.viacep.form.domain.repository

import com.fappslab.viacep.form.domain.model.Address

interface FormRepository {

    suspend fun getRemoteAddress(zipcode: String): Address
}
