package com.fappslab.viacep.local.client

interface Database<T> {

    fun create(): T
}
