package com.fappslab.viacep.remote.extension

import com.fappslab.viacep.remote.exception.ApplicationThrowable
import com.fappslab.viacep.remote.exception.ApplicationThrowable.ApiServiceThrowable
import com.fappslab.viacep.remote.exception.ApplicationThrowable.ServerThrowable
import com.google.gson.Gson
import retrofit2.HttpException
import kotlin.Exception as GenericException

private fun HttpException.parseError(): ApplicationThrowable =
    try {
        val convertErrorBody: ApiServiceThrowable? = Gson().fromJson(
            response()?.errorBody()?.string(),
            ApiServiceThrowable::class.java
        )
        convertErrorBody?.copy(throwable = this) ?: ServerThrowable()
    } catch (e: GenericException) {
        ServerThrowable(throwable = this)
    }

private fun Throwable.toThrowable(): ApplicationThrowable {
    return when (this) {
        is HttpException -> parseError()
        else -> ApplicationThrowable.ConnectionThrowable(throwable = this)
    }
}

fun <T> Result<T>.orParseHttpError(): Result<T> =
    onFailure { throwable ->
        throw throwable.toThrowable()
    }
