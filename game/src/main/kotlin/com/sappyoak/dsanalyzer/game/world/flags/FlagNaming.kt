package com.sappyoak.dsanalyzer.game.world.flags

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.game.data.DecodedScript
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventCallIndex
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.game.world.text.loadText
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

/**
 * Names every flag the game data ties to something with a name of its own. [calls] fills in what
 * common events are handed, so it should be the index built over the same scripts
 */
public suspend fun GameData.nameFlags(language: TextLanguage, calls: EventCallIndex): FlagNames {
    val params = params() ?: return FlagNames.Empty
    val catalog = catalog()
    val maps = catalog.maps.mapNotNull { map -> msb(map)?.let { map to it } }.toMap()
    val text = read { it.loadText(language, NAMING_TEXT) }
    val scripts = catalog.scripts.mapNotNull { readable(it) }
    val names = catalog.scripts.associateWith { scriptNames(it) }
    val talk = talk().flatMap { (map, ids) ->
        ids.mapNotNull { id -> esd(EsdSource.Talk(map, id))?.let { TalkScript(map, id, it) } }
    }

    return withContext(Dispatchers.Default) {
        val context = NamingContext(params, paramNames(language), text, maps, loadCharacterModels())
        val bound = scripts.flatMap { it.bound(calls) }
        FlagNames.of(
            context.lotFlags() +
                    context.mapFlags() +
                    context.scriptFlags(bound) { names.getValue(it.script) } +
                    context.talkFlags(talk)
        )
    }
}

/** A script that does not parse names nothing rather than failing the rest */
private suspend fun GameData.readable(script: ScriptId): DecodedScript? = try {
    script(script)
} catch (_: BinaryFormatException) {
    null
}
