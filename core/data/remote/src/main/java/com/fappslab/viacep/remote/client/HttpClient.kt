package com.fappslab.viacep.remote.client

interface HttpClient {

    fun <T> create(clazz: Class<T>): T
}
