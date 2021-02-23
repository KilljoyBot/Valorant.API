package com.github.blad3mak3r

import com.github.blad3mak3r.api.Region

internal fun getBaseEndpoint(region: Region): String {
    return "https://${region.name.toLowerCase()}.api.riotgames.com/val/"
}