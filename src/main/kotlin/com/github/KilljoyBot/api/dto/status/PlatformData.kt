package com.github.killjoybot.api.dto.status

import org.json.JSONObject

data class PlatformData(
    val id: String,
    val name: String,
    val locales: List<String>,
    val maintenances: List<Status>,
    val incidents: List<Status>
) {
    constructor(json: JSONObject) : this(
        json.getString("id"),
        json.getString("name"),
        json.getJSONArray("locales").map { it as String },
        json.getJSONArray("maintenances").map { (it as JSONObject); Status(it) },
        json.getJSONArray("incidents").map { (it as JSONObject); Status(it) }
    )
}
