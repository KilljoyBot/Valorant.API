package com.github.blad3mak3r

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

internal inline fun OkHttpClient.newCall(builder: Request.Builder.() -> Unit): Call {
    val request = Request.Builder().apply(builder).build()
    return newCall(request)
}

internal fun OkHttpClient.newCall(url: String): Call {
    val request = Request.Builder().url(url).build()
    return newCall(request)
}