package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEBuild
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace

public data class RuntimeState(
    public val attached: AttachedGame? = null,
    public val snapshot: RuntimeSnapshot? = null,
    public val flagLog: List<FlagEntry> = emptyList(),
    public val problem: String? = null
) {
    public val live: Boolean get() = attached != null
}

/** The game being read */
public data class AttachedGame(
    public val executableName: String,
    public val pid: Int,
    public val edition: GameEdition,
    public val build: PTDEBuild?,
    public val unresolved: List<String>
)

/** A flag that changed and the frame it changed on */
public data class FlagEntry(
    public val flagId: Int,
    public val set: Boolean,
    public val frame: Long,
    /** The map the change was seen in */
    public val place: WorldPlace
)