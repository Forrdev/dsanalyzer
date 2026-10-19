package com.sappyoak.dsanalyzer.runtime.ptde


import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

internal const val MANAGER_SIZE: Int = 0xA0
internal const val WORLD_SIZE: Int = 0x24
internal const val ISLAND_SIZE: Int = 0x58
internal const val BODY_SIZE: Int = 0x130

/**
 * 'NS_FRPG::FrpgHavokManImpl' The entry into the physics word
 *
 * 'hkpWorld' has RTTI of its own and could be walked to directly, but
 * this is preferred as its FromSoft's singleton and carries its own book-keeping
 * beside the world.
 */

public object Havok {
    public val Manager: GamePointer = GamePointer(
        name = "FrpgHavokMan",
        base = Signature.parse(
            name = "FrpgHavokMan",
            pattern = "51 A1 ?? ?? ?? ?? 8B 48 14 53 55 8B 6C 24 10 8B 5D 3C 56 57",
            target = SignatureTarget.Embedded(2)
        ),
        offsets = listOf(0L),
        lifetime = Lifetime.Volatile,
        size = MANAGER_SIZE
    )

    public const val World: Int = 0x94
}

public object HavokWorld {
    public const val FixedIsland: Int = 0x20
}

public object HavokIsland {
    public val Entities: GameTable = GameTable(
        name = "FixedIslandEntities",
        shape = TableShape.Counted(lengthAt = 0x50, firstAt = 0x4C, indirect = true),
        elements = TableElements.Pointers,
        limit = 4096
    )
}

internal object RigidBody {
    const val Shape: Int = 0x10
    const val Name: Int = 0x78

    /**
     * The motion state transform, four 'hkVector4' rows
     *
     * Shape vertices are in the body's local space and this is what puts them
     * in the world
     */
    const val Transform: Int = 0xF0
}