package com.github.KilljoyBot.api.dto.status

import org.json.JSONObject

data class Status(
    val id: Int,
    val maintenanceStatus: MaintenanceStatus,
    val incidentSeverity: IncidentSeverity,
    val titles: List<Content>,
    val updates: List<Update>,
    val createdAt: String,
    val archiveAt: String,
    val updatedAt: String,
    val platforms: List<Platform>
) {

    constructor(json: JSONObject) : this(
        json.getInt("id"),
        MaintenanceStatus.valueOf(json.getString("maintenance_status").toUpperCase()),
        IncidentSeverity.valueOf(json.getString("incident_severity").toUpperCase()),
        json.getJSONArray("titles").map { (it as JSONObject); Content(it) },
        json.getJSONArray("updates").map { (it as JSONObject); Update(it) },
        json.getString("created_at"),
        json.getString("archive_at"),
        json.getString("updated_at"),
        json.getJSONArray("platforms").map { (it as String); Platform.valueOf(it.toUpperCase()) }
    )

    enum class MaintenanceStatus {
        SCHEDULED, IN_PROGRESS, COMPLETE
    }

    enum class IncidentSeverity {
        INFO, WARNING, CRITICAL
    }

    enum class Platform {
        WINDOWS, MACOS, ANDROID, IOS, PS4, XBONE, SWITCH, UNKNOWN
    }

    data class Content(
        val locale: String,
        val content: String
    ) {
        constructor(json: JSONObject) : this(
            json.getString("locale"),
            json.getString("content")
        )
    }

    data class Update(
        val id: Int,
        val author: String,
        val publish: Boolean,
        val publishLocations: List<Location>,
        val translations: List<Content>,
        val createdAt: String,
        val updatedAt: String
    ) {

        constructor(json: JSONObject) : this(
            json.getInt("id"),
            json.getString("author"),
            json.getBoolean("publish"),
            json.getJSONArray("publish_locations").map { (it as String); Location.valueOf(it.toUpperCase()) },
            json.getJSONArray("translations").map { (it as JSONObject); Content(it) },
            json.getString("created_at"),
            json.getString("updated_at")
        )

        enum class Location {
            RIOTCLIENT, RIOTSTATUS, GAME, UNKNOWN
        }
    }
}
