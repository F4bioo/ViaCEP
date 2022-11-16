package com.fappslab.viacep.remote.exception

import com.google.gson.annotations.Expose

private const val MESSAGE = "Please, check your connection or try again later."

class ArgumentException(
    @Expose override val message: String? = null,
) : Throwable(message ?: MESSAGE)
