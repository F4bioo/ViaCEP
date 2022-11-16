package com.fappslab.viacep.remote.exception

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FailureException(
    @SerializedName("erro") val error: Boolean? = null,
    @SerializedName("message") override val message: String? = null,
    @Expose private val throwable: Throwable? = null
) : Throwable(message, throwable)
