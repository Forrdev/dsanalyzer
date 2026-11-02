package com.sappyoak.dsanalyzer.app.analysis

import com.sappyoak.dsanalyzer.analysis.engine.GameSource
import com.sappyoak.dsanalyzer.analysis.engine.Requirement
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.params.GAME_PARAM_PATH
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.availableScripts

/** A run's view of one installation, through the shared file leases */
internal class InstallationSource private constructor(
    private val installation: Installation,
    private val files: InstallationFiles,
    override val scripts: List<ScriptId>,
    private val missing: Set<Requirement>
) : GameSource {
    override val build: GameBuild get() = installation.build

    override fun lacks(requirement: Requirement): Boolean = requirement in missing
    override suspend fun <T> read(block: suspend (GameFiles) -> T): T = files.use(installation, block)

    companion object {
        /** Settles up front what the installation holds, so the graph can say what cannot run */
        suspend fun open(installation: Installation, files: InstallationFiles): InstallationSource =
            files.use(installation) { games ->
                InstallationSource(
                    installation = installation,
                    files = files,
                    scripts = games.availableScripts(games.availableMaps(loadFileManifest(installation.build.edition))),
                    missing = Requirement.entries.filterTo(mutableSetOf()) { !games.holds(it) }
                )
            }

        private fun GameFiles.holds(requirement: Requirement): Boolean = when (requirement) {
            Requirement.GameParams -> exists(GAME_PARAM_PATH)
        }
    }
}
