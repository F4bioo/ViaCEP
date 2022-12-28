package com.fappslab.viacep.arch.extension

private const val ZIPCODE_LENGTH_ZERO = 0
private const val ZIPCODE_LENGTH_FIVE = 5

fun String.toZipcodeFormatter(): String = if (length == ZIPCODE_LENGTH_FIVE) {
    substring(ZIPCODE_LENGTH_ZERO, ZIPCODE_LENGTH_FIVE) + "-" + substring(ZIPCODE_LENGTH_FIVE)
} else this
