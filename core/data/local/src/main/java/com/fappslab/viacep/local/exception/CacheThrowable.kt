package com.fappslab.viacep.local.exception

import com.google.gson.annotations.Expose

private const val MESSAGE = "Please, try again later."

class CacheThrowable(
    @Expose override val message: String? = null,
) : Throwable(message ?: MESSAGE)
