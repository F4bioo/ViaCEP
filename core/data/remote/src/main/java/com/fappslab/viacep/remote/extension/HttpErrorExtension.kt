package com.fappslab.viacep.remote.extension

import com.fappslab.viacep.remote.exception.ArgumentException
import com.fappslab.viacep.remote.exception.FailureException
import com.fappslab.viacep.remote.exception.NetworkException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.Exception as GenericException

private const val UNEXPECTED_ERROR =
    "Oops! Something went wrong while trying to get remote data."

private fun HttpException.parseError(): FailureException =
    try {
        val convertErrorBody: FailureException? = Gson().fromJson(
            response()?.errorBody()?.string(),
            FailureException::class.java
        )
        convertErrorBody?.copy(throwable = this) ?: FailureException(
            message = UNEXPECTED_ERROR,
            throwable = null
        )
    } catch (e: GenericException) {
        FailureException(
            message = UNEXPECTED_ERROR,
            throwable = this
        )
    }

private fun Throwable.toThrowable(): Throwable =
    when (this) {
        is HttpException -> parseError()
        is JsonSyntaxException,
        is IllegalArgumentException -> ArgumentException()
        is UnknownHostException,
        is TimeoutException,
        is InterruptedIOException,
        is SocketTimeoutException,
        is SocketException,
        is ConnectException -> NetworkException()
        else -> throw UnknownError(UNEXPECTED_ERROR)
    }

fun <T> Flow<T>.orParseHttpError(): Flow<T> =
    catch { throwable ->
        throw throwable.toThrowable()
    }
