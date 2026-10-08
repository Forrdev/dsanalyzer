package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** The walk to [ChrIns], which the structures below continue rather than start again */
internal val THROUGH_CHARACTER: List<Long> = listOf(0L, 4L, 0L)

/**
 * 'NS_FRPG::ChrCtrl', the character's controller
 *
 * Polymorphic with one subclass per kind of character, 'PlayerCtrl' on the player and
 * 'EnemyCtrl' on an enemy both confirmed by RTTI on live objects. The base runs to '0x2AF' and each
 * subclass adds its own fields from '0x280'. So unlike [ChrIns] this structure is **not** uniform across
 * characters above that line. [NpcParamPairPointer] is an 'EnemyCtrl' field.
 *
 * 'sizeof' is '0x2E0' which the constructor state outright rather than having to guess it
 *
 * Reached through [ChrIns.ChrCtrlPointer], so this shares the character's signature and appends to
 * its walk rather than scanning for anything of its own. Each of the pointers below is a separate
 * allocation that the pool happens to hand out near this one. They are not embedded, and the destructor
 * frees them one at a time
 */
public object ChrCtrl {
    public val Pointer: GamePointer = GamePointer(
        name = "ChrCtrl",
        base = ChrIns.Base,
        offsets = THROUGH_CHARACTER + ChrIns.ChrCtrlPointer.toLong(),
        lifetime = Lifetime.World,
        size = 0x2E0
    )

    /** Back to the [ChrIns] that owns this controller */
    public const val ChrInsPointer: Int = 0x10

    public const val AnimDataPointer: Int = 0x14

    /**
     * The pooled region holding the animation request slots
     *
     * Nothing points at either slot. See [AnimRequestChannelA] for how they are addressed and for why
     * and offset taken from here can run off the end of one allocation into the next
     */
    public const val AnimRequestPoolPointer: Int = 0x18

    public const val PositionPointer: Int = 0x1C

    /** 'NS_FRPG::ComManipulator', the same object [ChrIns.ComManipulatorPointer] points at */
    public const val ComManipulatorPointer: Int = 0x54

    public const val Flags: Int = 0xC4
    public const val Warp: Int = 0xC8
    public const val WarpPosition: Int = 0xD0
    public const val WarpAngle: Int = 0xE4

    /**
     * An 'EnemyCtrl' field. A pair of '{ npcParamId, NpcParam* }, the same pair the character's own
     * vtable slot '0x80' returns.
     *
     * The vtable call is the portable way to reach it, because every 'ChrIns' subclass overrides that slot
     * while only 'EnemyCtrl' stores the pair here
     */
    public const val NpcParamPairPointer: Int = 0x2B0
}

public object ChrCtrlFlags {
    public const val DisableMapHit: Int = 0x00000010
}

public object ChrPosData {
    public val Pointer: GamePointer = GamePointer(
        name = "ChrPosData",
        base = ChrIns.Base,
        offsets = THROUGH_CHARACTER + listOf(
            ChrIns.ChrCtrlPointer.toLong(),
            ChrCtrl.PositionPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = 0x20
    )

    public const val Angle: Int = 0x4

    /** X, Y, and Z consecutively from here */
    public const val Position: Int = 0x10
}


public object AnimData {
    public val Pointer: GamePointer = GamePointer(
        name = "AnimData",
        base = ChrIns.Base,
        offsets = THROUGH_CHARACTER + listOf(
            ChrIns.ChrCtrlPointer.toLong(),
            ChrCtrl.AnimDataPointer.toLong()
        ),
        lifetime = Lifetime.World,
        size = 0x200
    )

    public const val PlaySpeed: Int = 0x64
}