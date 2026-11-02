package com.sappyoak.dsanalyzer.game.world.esd

import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.esd.readEsd
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.files.openBinder
import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** The player's own state machine, which the game runs for the local character in every map */
public val PLAYER_ESD_PATH: GamePath = GamePath.of("/chr/c0000.esd.dcx")

/** A map's talk scripts, one `tNNNNNN.esd` per talk id an enemy in that map can state */
public val MapId.talkBinderPath: GamePath get() = GamePath.of("/script/talk/$this.talkesdbnd.dcx")

public sealed interface EsdSource {
    public data object Player : EsdSource

    public data class Talk(public val map: MapId, public val talkId: Int) : EsdSource
}

public fun GameFiles.availableTalk(maps: List<MapId>): Map<MapId, List<Int>> =
    maps.mapNotNull { map -> openBinder(map.talkBinderPath)?.let { map to it.talkIds() } }.toMap()

public fun GameFiles.loadEsd(source: EsdSource): Esd? = when (source) {
    EsdSource.Player -> open(PLAYER_ESD_PATH)?.let(::readEsd)
    is EsdSource.Talk -> openBinder(source.map.talkBinderPath)?.let { binder ->
        binder.binder.entries.firstOrNull { talkIdOf(it.name) == source.talkId }?.let { readEsd(binder.open(it)) }
    }
}

private fun OpenBinder.talkIds(): List<Int> = binder.entries.mapNotNull { talkIdOf(it.name) }.sorted()

/** `...\t100000.esd` is talk 100000; any other name in the binder is not a talk script */
private fun talkIdOf(name: String?): Int? =
    name?.substringAfterLast('\\')?.removePrefix("t")?.substringBefore('.')?.toIntOrNull()
