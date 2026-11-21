package com.sappyoak.dsanalyzer.game.world.flags

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.bundledText

private const val RESOURCE = "/definitions/ds1-engine-flags.json"

private val JSON = Json { ignoreUnknownKeys = true }

/** The bundled list: flags the executable itself sets or reads by a fixed id, found by reading its code */
@Serializable
private class EngineFlagsFile(val sites: List<EngineFlagRecord>)

@Serializable
private class EngineFlagRecord(
    val flags: List<Int>,
    val access: FlagAccess,
    val function: String,
    val address: String,
    val label: String
)

private val SITES: List<Pair<Int, FlagSite>> by lazy {
    JSON.decodeFromString<EngineFlagsFile>(bundledText(RESOURCE)).sites.flatMap { record ->
        val site = FlagSite(record.access, record.label, FlagSource.Engine(record.function, record.address.toLong(16)))
        record.flags.map { it to site }
    }
}

/** Every place the executable sets or reads a flag by a fixed id */
internal val EngineOrigin: FlagOrigin = FlagOrigin { OriginReading(sites = SITES) }
