package com.fappslab.viacep.arch.extension

suspend fun <T> Any?.runSafely(resultBlock: suspend () -> Result<T>): Result<T> =
    runCatching { resultBlock().getOrThrow() }


fun <T> Result<T>.onComplete(completeBlock: () -> Unit): Result<T> {
    completeBlock()
    return this
}
