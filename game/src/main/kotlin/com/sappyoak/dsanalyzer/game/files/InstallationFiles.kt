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

/**
 * Keeps at most one open [GameFiles] per installation, shared by everything that reads it.
 *
 * Access is leased through [use] rather than handed out because [GameFiles] is backed by mapped
 * memory and reading it after it closes crashes the JVM rather than throwing. An instance is never
 * closed while leased.
 *
 * This is the same reason that a simple monitor is used and locks on the entire map.
 * A concurrent map would not provide sufficient atomicity around the [Held.idleClose] job potentially leading
 * to the case where you could acquire a lease after it has been closed, and since [AutoCloseable] is non-suspending
 * a mutex would force a deadlock
 *
 * Idle instances close after [idleTimeout]. On Windows a mapped file cannot be modified, so holding
 * the archive open for the app's entire lifetime would make Steam updates of the game fail.
 */
public class InstallationFiles(
    private val scope: CoroutineScope,
    private val idleTimeout: Duration = 30.seconds,
    private val context: CoroutineContext = Dispatchers.IO,
    private val open: (Installation) -> GameFiles = ::openGameFiles
) : AutoCloseable {
    private val held = mutableMapOf<InstallationId, Held>()

    public suspend fun <T> use(installation: Installation, block: suspend (GameFiles) -> T): T =
        withContext(context) {
            val lease = acquire(installation)
            try {
                block(lease.files)
            } finally {
                release(installation.id, lease)
            }
        }

    public fun retire(id: InstallationId) = synchronized(held) {
        val entry = held.remove(id)
        if (entry != null) {
            retireHeld(entry)
        }
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