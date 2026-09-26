package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.readEmedf

private const val DEFINITIONS_RESOURCE = "/definitions/ds1-common.emedf.json"

public fun loadInstructionDefinitions(): Emedf {
    val stream = checkNotNull(Emedf::class.java.getResourceAsStream(DEFINITIONS_RESOURCE)) {
        "Bundled definitions $DEFINITIONS_RESOURCE are missing"
    }
    return stream.bufferedReader().use { readEmedf(it.readText()) }
}