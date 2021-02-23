package com.github.blad3mak3r.internal

import com.github.blad3mak3r.api.Region

object Endpoints {

    internal fun getBaseEndpoint(region: Region): String {
        return "https://${region.name.toLowerCase()}.api.riotgames.com/val"
    }

    internal fun getLeaderboardEndpoint(region: Region, actId: String, startIndex: Int, size: Int): String {
        return getBaseEndpoint(region).plus("/ranked/v1/leaderboards/by-act/${actId}?startIndex=$startIndex&size=$size")
    }

}