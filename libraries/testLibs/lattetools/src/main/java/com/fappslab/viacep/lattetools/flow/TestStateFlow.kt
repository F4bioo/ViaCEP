package com.fappslab.viacep.lattetools.flow

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PRIVATE)
@ExperimentalCoroutinesApi
class TestStateFlow<T>(initialState: T) : MutableStateFlow<T> {

    private val _subscriptionCount = MutableStateFlow(0)
    override val subscriptionCount: StateFlow<Int>
        get() = _subscriptionCount

    private val _value = MutableStateFlow(initialState)
    override var value: T
        get() = _value.value
        set(value) {
            _value.value = value
        }

    private val _replayCache = mutableListOf<T>()
    override val replayCache: List<T>
        get() = _replayCache

    override suspend fun emit(value: T) {
        _value.value = value
    }

    override fun resetReplayCache() {
        _replayCache.clear()
    }

    override fun tryEmit(value: T): Boolean {
        _value.value = value
        return true
    }

    override fun compareAndSet(expect: T, update: T): Boolean {
        if (_value.value == expect) {
            _value.value = update
            return true
        }
        return false
    }

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        while (true) {
            collector.emit(_value.value)
            _subscriptionCount.value++
            delay(1000)
        }
    }
}
