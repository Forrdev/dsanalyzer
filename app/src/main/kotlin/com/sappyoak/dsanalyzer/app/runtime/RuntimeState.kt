package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.runtime.timing.TimingLibrary
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.RuntimeFeature
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionSnapshot

public data class RuntimeState(
    public val attached: AttachedGame? = null,
    public val snapshot: RuntimeSnapshot? = null,
    public val flagLog: List<FlagEntry> = emptyList(),
    /**
     * Every enemy the loaded blocks place, once something asks for them
     */
    public val placedEnemies: List<BlockRoster>? = null,
    public val collision: CollisionSnapshot? = null,
    public val problem: String? = null,
    public val installation: Installation? = null,
    public val timings: TimingLibrary = TimingLibrary()
) {
    public val live: Boolean get() = attached != null
    public val isCorrectGame: Boolean get() = installation != null && attached != null && installation.build.edition == attached.edition

    public val supports: Set<RuntimeFeature> get() = attached?.supports.orEmpty()
}

/** The game being read */
public data class AttachedGame(
    public val executableName: String,
    public val pid: Int,
    public val edition: GameEdition,
    public val build: String?,
    public val unresolved: List<String>,
    public val supports: Set<RuntimeFeature>
)

/** A flag that changed and the frame it changed on */
public data class FlagEntry(
    public val flagId: Int,
    public val set: Boolean,
    public val frame: Long,
    /** The map the change was seen in */
    public val place: WorldPlace
)
