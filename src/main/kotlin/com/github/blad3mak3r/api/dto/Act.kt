package com.github.blad3mak3r.api.dto

import org.json.JSONObject

data class Act(
    val name: String,
    val localizedNames: LocalizedNames?,
    val id: String,
    val isActive: Boolean
) {
    constructor(json: JSONObject) : this(
        json.getString("name"),
        if (json.has("localizedNames")) LocalizedNames(json.getJSONObject("localizedNames")) else null,
        json.getString("id"),
        json.getBoolean("isActive")
    )
}
