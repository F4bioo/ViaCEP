package com.fappslab.viacep.arch.args

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty

fun <F : Fragment> F.withArgs(args: Bundle.() -> Unit): F = apply {
    arguments = Bundle().apply(args)
}

fun <P : Parcelable> Fragment.viewArgs(): ReadOnlyProperty<Fragment, P> =
    ArgsProperty { fragment -> fragment.arguments }

fun Bundle.putArguments(args: Parcelable): Unit = putParcelable(KEY_ARG, args)
