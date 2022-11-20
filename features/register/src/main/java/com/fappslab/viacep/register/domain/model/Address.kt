package com.fappslab.viacep.register.domain.model

data class Address(
    val zipcode: String,
    val street: String,
    val district: String,
    val city: String,
    val state: String,
    val areaCode: String
)
