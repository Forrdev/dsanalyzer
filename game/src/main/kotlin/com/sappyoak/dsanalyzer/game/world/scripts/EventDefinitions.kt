package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.readAliases
import com.sappyoak.dsanalyzer.formats.emevd.emedf.readEmedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.withAliases
import com.sappyoak.dsanalyzer.game.bundledText

private const val DEFINITIONS_RESOURCE = "/definitions/ds1-common.emedf.json"
private const val ALIASES_RESOURCE = "/definitions/ds1-event-aliases.json"

public fun loadInstructionDefinitions(): Emedf =
    readEmedf(bundledText(DEFINITIONS_RESOURCE)).withAliases(readAliases(bundledText(ALIASES_RESOURCE)))
