@file:Suppress("unused")

package com.github.blad3mak3r

import com.github.blad3mak3r.api.dto.Contents
import com.github.blad3mak3r.api.dto.Leaderboard
import com.github.blad3mak3r.api.dto.Player
import com.github.blad3mak3r.api.enums.Locale
import com.github.blad3mak3r.api.enums.Region
import com.github.blad3mak3r.internal.Endpoints
import com.github.blad3mak3r.internal.RiotTokenInterceptor
import com.github.blad3mak3r.internal.isValidToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CompletableFuture

class ValorantAPI(private val token: String) {

    init {
        if (!isValidToken(token)) throw IllegalArgumentException("$token is not a valid Riot token, must start with RGAPI-")
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(RiotTokenInterceptor(this.token))
        .followRedirects(false)
        .build()

    fun getContents(region: Region, locale: Locale? = null): CompletableFuture<Contents> {
        val future = CompletableFuture<Contents>()

        val url = Endpoints.getContentsEndpoint(region, locale)
        httpClient.newCall(url).enqueue(object : Callback {
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
                    val contents = Contents(json)

                    future.complete(contents)
                }
            }

        })

        return future
    }

    fun getLeaderboards(region: Region, actId: String = "97b6e739-44cc-ffa7-49ad-398ba502ceb0", startIndex: Int = 0, size: Int = 200): CompletableFuture<Leaderboard> {
        check(startIndex >= 0) { "startIndex cannot be lower than 0" }
        check(size in 1..200) { "size must be higher than 0 and lower or equal to 0" }

        val future = CompletableFuture<Leaderboard>()

        val url = Endpoints.getLeaderboardEndpoint(region, actId, startIndex, size)
        httpClient.newCall(url).enqueue(object : Callback {
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