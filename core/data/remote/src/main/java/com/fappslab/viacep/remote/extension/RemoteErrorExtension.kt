package com.fappslab.viacep.remote.extension

import com.fappslab.viacep.remote.exception.RemoteThrowable
import com.fappslab.viacep.remote.exception.RemoteThrowable.ApiServiceThrowable
import com.fappslab.viacep.remote.exception.RemoteThrowable.ServerThrowable
import com.google.gson.Gson
import retrofit2.HttpException
import kotlin.Exception as GenericException

private fun HttpException.parseError(): RemoteThrowable =
    try {
        val convertErrorBody: ApiServiceThrowable? = Gson().fromJson(
            response()?.errorBody()?.string(),
            ApiServiceThrowable::class.java
        )
        convertErrorBody?.copy(throwable = this) ?: ServerThrowable()
    } catch (e: GenericException) {
        ServerThrowable(throwable = this)
    }

private fun Throwable.toThrowable(): RemoteThrowable {
    return when (this) {
        is HttpException -> parseError()
        else -> RemoteThrowable.ConnectionThrowable(throwable = this)
    }
}

fun <T> Result<T>.orParseHttpError(): Result<T> =
    onFailure { throwable ->
        throw throwable.toThrowable()
    }
