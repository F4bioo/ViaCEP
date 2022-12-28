package com.fappslab.viacep.local.extension

import com.fappslab.viacep.local.exception.CacheThrowable

private const val UNEXPECTED_ERROR =
    "Oops! Something went wrong while trying to get local data."

private fun Throwable.toThrowable(): Throwable =
    when (this) {
        is IllegalArgumentException,
        is NullPointerException -> CacheThrowable(message)
        else -> throw UnknownError(UNEXPECTED_ERROR)
    }

fun <T> Result<T>.orParseCacheError(): Result<T> =
    onFailure { throwable ->
        throw throwable.toThrowable()
    }
