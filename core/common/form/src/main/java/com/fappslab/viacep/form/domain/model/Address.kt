package com.fappslab.viacep.form.domain.model

data class Address(
    val zipcode: String,
    val street: String,
    val district: String,
    val city: String,
    val state: String,
    val areaCode: String
)
