package com.github.killjoybot.internal

import okhttp3.Interceptor
import okhttp3.Response

class RiotTokenInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Riot-Token", token)
            .build()

        return chain.proceed(request)
    }
}