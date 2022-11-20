package com.fappslab.viacep.remote.model

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("cep")
    val zipcode: String?,
    @SerializedName("logradouro")
    val street: String?,
    @SerializedName("bairro")
    val district: String?,
    @SerializedName("localidade")
    val city: String?,
    @SerializedName("uf")
    val state: String?,
    @SerializedName("ddd")
    val areaCode: String?
)
