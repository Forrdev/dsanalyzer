package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.readAliases
import com.sappyoak.dsanalyzer.formats.emevd.emedf.readEmedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.withAliases

private const val DEFINITIONS_RESOURCE = "/definitions/ds1-common.emedf.json"
private const val ALIASES_RESOURCE = "/definitions/ds1-event-aliases.json"

public fun loadInstructionDefinitions(): Emedf =
    readEmedf(bundled(DEFINITIONS_RESOURCE)).withAliases(readAliases(bundled(ALIASES_RESOURCE)))


internal fun bundled(resource: String): String {
    val stream = checkNotNull(Emedf::class.java.getResourceAsStream(resource)) {
        "Bundled resource $resource is missing"
    }
    return stream.bufferedReader().use { it.readText() }
}