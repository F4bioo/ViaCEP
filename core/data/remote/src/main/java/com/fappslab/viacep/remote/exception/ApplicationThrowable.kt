package com.fappslab.viacep.remote.exception

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal const val CONNECTION_DEFAULT_ERROR_MESSAGE =
    "Our system is currently unavailable. After verifying your connection, let's try again?"

internal const val UNEXPECTED_DEFAULT_ERROR_MESSAGE =
    "Oops! Something went wrong while trying to get remote data. Please try again."

internal const val API_DEFAULT_ERROR_MESSAGE =
    "Our system was unable to process your request. Please check the data sent and try again."

sealed class ApplicationThrowable(cause: Throwable?) : Throwable(cause) {

    class ConnectionThrowable(
        @Expose override val message: String? = CONNECTION_DEFAULT_ERROR_MESSAGE,
        throwable: Throwable? = null
    ) : ApplicationThrowable(throwable)

    class ServerThrowable(
        @Expose override val message: String? = UNEXPECTED_DEFAULT_ERROR_MESSAGE,
        throwable: Throwable? = null
    ) : ApplicationThrowable(throwable)

    data class ApiServiceThrowable(
        @SerializedName("erro") val error: Boolean? = null,
        @SerializedName("message") override val message: String? = API_DEFAULT_ERROR_MESSAGE,
        @Expose private val throwable: Throwable? = null
    ) : ApplicationThrowable(throwable)
}
