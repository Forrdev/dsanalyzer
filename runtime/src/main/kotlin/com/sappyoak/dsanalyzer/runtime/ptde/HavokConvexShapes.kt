package com.sappyoak.dsanalyzer.runtime.ptde

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