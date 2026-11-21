package com.sappyoak.dsanalyzer.game.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles

internal class DataScope(
    val installation: Installation,
    private val files: InstallationFiles,
    parent: CoroutineScope
) : AutoCloseable {
    private val scope = CoroutineScope(parent.coroutineContext + SupervisorJob(parent.coroutineContext[Job]))

    fun <K : Any, V> memo(name: String, kept: Int? = null): Memo<K, V> = Memo(name, scope, kept)
    suspend fun <T> parse(block: (GameFiles) -> T): T =
        files.use(installation) { game -> withContext(Dispatchers.Default) { block(game) } }

    suspend fun <T> files(block: (GameFiles) -> T): T = files.use(installation) { block(it) }

    suspend fun <T> io(block: () -> T): T = withContext(Dispatchers.IO) { block() }

    override fun close() = scope.cancel("${installation.id.value} retired")
}
