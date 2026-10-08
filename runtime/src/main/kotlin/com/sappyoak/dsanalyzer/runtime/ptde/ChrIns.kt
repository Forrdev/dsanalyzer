package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/**
 * 'NS_FRPG::ChrIns' -- the part every character has and the structures hanging off of it
 *
 * FieldInsBase -> ChrIns -> [PlayerIns, EnemyIns, ReplayGhostIns]
 *
 * Both subclasses share one base-class descriptor and every base sits at displacement 0, so an offset
 * below '0x410' reads the same field on an enemy as on the player.
 *
 * `8B 15 ?? ?? ?? ??` is `mov edx, [imm32]`, so the operand two bytes in is the address of the
 * global rather than the global itself, which is what [SignatureTarget.Embedded] reads
 */

public object ChrIns {
    internal val Base: Signature = Signature.parse(
        name = "ChrIns",
        pattern = "8B 15 ?? ?? ?? ?? F3 0F 10 44 24 30 52",
        target = SignatureTarget.Embedded(2)
    )

    public val Pointer: GamePointer = GamePointer(
        name = "ChrIns",
        base = Base,
        offsets = listOf(0L, 4L, 0L),
        lifetime = Lifetime.World,
        size = 0x640
    )

    /** The entity handle the character was constructed with */
    public const val Handle: Int = 0x04

    public const val ChrResPointer: Int = 0x18
    public const val ChrCtrlPointer: Int = 0x28

    /** 'NS_FRPG::ComManipulator', the same object [ChrCtrl.ComManipulatorPointer] points at */
    public const val ComManipulatorPointer: Int = 0x2C

    /**
     * The chr model name in UTF-16
     * The inline buffer of a small-string-optimized 'basic_string<char16_t>. Eight slots here.
     * The length at [ModelNameLength], the capacity of 7 four bytes past that. A name needing more than
     * seven characters would live on the heap instead, which no stock chr name does
     */
    public const val ModelName: Int = 0x38
    public const val ModelNameLength: Int = 0x48

    /**
     * The numeric part of [ModelName], so "c1000" reads as '1000'
     *
     * The constructor parses it once rather than the game deriving it per read, which makes this
     * the cheapest way to ask what kind of character something is
     */
    public const val ModelId: Int = 0x6C

    public const val ChrType: Int = 0x70
    public const val TeamType: Int = 0x74

    /**
     * World position, held as the translation column of the 3x4 transform starting at '0x90'
     *
     * This is **not** three consecutive floats. THey are '0x10' apart, one per row, so a 'vec3' read at
     * [PositionX] returns the tail of the first row rather than a position.
     * [CharPosData.Position] is the contiguous one and stays the default source
     */
    public const val PositionX: Int = 0x9C
    public const val PositionY: Int = 0xAC
    public const val PositionZ: Int = 0xBC

    /**
     * DS-Gadget's name for this, which our own read of the constructor disagrees with. '0xFC'
     * begins five 8-byte objects initialized from a pair of globals, not an animation id.
     *
     * Kept because a write-only trigger would look exactly like this from the constructor alone and
     * nothing here has disproven it, but it should not be read expecting to find an animation
     */
    public const val ForcePlayAnimation: Int = 0xFC

    public const val SpecialEffectPointer: Int = 0x1E0
    public const val Flags1: Int = 0x1FC
    public const val PlayRegion: Int = 0x284

    public const val Health: Int = 0x2D4
    public const val HealthMax: Int = 0x2D8
    public const val Mp: Int = 0x2DC
    public const val MpMax: Int = 0x2E0
    public const val Stamina: Int = 0x2E4
    public const val StaminaMax: Int = 0x2E8

    /**
     * The three maxima before equipment and effects modify them.
     *
     * 'EnemyIns' fills [MpMax] and [StaminaMax] from 'NpcParam', then clamps the current values
     * against them, stamina to '[-100, max]' rather than '[0, max]' which is why a stamina read can
     * legitimately come back negative
     */
    public const val HealthBaseMax: Int = 0x2F4
    public const val MpBaseMax: Int = 0x2F8
    public const val StaminaBaseMax: Int = 0x2FC

    /**
     * Four consecutive resistances, current here and maximum at [ResistMax]
     *
     * 'EnemyIns' loads the maxima from four 'u16' at 'NpcParam+0x104' and clamps each '>= 0' into
     * this block. Four of them for poison, disease, bleed and curse, but which index is which is not confirmed yet,
     * so the block is read rather than naming a member
     */
    public const val ResistCurrent: Int = 0x300
    public const val ResistMax: Int = 0x310

    public const val Flags2: Int = 0x3C4

    /** Past the '0x410' seam, so a 'PlayerIns' field rather than one an enemy also has' */
    public const val StoredItem: Int = 0x628
}

public object ChrInsFlags1 {
    public const val SetDeadMode: Int = 0x02000000
    public const val DisableDamage: Int = 0x04000000
    public const val EnableInvincible: Int = 0x08000000
    public const val FirstPerson: Int = 0x00100000
    public const val SetDrawEnable: Int = 0x00800000
    public const val SetSuperArmor: Int = 0x00010000
    public const val SetDisableGravity: Int = 0x00004000
    public const val ForceUpdateNextFrame: Int = 0x00000200
    public const val SetEventGenerate: Int = 0x00000010
    public const val DisableHPGauge: Int = 0x00000008
}

public object ChrInsFlags2 {
    public const val NoGoodsConsume: Int = 0x01000000
    public const val NoUpdate: Int = 0x00008000
    public const val NoMPConsume: Int = 0x00000800
    public const val NoStaminaConsume: Int = 0x00000400
    public const val NoMove: Int = 0x00000200
    public const val NoAttack: Int = 0x00000100
    public const val NoHit: Int = 0x00000080
    public const val NoDamage: Int = 0x00000040
    public const val NoDead: Int = 0x00000020
}