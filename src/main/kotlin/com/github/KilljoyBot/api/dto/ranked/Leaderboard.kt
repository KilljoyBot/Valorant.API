package com.github.KilljoyBot.api.dto.ranked

data class Leaderboard(
    val shard: String,
    val actId: String,
    val totalPlayers: Long,
    val players: List<Player>
)
