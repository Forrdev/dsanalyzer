package com.sappyoak.dsanalyzer.app.analysis

import com.sappyoak.dsanalyzer.analysis.engine.GameSource
import com.sappyoak.dsanalyzer.analysis.engine.Requirement
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.params.GAME_PARAM_PATH
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.availableScripts

/** A run's view of one installation, through the shared file leases */
internal class InstallationSource private constructor(
    private val game: GameData,
    override val scripts: List<ScriptId>,
    private val missing: Set<Requirement>
) : GameSource {
    override val build: GameBuild get() = game.installation.build

    override fun lacks(requirement: Requirement): Boolean = requirement in missing
    override suspend fun <T> data(block: suspend (GameData) -> T): T = block(game)

    companion object {
        /** Settles up front what the installation holds, so the graph can say what cannot run */
        suspend fun open(game: GameData): InstallationSource = InstallationSource(
            game = game,
            scripts = game.catalog().scripts,
            missing = Requirement.entries.filterTo(mutableSetOf()) { !game.holds(it.path) }
        )

        private val Requirement.path: String get() = when (this) {
            Requirement.GameParams -> GAME_PARAM_PATH.value
        }
    }
}
