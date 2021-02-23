package com.github.blad3mak3r.internal

internal val TOKEN_REGEX = "RGAPI-(.+?)".toRegex()

internal fun isValidToken(token: String) = TOKEN_REGEX matches token