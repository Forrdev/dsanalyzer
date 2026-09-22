package com.sappyoak.dsanalyzer.game.files

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

public class InstallationFiles(
    private val scope: CoroutineScope,
    private val idleTimeout: Duration = 30.seconds,
    private val context: CoroutineContext = Dispatchers.IO,
    private val open: (Installation) -> GameFiles
) : AutoCloseable {
    private val held = mutableMapOf<InstallationId, Held>()

    public suspend fun <T> use(installation: Installation, block: suspend (GameFiles) -> T): T {
        val lease = withContext(context) { acquire(installation) }
        try {
            return block(lease.files)
        } finally {
            release(installation.id, lease)
        }
    }

    public fun retire(id: InstallationId) = synchronized(held) {
        held.remove(id)?.let(::retireHeld)
    }

    override fun close() = synchronized(held) {
        held.values.forEach(::retireHeld)
        held.clear()
    }

    private fun acquire(installation: Installation): Held = synchronized(held) {
        val entry = held.getOrPut(installation.id) { Held(open(installation)) }
        entry.idleClose?.cancel()
        entry.idleClose = null
        entry.leases++
        entry
    }

    private fun release(id: InstallationId, entry: Held) = synchronized(held) {
        entry.leases--
        when {
            entry.leases > 0 -> Unit
            entry.retired -> entry.files.close()
            else -> entry.idleClose = scope.launch {
                delay(idleTimeout)
                closeIfIdle(id, entry)
            }
        }
    }
    private fun closeIfIdle(id: InstallationId, entry: Held): Unit = synchronized(held) {
        if (entry.leases == 0 && held[id] === entry) {
            held.remove(id)
            entry.files.close()
        }
    }

    private fun retireHeld(entry: Held) {
        entry.retired = true
        entry.idleClose?.cancel()
        if (entry.leases == 0) entry.files.close()
    }

    private class Held(val files: GameFiles) {
        var leases = 0
        var retired = false
        var idleClose: Job? = null
    }
}