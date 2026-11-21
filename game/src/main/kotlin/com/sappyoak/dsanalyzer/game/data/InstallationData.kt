package com.sappyoak.dsanalyzer.game.data

import java.nio.file.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.InstallationFiles

/**
 * Each installation's data; [directory] holds a folder per installation for what is kept between sessions.
 * Data for an installation that changed or retired is closed, cancelling anything still loading
 */
public class InstallationData(
    public val files: InstallationFiles,
    scope: CoroutineScope,
    private val directory: Path? = null
) {
    private val scope = CoroutineScope(scope.coroutineContext + SupervisorJob(scope.coroutineContext[Job]))
    private val held = mutableMapOf<InstallationId, GameData>()

    public fun of(installation: Installation): GameData = synchronized(held) {
        val current = held[installation.id]
        if (current?.installation == installation) return current
        current?.close()
        GameData(DataScope(installation, files, scope), directory?.resolve(installation.id.value))
            .also { held[installation.id] = it }
    }

    public fun retire(id: InstallationId): Unit = synchronized(held) {
        held.remove(id)?.close()
        files.retire(id)
    }
}
