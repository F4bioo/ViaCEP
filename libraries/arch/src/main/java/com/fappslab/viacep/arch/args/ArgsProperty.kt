package com.fappslab.viacep.arch.args

import android.os.Bundle
import android.os.Parcelable
import com.fappslab.viacep.arch.extension.isNull
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal const val KEY_ARGS = "arch:key_args"

internal class ArgsProperty<H, P : Parcelable>(
    private val bundleResolver: (H) -> Bundle?
) : ReadOnlyProperty<H, P> {

    private var value: P? = null

    override fun getValue(thisRef: H, property: KProperty<*>): P {
        if (value.isNull()) value = bundleResolver(thisRef).extractArgs()
        return checkNotNull(value)
    }
}

private fun <P : Parcelable> Bundle?.extractArgs(): P {
    requireNotNull(this) { "Have you invoked putArguments()?" }

    val argUntyped = get(KEY_ARGS)
    requireNotNull(argUntyped) { "Have you invoked putArguments()?" }

    @Suppress("UNCHECKED_CAST")
    return argUntyped as P
}
