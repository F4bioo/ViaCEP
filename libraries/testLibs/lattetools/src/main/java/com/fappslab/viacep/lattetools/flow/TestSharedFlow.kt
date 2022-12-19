package com.fappslab.viacep.lattetools.flow

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PRIVATE)
@ExperimentalCoroutinesApi
class TestSharedFlow<T> : MutableSharedFlow<T> {

    private val _subscriptionCount = MutableStateFlow(0)
    override val subscriptionCount: StateFlow<Int>
        get() = _subscriptionCount

    private val _values = mutableListOf<T>()
    override val replayCache: List<T>
        get() = _values

    private val _emitter = MutableSharedFlow<T>()
    override suspend fun emit(value: T) {
        _values.add(value)
        _emitter.emit(value)
    }

    override fun resetReplayCache() {
        _values.clear()
    }

    override fun tryEmit(value: T): Boolean {
        return _emitter.tryEmit(value)
    }

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        _subscriptionCount.value++
        try {
            _emitter.collect(collector)
        } finally {
            _subscriptionCount.value--
        }
    }
}
