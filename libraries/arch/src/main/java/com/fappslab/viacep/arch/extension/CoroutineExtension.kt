package com.fappslab.viacep.arch.extension

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> CoroutineDispatcher.runFetch(
    fetchBlock: suspend () -> T
): Result<T> = withContext(context = this) {
    runCatching { fetchBlock() }
}
