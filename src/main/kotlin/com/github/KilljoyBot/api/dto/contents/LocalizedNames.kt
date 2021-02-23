package com.github.KilljoyBot.api.dto.contents

import org.json.JSONObject

data class LocalizedNames(
    val ar_AE: String,
    val de_DE: String,
    val en_GB: String,
    val en_US: String,
    val es_ES: String,
    val es_MX: String,
    val fr_FR: String,
    val id_ID: String,
    val it_IT: String,
    val ja_JP: String,
    val ko_KR: String,
    val pl_PL: String,
    val pt_BR: String,
    val ru_RU: String,
    val th_TH: String,
    val tr_TR: String,
    val vi_VN: String,
    val zh_CN: String,
    val zh_TW: String
) {

    constructor(json: JSONObject) : this(
        json.getString("ar-AE"),
        json.getString("de-DE"),
        json.getString("en-GB"),
        json.getString("en-US"),
        json.getString("es-ES"),
        json.getString("es-MX"),
        json.getString("fr-FR"),
        json.getString("id-ID"),
        json.getString("it-IT"),
        json.getString("ja-JP"),
        json.getString("ko-KR"),
        json.getString("pl-PL"),
        json.getString("pt-BR"),
        json.getString("ru-RU"),
        json.getString("th-TH"),
        json.getString("tr-TR"),
        json.getString("vi-VN"),
        json.getString("zh-CN"),
        json.getString("zh-TW")
    )
}