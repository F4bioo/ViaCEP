package com.fappslab.viacep.arch.args

import android.os.Bundle
import android.os.Parcelable
import com.fappslab.viacep.arch.extension.isNull
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

const val KEY_ARG = "arch:key_args"

internal class ArgsProperty<H, P : Parcelable>(
    private val bundleResolver: (H) -> Bundle?
) : ReadOnlyProperty<H, P> {

    private var value: P? = null

    override fun getValue(thisRef: H, property: KProperty<*>): P {
        if (value.isNull()) {
            value = bundleResolver(thisRef).extractArgs()
        }
        return checkNotNull(value)
    }
}

private fun <P : Parcelable> Bundle?.extractArgs(): P {
    requireNotNull(this) { "There are no arguments! Are you calling putArguments()?" }

    val argUntyped = get(KEY_ARG)
    requireNotNull(argUntyped) { "Arguments not found at key KEY_ARG! Are you calling putArguments()?" }

    @Suppress("UNCHECKED_CAST")
    return argUntyped as P
}
