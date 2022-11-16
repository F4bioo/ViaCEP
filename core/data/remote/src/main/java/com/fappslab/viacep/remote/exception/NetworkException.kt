package com.fappslab.viacep.remote.exception

import com.google.gson.annotations.Expose

private const val MESSAGE =
    "Our system is currently unavailable. After verifying your connection, let\'s try again?"

data class NetworkException(
    @Expose override val message: String? = null,
) : Throwable(message ?: MESSAGE)
