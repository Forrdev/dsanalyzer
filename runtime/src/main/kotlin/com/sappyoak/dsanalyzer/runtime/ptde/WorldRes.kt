package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** One `NS_FRPG::WorldBlockRes` — the resource side of a block, not [WorldBlock] */
internal const val WORLD_BLOCK_RES_SIZE = 0x170

/** No map requested. Every one of the three slots reads this when empty */
public const val NO_MAP: Int = -1

/**
 * 'NS_FRPG::WorldRes' The map load queue
 *
 * This is the resource nest, parallel to and distinct from [WorldChrMan]'s character nest.
 * They describe the same nine areas and seventeen blocks from different angles, with different
 * strides.
 *
 * [WorldBlock.MapDataPointer] says whether a block's MSB is already resident. This says what has been
 * *asked for*, and how far along it is, so a block that is queued but not yet loaded is visible
 *
 * Reached through the character manager rather than through a signature of its own. 'WorldRes' is not a singleton.
 * 'NS_FRPG::WorldInfoOwner' derives from it and is an embedded member of 'NS_FRPG::InGameStep' at '+0x180', so
 * there is no global naming it. But 'WorldChrManImp' is handed it at construction and keeps it at '+0x10' which makes
 * it reachable from a signature already verified
 */
public object WorldRes {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldRes",
        base = WorldChrMan.Base,
        offsets = listOf(0L, WorldChrMan.WorldResPointer.toLong()),
        lifetime = Lifetime.Session,
        // A read width rather than object size. WorldInfoOwner runs well past this, but
        // nothing below CurrentMap is declared and fetching the whole of it would be wasteful
        size = 0x800
    )

    /** The blocks, inline and '0x170' apart: See [WorldBlockRes] */
    public val Blocks: GameTable = GameTable(
        name = "WorldBlockRes",
        shape = TableShape.Counted(lengthAt = BlockCount, firstAt = BlocksPointer, indirect = true),
        elements = TableElements.Inline(WORLD_BLOCK_RES_SIZE),
        limit = 64
    )

    public const val AreaCount: Int = 0x700

    /** Points at the area array, stride '0x70' */
    public const val AreasPointer: Int = 0x7D4
    public const val BlockCount: Int = 0x7E0
    public const val BlocksPointer: Int = 0x7E4

    /**
     * The map currently requested, or [NO_MAP]
     */
    public const val CurrentMap: Int = 0x7E8

    /**
     * The map [CurrentMap] displaced, kept resident through the handoff rather than dropped
     *
     * The setter does 'if (current != id) { previous = current; current = id }' which is why two
     * maps are resident across a transition at all
     */
    public const val PreviousMap: Int = 0x7EC

    /**
     * An explicit load request, set by the warp routine and self-clearing once its block reaches
     * [LoadStage.Loading]
     */
    public const val WarpTarget: Int = 0x7F0

    /**
     * Decodes one of the three slots, or null when it is [NO_MAP]
     *
     * The four bytes are 'dd', 'cc', block, area from low to high so the dword is read apart
     * rather than compared whole
     */
    public fun mapOf(slot: Int): MapId? {
        if (slot == NO_MAP) return null
        return MapId.of(area = (slot ushr 24) and 0xFF, block = (slot ushr 16) and 0xFF)
    }
}

/**
 * One 'NS_FRPG::WorldBlockRes': a blocks load state
 *
 * The request is latched through several fields rather than one, and each is gated by something
 * different.
 */
public object WorldBlockRes {
    /** The owning 'WorldAreaRes' whose own state gates [WantLoaded] */
    public const val AreaPointer: Int = 0x0C

    /** Forces the request off regardless of everything below it */
    public const val Suppress: Int = 0x60

    /** A one-shot: Sets [Requested] on the next frame, then clears itself */
    public const val ForceLoad: Int = 0x61

    /** Set each tick by matching this block's id against [WorldRes]'s three slots, or by [ForceLoad] */
    public const val Requested: Int = 0x62

    public const val Latched: Int = 0x63

    /** The effective answer, additionally gated on the owning area's state being '6' */
    public const val WantLoaded: Int = 0x65

    /** See [LoadStage] */
    public const val Stage: Int = 0x68

    /** Held while something is waiting on this block, which keeps it from unloading underneath */
    public const val PinLoaded: Int = 0x160
}

/**
 * Values of [WorldBlockRes.Stage]
 *
 * Only the three that are pinned from both ends are named. [Loading] is set by the function
 * that issues the file requests, and [Loaded] is what a wait helper polls for. [Idle] is what
 * the unload-all path spins until every block reports. The intermediate stages are real but their order is inferred
 * from one switch statement, so they are deliberately not given names that would imply more confidence
 */
public object LoadStage {
    public const val Idle: Int = 0
    public const val Loading: Int = 2
    public const val Loaded: Int = 7

    /** Whether a block is past requesting and not yet finished */
    public fun isPending(stage: Int): Boolean = stage != Idle && stage != Loaded
}