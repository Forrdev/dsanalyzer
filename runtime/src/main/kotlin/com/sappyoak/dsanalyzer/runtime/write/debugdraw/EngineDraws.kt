package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.inject.CodeCave
import com.sappyoak.dsanalyzer.runtime.write.SessionContext
import com.sappyoak.dsanalyzer.runtime.snapshot.EngineDraw

internal class DrawEdit(val address: Address, val bytes: ByteArray)

internal sealed interface DrawRoute {
    class Ready(
        val edits: List<DrawEdit>,
        val caves: List<CodeCave>,
        val writable: List<AddressRange> = emptyList()
    ) : DrawRoute
    class Unavailable(val reason: String) : DrawRoute
}

/**
 * The one part of the in-game draw that differs by edition. What has to change for the game's
 * debug renderer to reach the screen, and to obey the control block at [control]
 */
internal fun interface DrawBackend {
    fun route(context: SessionContext, control: Address): DrawRoute
}

public fun engineDrawFor(context: SessionContext): EngineDraw =
    EngineDrawWriter(context, context.layout.debugDraw, context.draw)
