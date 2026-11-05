package com.sappyoak.dsanalyzer.game.world.esd

import com.sappyoak.dsanalyzer.formats.esd.EsdDefinitions
import com.sappyoak.dsanalyzer.formats.esd.EsdKind
import com.sappyoak.dsanalyzer.formats.esd.readEsdDefinitions
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.bundledText

private const val NAMES_RESOURCE = "/definitions/ds1-esd-definitions.json"
private const val READINGS_RESOURCE = "/definitions/ds1-esd-readings.json"

public val EsdSource.kind: EsdKind
    get() = when (this) {
        EsdSource.Player -> EsdKind.Character
        is EsdSource.Talk -> EsdKind.Talk
    }


public fun loadEsdDefinitions(edition: GameEdition, kind: EsdKind): EsdDefinitions =
    readEsdDefinitions(bundledText(NAMES_RESOURCE), bundledText(READINGS_RESOURCE), edition.shortName, kind)
