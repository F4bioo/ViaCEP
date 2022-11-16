package com.fappslab.viacep.remote.di

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.remote.BuildConfig
import com.fappslab.viacep.remote.BuildConfig.BASE_URL
import com.fappslab.viacep.remote.client.HttpClient
import com.fappslab.viacep.remote.client.HttpClientImpl
import com.fappslab.viacep.remote.client.RetrofitClient
import com.fappslab.viacep.remote.client.interceptor.DefInterceptor
import com.fappslab.viacep.remote.client.interceptor.HeaderInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

object DataRemoteModule : KoinLoad() {

    override val dataModule = module {
        single<HttpClient> {
            HttpClientImpl(
                retrofit = getRetrofitClient().create()
            )
        }
    }

    private fun getRetrofitClient(): RetrofitClient {
        return RetrofitClient(
            baseUrl = BASE_URL,
            interceptors = listOf(
                DefInterceptor(),
                HeaderInterceptor(),
                httpLoggingInterceptor()
            )
        )
    }
}

private fun httpLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
    }
