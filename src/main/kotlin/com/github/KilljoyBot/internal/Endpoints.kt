package com.github.KilljoyBot.internal

import com.github.KilljoyBot.api.enums.Locale
import com.github.KilljoyBot.api.enums.Region

object Endpoints {

    internal fun getBaseEndpoint(region: Region): String {
        return "https://${region.name.toLowerCase()}.api.riotgames.com/val"
    }

    internal fun getContentsEndpoint(region: Region, locale: Locale? = null): String {
        var base = getBaseEndpoint(region).plus("/content/v1/contents")

        if (locale != null) base += "?locale=${locale.code}"

        return base
    }

    internal fun getLeaderboardEndpoint(region: Region, actId: String, startIndex: Int, size: Int): String {
        return getBaseEndpoint(region).plus("/ranked/v1/leaderboards/by-act/${actId}?startIndex=$startIndex&size=$size")
    }

    internal fun getStatusEndpoint(region: Region): String {
        return getBaseEndpoint(region).plus("/status/v1/platform-data")
    }

}