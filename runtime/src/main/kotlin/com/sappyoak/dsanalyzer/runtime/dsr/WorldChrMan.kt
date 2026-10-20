package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** One 'NS_FRPG::WorldBlockChr' */
internal const val WORLD_BLOCK_CHR_SIZE: Int = 0xE8

/** One slot of a block's character array */
internal const val CHARACTER_SLOT_SIZE: Int = 0x38

/** Through [WorldChrMan.PlayerPointer], the furthest field declared here */
private const val WORLD_CHR_MAN_SIZE: Int = 0x70
/** Through [ChrIns.Stamina] */
private const val CHR_INS_SIZE: Int = 0x400

public object WorldChrMan {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldChrMan",
        base = DSRSignatures.WorldChrMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = WORLD_CHR_MAN_SIZE
    )

    public const val WorldInfoOwnerPointer: Int = 0x10
    public const val AreaCount: Int = 0x18
    public const val AreasPointer: Int = 0x20
    public const val BlockCount: Int = 0x28
    public const val BlocksPointer: Int = 0x30

    /** The local player as a 'ChrIns*' */
    public const val PlayerPointer: Int = 0x68

    public val Blocks: GameTable = GameTable(
        name = "WorldBlockChr",
        shape = TableShape.Counted(lengthAt = BlockCount, firstAt = BlocksPointer, indirect = true),
        elements = TableElements.Inline(WORLD_BLOCK_CHR_SIZE),
        limit = 64
    )
}

/** The local player */
public object Player {
    public val Pointer: GamePointer = GamePointer(
        name = "Player",
        base = DSRSignatures.WorldChrMan,
        offsets = listOf(0L, WorldChrMan.PlayerPointer.toLong()),
        lifetime = Lifetime.World,
        size = CHR_INS_SIZE
    )
}

/**
 * One 'NS_FRPG::WorldBlockChr
 */
public object WorldBlockChr {
    public const val BlockInfoPointer: Int = 0x08
    public const val AreaPointer: Int = 0x10
    public const val CharacterCount: Int = 0x48
    public const val CharactersPointer: Int = 0x50
    public const val MapDataPointer: Int = 0x90

    public val Characters: GameTable = GameTable(
        name = "BlockCharacters",
        shape = TableShape.Counted(
            lengthAt = CharacterCount,
            firstAt = CharactersPointer,
            indirect = true
        ),
        elements = TableElements.Inline(CHARACTER_SLOT_SIZE)
    )
}

/**
 * One slot of [WorldBlockChr.Characters]
 */
public object CharacterSlot {
    public const val Character: Int = 0x00

    public const val SpecialEffect: Int = 0x18
}