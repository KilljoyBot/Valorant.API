package com.github.KilljoyBot.api.dto.ranked

import org.json.JSONObject

@Suppress("MemberVisibilityCanBePrivate")
data class Player(
    val puuid: String,
    val gameName: String,
    val tagLine: String,
    val leaderboardRank: Long,
    val rankedRating: Long,
    val numberOfWins: Long
) {

    constructor(json: JSONObject) : this(
        json.getString("puuid"),
        json.getString("gameName"),
        json.getString("tagLine"),
        json.getLong("leaderboardRank"),
        json.getLong("rankedRating"),
        json.getLong("numberOfWins")
    )

    val fullName = "${gameName}#${tagLine}"

    override fun toString(): String {
        return "Player(puuid='$puuid', gameName='$gameName', tagLine='$tagLine', leaderboardRank=$leaderboardRank, rankedRating=$rankedRating, numberOfWins=$numberOfWins, fullName='$fullName')"
    }


}
