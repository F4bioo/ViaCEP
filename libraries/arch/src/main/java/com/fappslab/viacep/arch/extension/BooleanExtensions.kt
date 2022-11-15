package com.fappslab.viacep.arch.extension

private const val TRUE = 1
private const val FALSE = 0

fun Boolean.toInt() = if (this) TRUE else FALSE

fun Boolean.stringfy(): String =
    if (this) TRUE.toString() else FALSE.toString()

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = !orTrue()

fun <T> T?.isNull() = this == null

fun <T> T?.isNotNull() = !isNull()

fun Boolean.inverse(): Boolean = not()
