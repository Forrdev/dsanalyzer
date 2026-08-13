package com.sappyoak.dsanalyzer.app.config

import kotlinx.serialization.json.Json

val jsonSerializer = Json {
    decodeEnumsCaseInsensitive = true
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}