package com.fappslab.viacep.arch.resultbuilder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ResultBuilder<T>(private val functionBlock: suspend () -> T) {
    private var startAction: (() -> Unit)? = null
    private var completeAction: (() -> Unit)? = null
    private var catchAction: ((Throwable) -> Unit)? = null
    private var collectResultAction: ((T) -> Unit)? = null

    fun start(startBlock: () -> Unit): ResultBuilder<T> {
        startAction = startBlock
        return this
    }

    fun complete(completeBlock: () -> Unit): ResultBuilder<T> {
        completeAction = completeBlock
        return this
    }

    fun catch(catchBlock: (Throwable) -> Unit): ResultBuilder<T> {
        catchAction = catchBlock
        return this
    }

    fun result(resultBlock: (T) -> Unit): ResultBuilder<T> {
        collectResultAction = resultBlock
        return this
    }

    suspend fun build() {
        apply {
            startAction?.invoke()
        }.runCatching {
            functionBlock.invoke()
        }.apply {
            completeAction?.invoke()
        }.fold(
            onFailure = { catchAction?.invoke(it) },
            onSuccess = { collectResultAction?.invoke(it) }
        )
    }

    fun buildIn(scope: CoroutineScope) {
        scope.launch {
            apply {
                startAction?.invoke()
            }.runCatching {
                functionBlock.invoke()
            }.apply {
                completeAction?.invoke()
            }.fold(
                onFailure = { catchAction?.invoke(it) },
                onSuccess = { collectResultAction?.invoke(it) }
            )
        }
    }
}

fun <T> runResultBuilder(functionBlock: suspend () -> T): ResultBuilder<T> {
    return ResultBuilder(functionBlock)
}
