package com.fappslab.viacep.arch.resultbuilder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ResultBuilder<T> private constructor(
    private val functionBlock: suspend () -> T,
) {
    var onStart: (() -> Unit)? = null
    var onCompletion: (() -> Unit)? = null
    var onFailure: ((Throwable) -> Unit)? = null
    var onSuccess: ((T) -> Unit)? = null

    suspend fun build() {
        apply {
            onStart?.invoke()
        }.runCatching {
            functionBlock.invoke()
        }.apply {
            onCompletion?.invoke()
        }.fold(
            onFailure = { onFailure?.invoke(it) },
            onSuccess = { onSuccess?.invoke(it) }
        )
    }

    companion object {
        fun <T> runAsyncSafely(functionBlock: suspend () -> T): ResultBuilder<T> {
            return ResultBuilder(functionBlock)
        }
    }
}

fun <T> ResultBuilder<T>.onStart(action: () -> Unit): ResultBuilder<T> {
    onStart = action
    return this
}

fun <T> ResultBuilder<T>.onCompletion(action: () -> Unit): ResultBuilder<T> {
    onCompletion = action
    return this
}

fun <T> ResultBuilder<T>.onFailure(action: (Throwable) -> Unit): ResultBuilder<T> {
    onFailure = action
    return this
}

fun <T> ResultBuilder<T>.onSuccess(action: (T) -> Unit): ResultBuilder<T> {
    onSuccess = action
    return this
}

fun <T> ResultBuilder<T>.launchIn(scope: CoroutineScope) {
    scope.launch { build() }
}
