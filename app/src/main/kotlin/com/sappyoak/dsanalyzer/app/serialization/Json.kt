package com.sappyoak.dsanalyzer.app.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

private val module = SerializersModule {
    contextual(PathSerializer)
}

val jsonSerializer: Json = Json {
    decodeEnumsCaseInsensitive = true
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
    serializersModule = module
}

