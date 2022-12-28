package com.fappslab.viacep.arch.args

import android.os.Bundle
import android.os.Parcelable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal const val KEY_ARGS = "arch:key_args"

internal class ArgsProperty<H, P : Parcelable>(
    private val bundleResolver: (H) -> Bundle,
) : ReadOnlyProperty<H, P> {

    private var value: P? = null

    override fun getValue(thisRef: H, property: KProperty<*>): P {
        return value ?: bundleResolver(thisRef).let { bundle ->
            val argUntyped = bundle.get(KEY_ARGS)
            requireNotNull(argUntyped) { "Have you invoked putArguments()?" }
            @Suppress("UNCHECKED_CAST")
            argUntyped as P
        }
    }
}
