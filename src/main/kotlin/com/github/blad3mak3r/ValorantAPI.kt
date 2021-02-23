@file:Suppress("unused")

package com.github.blad3mak3r

import com.github.blad3mak3r.api.Leaderboard
import com.github.blad3mak3r.api.Player
import com.github.blad3mak3r.api.Region
import com.github.blad3mak3r.internal.RiotTokenInterceptor
import com.github.blad3mak3r.internal.isValidToken
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.concurrent.CompletableFuture

class ValorantAPI(private val token: String) {

    init {
        if (!isValidToken(token)) throw IllegalArgumentException("$token is not a valid Riot token, must start with RGAPI-")
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(RiotTokenInterceptor(this.token))
        .followRedirects(false)
        .build()

    fun getLeaderboards(region: Region, actId: String = "97b6e739-44cc-ffa7-49ad-398ba502ceb0", startIndex: Int = 0, size: Int = 200): CompletableFuture<Leaderboard> {
        check(startIndex >= 0) { "startIndex cannot be lower than 0" }
        check(size in 1..200) { "size must be higher than 0 and lower or equal to 0" }

        val future = CompletableFuture<Leaderboard>()

        val url = getBaseEndpoint(region).plus("ranked/v1/leaderboards/by-act/${actId}?startIndex=$startIndex&size=$size")
        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                future.completeExceptionally(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { res ->
                    if (!res.isSuccessful) {
                        future.completeExceptionally(IOException("Received ${res.code} status code on request: $url"))
                        return
                    }

                    if (res.body == null) {
                        future.completeExceptionally(IllegalStateException("Received empty body on request: $url"))
                        return
                    }

                    val json = JSONObject(res.body!!.string())

                    val leaderboard = Leaderboard(
                        shard = json.getString("shard"),
                        actId = json.getString("actId"),
                        totalPlayers = json.getLong("totalPlayers"),
                        players = json.getJSONArray("players").map { (it as JSONObject); Player(it) }
                    )

                    future.complete(leaderboard)
                }
            }

        })

        return future
    }

    fun shutdown() {
        httpClient.connectionPool.evictAll()
        httpClient.dispatcher.executorService.shutdown()
    }

}