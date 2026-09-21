package com.sappyoak.dsanalyzer.runtime

import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ProcessAccessException
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/** An open connection to a running game */
public class GameConnection internal constructor(
    public val game: GameProcess,
    private val process: AttachedProcess
) : AutoCloseable {
    public val isRunning: Boolean get() = process.isRunning

    override fun close() {
        process.close()
    }
}

public sealed interface ConnectResult {
    public data class Connected(public val connection: GameConnection) : ConnectResult
    public data class Refused(public val reason: String) : ConnectResult
}

public fun Processes.connect(game: GameProcess): ConnectResult {
    val process = try {
        attach(game.process)
    } catch (err: ProcessAccessException) {
        return ConnectResult.Refused(err.message ?: "The process could not be opened")
    }

    if (process.pointerSize != game.edition.pointerSize) {
        process.close()
        return ConnectResult.Refused(
            "${game.process.executableName} is ${process.pointerSize.bits}-bit, " +
            "but ${game.edition.name} is ${game.edition.pointerSize.bits}-bit"
        )
    }

    return ConnectResult.Connected(GameConnection(game, process))
}

private val PointerSize.bits: Int get() = value * Byte.SIZE_BITS