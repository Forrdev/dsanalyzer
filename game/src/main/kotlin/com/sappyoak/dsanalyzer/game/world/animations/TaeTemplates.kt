package com.sappyoak.dsanalyzer.game.world.animations

import com.sappyoak.dsanalyzer.formats.tae.template.EventBank
import com.sappyoak.dsanalyzer.formats.tae.template.TaeTemplate
import com.sappyoak.dsanalyzer.formats.tae.template.readTaeTemplate
import com.sappyoak.dsanalyzer.game.bundledText

private const val CHARACTER_RESOURCE = "/definitions/ds1-tae-events-chr.json"
private const val OBJECT_RESOURCE = "/definitions/ds1-tae-events-obj.json"

public fun loadTaeTemplate(bank: EventBank): TaeTemplate = readTaeTemplate(
    bundledText(
        when (bank) {
            EventBank.Character -> CHARACTER_RESOURCE
            EventBank.Object -> OBJECT_RESOURCE
        }
    )
)