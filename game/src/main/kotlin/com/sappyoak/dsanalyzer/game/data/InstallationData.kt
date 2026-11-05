package com.sappyoak.dsanalyzer.game.data

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.plus

import com.sappyoak.dsanalyzer.game.files.InstallationFiles

public class InstallationData(
    public val files: InstallationFiles,
    scope: CoroutineScope
) {
    private val scope = CoroutineScope(scope.coroutineContext + SupervisorJob(scope.coroutineContext[Job]))
    private val held = mutableMapOf<InstallationId, GameData>()

    public fun of(installation: Installation): GameData = synchronized(held) {
        held[installation.id]?.takeIf { it.installation == installation }
            ?: GameData(installation, files, scope).also { held[installation.id] = it }
    }

    public fun retire(id: InstallationId): Unit = synchronized(held) {
        held.remove(id)?.clear()
        files.retire(id)
    }
}
