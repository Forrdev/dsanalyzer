package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.inject.cave.CodeCave
import com.sappyoak.dsanalyzer.runtime.sampling.SessionContext
import com.sappyoak.dsanalyzer.runtime.session.EngineDraw
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr.DSRDrawBackend
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde.PTDEDrawBackend

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

public fun engineDrawFor(context: SessionContext): EngineDraw? {
    val layout = context.layout.debugDraw ?: return null
    val backend = when (context.edition) {
        GameEdition.PrepareToDie -> PTDEDrawBackend
        GameEdition.Remastered -> DSRDrawBackend
    }
    return EngineDrawWriter(context, layout, backend)
}
