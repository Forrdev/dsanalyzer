package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

public enum class ShapeKind(public val code: Int) {
    Cylinder(2),
    ConvexVertices(6),
    Mopp(10),
    ConvexTranslate(11),
    Mesh(14);

    public companion object {
        private val byCode = entries.associateBy { it.code }

        public fun of(code: Int): ShapeKind? = byCode[code]
    }
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