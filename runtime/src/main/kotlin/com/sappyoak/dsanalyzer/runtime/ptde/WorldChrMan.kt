package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape


/** One `NS_FRPG::WorldBlockChr`, which the manager keeps 27 slots for and fills 17 of */
internal const val WORLD_BLOCK_SIZE = 0x98

/**
 * 'NS-FRPG::WorldChrManImp' the character side of the world.
 *
 * It owns the nine map areas and the seventeen map blocks, and through a block it reaches
 * that block's map data, which is the only route to the parts of a block the player is
 * *not* standing in. [ChrIns] cannot do that. Its own 'MapDataPointer' is null on the player
 *
 * Everything below is read from the constructor at '00e3ef70' rather than inferred from memory and
 * then confirmed against a running game.
 * `A1 imm32` is `mov eax, [global]`, so the operand one byte in is the address of the pointer to
 * the singleton, one dereference reaches the object, which is why [Pointer] walks a single zero
 */
public object WorldChrMan {
    internal val Base: Signature = Signature.parse(
        name = "WorldChrMan",
        pattern = "A1 ?? ?? ?? ?? 8B 40 3C 3B C8 75 02 33 C9 56",
        target = SignatureTarget.Embedded(1)
    )

    public val Pointer: GamePointer = GamePointer(
        name = "WorldChrMan",
        base = Base,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x13A0
    )

    /**
     * The map blocks, inline and '0x98' apart
     *
     * [BlockCount] is the right length **for this array** This is not the number of areas.
     * The constructor clears '0x1008' bytes here, which is 27 slots. The count states how many are in use
     */
    public val Blocks: GameTable = GameTable(
        name = "WorldBlocks",
        shape = TableShape.Counted(lengthAt = BlockCount, firstAt = BlocksPointer, indirect = true),
        elements = TableElements.Inline(WORLD_BLOCK_SIZE),
        limit = 64
    )

    /**
     * The 'NS_FRPG::WorldInfoOwner' this manager was constructed with.
     *
     * The only route to the load queue we have. 'WorldRes' is a base class of an object embedded
     * in 'InGameStep', so it has no global of its own. This pointer is how a signed structure reaches
     * it.
     */
    public const val WorldResPointer: Int = 0x10
    /** The local player, as a 'ChrIns*'. The Same object [ChrIns] resolves to, one read instead of three. */
    public const val PlayerPointer: Int = 0x3C

    public const val AreaCount: Int = 0x14

    /** Points at an array of nine '0x10' area records embedded at '+0x2D0' */
    public const val AreasPointer: Int = 0x18
    public const val BlockCount: Int = 0x1C

    /** Points at the block array embedded at '+0x360' */
    public const val BlocksPointer: Int = 0x20
}

/**
 * One 'NS_FRPG::WorldBlockChr' A map block as the character system sees it.
 *
 * The constructor ('00e41090') zeros through '+0x70' and sets '+0x94' to '-1' so
 * [MapDataPointer] starting null is a property of the code rather than something observed
 */
public object WorldBlock {
    /** The '0x3C'-byte record this block was built from */
    public const val SourcePointer: Int = 0x04

    /**
     * The block's loaded MSB, as an 'NS_FRPG::MsbResCap'
     *
     * **Null means the block is not loaded** It says nothing about a block that has been
     * *requested* and is still loading. The request and its progress live in 'NS_FRPG::WorldRes'
     */
    public const val MapDataPointer: Int = 0x60

    /**
     * Which of the seventeen blocks this is, repeated inside the block itself
     */
    public const val BlockIndex: Int = 0x64
}