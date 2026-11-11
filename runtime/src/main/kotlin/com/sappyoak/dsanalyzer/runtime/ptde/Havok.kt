package com.sappyoak.dsanalyzer.runtime.ptde

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
        base = PTDESignatures.FrpgHavokMan,
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
    /** The collision filter id */
    const val CollisionFilterInfo: Int = 0x2C

    /**
     * 'hkpCollidable.allowedPenetrationDepth' at the collidable's '+0x4C'
     *
     * How far the solver permits something to be penetrated before pushing it out
     */
    const val AllowedPenetrationDepth: Int = 0x5C
    const val Name: Int = 0x78

    /**
     * The motion state transform, four 'hkVector4' rows
     *
     * Shape vertices are in the body's local space and this is what puts them
     * in the world
     */
    const val Transform: Int = 0xF0
}

internal object Shape {
    const val Type: Int = 0x0C
}

internal object MoppShape {
    const val ChildShape: Int = 0x34
}

internal const val SHAPE_SIZE: Int = 0x10
internal const val MOPP_SIZE: Int = 0x38

internal object MeshShape {
    const val AabbHalfExtents: Int = 0x90
    const val AabbCenter: Int = 0xA0

    val Subparts: GameTable = GameTable(
        name = "MeshTriangleSubparts",
        shape = TableShape.Counted(lengthAt = 0xBC, firstAt = 0xB8, indirect = true),
        elements = TableElements.Inline(SUBPART_SIZE),
        limit = 1024
    )
}

internal const val MESH_SIZE: Int = 0xC4
internal const val SUBPART_SIZE: Int = 0x70

internal object Subpart {
    const val TriangleCount: Int = 0x14
    const val VertexBase: Int = 0x18
    const val VertexCount: Int = 0x1C
    const val IndexBase: Int = 0x20

    const val VertexStriding: Int = 0x24
    const val IndexStriding: Int = 0x2C
    const val StridingType: Int = 0x2E
}

internal object ConvexShape {
    const val Radius: Int = 0x10
}

internal object BoxShape {
    const val HalfExtents: Int = 0x20
}

internal const val BOX_SIZE: Int = 0x30

internal object TranslateShape {
    const val ChildShape: Int = 0x18
    const val Translation: Int = 0x20
}

internal const val TRANSLATE_SIZE: Int = 0x30

internal object CylinderShape {
    const val CylRadius: Int = 0x14
    const val VertexA: Int = 0x20
    const val VertexB: Int = 0x30
}

internal const val CYLINDER_SIZE: Int = 0x40

internal object HullShape {
    const val RotatedVertices: Int = 0x40
    const val VertexCount: Int = 0x4C
    const val Connectivity: Int = 0x64
}

internal const val HULL_SIZE: Int = 0x68

internal const val FOUR_VECTORS_SIZE: Int = 0x30
internal const val POINTS_PER_BLOCK: Int = 4

internal object HullConnectivity {
    const val VertexIndices: Int = 0x08
    const val FaceSizes: Int = 0x14
}

internal const val CONNECTIVITY_SIZE: Int = 0x20
