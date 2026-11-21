package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.inject.CodeCave
import com.sappyoak.dsanalyzer.runtime.write.SessionContext
import com.sappyoak.dsanalyzer.runtime.snapshot.EngineDraw

internal class DrawEdit(val address: Address, val bytes: ByteArray)

/** A dword of the control block, for a value a cave would otherwise have built in that the game can change */
internal class ControlWord(val offset: Int, val value: Int)

internal sealed interface DrawRoute {
    class Ready(
        val edits: List<DrawEdit>,
        val caves: List<CodeCave>,
        val writable: List<AddressRange> = emptyList(),
        val words: List<ControlWord> = emptyList()
    ) : DrawRoute
    class Unavailable(val reason: String) : DrawRoute
}

/**
 * The one part of the in-game draw that differs by edition. What has to change for the game's
 * debug renderer to reach the screen, and to obey the control block at [control]
 */
internal fun interface DrawBackend {
    /**
     * Whether the game remakes what [route] reads, as PTDE's load screens do, so it is routed again while
     * installed and its edits and words rewritten when they change. They caves stay, only their data changes
     */
    val reroutes: Boolean get() = false

    fun route(context: SessionContext, control: Address): DrawRoute
}

public fun engineDrawFor(context: SessionContext): EngineDraw =
    EngineDrawWriter(context, context.layout.debugDraw, context.draw)
