package com.github.killjoybot.api.dto.contents

import org.json.JSONObject

data class Contents(
    val version: String,
    val characters: List<Item>,
    val maps: List<Item>,
    val chromas: List<Item>,
    val skins: List<Item>,
    val skinLevels: List<Item>,
    val equips: List<Item>,
    val gameModes: List<Item>,
    val sprays: List<Item>,
    val sprayLevel: List<Item>,
    val charms: List<Item>,
    val charmLevels: List<Item>,
    val playerCards: List<Item>,
    val playerTitles: List<Item>,
    val acts: List<Act>
) {

    constructor(json: JSONObject) : this(
        json.getString("version"),
        json.getJSONArray("characters").map { (it as JSONObject); Item(it) },
        json.getJSONArray("maps").map { (it as JSONObject); Item(it) },
        json.getJSONArray("chromas").map { (it as JSONObject); Item(it) },
        json.getJSONArray("skins").map { (it as JSONObject); Item(it) },
        json.getJSONArray("skinLevels").map { (it as JSONObject); Item(it) },
        json.getJSONArray("equips").map { (it as JSONObject); Item(it) },
        json.getJSONArray("gameModes").map { (it as JSONObject); Item(it) },
        json.getJSONArray("sprays").map { (it as JSONObject); Item(it) },
        json.getJSONArray("sprayLevels").map { (it as JSONObject); Item(it) },
        json.getJSONArray("charms").map { (it as JSONObject); Item(it) },
        json.getJSONArray("charmLevels").map { (it as JSONObject); Item(it) },
        json.getJSONArray("playerCards").map { (it as JSONObject); Item(it) },
        json.getJSONArray("playerTitles").map { (it as JSONObject); Item(it) },
        json.getJSONArray("acts").map { (it as JSONObject); Act(it) },
    )

    data class Item(
        val name: String,
        val localizedNames: LocalizedNames? = null,
        val id: String,
        val assetName: String,
        val assetPath: String? = null
    ) {
        constructor(json: JSONObject) : this(
            json.getString("name"),
            if (json.has("localizedNames")) LocalizedNames(json.getJSONObject("localizedNames")) else null,
            json.getString("id"),
            json.getString("assetName"),
            if (json.has("assetPath")) json.getString("assetPath") else null
        )
    }
}
