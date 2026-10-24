package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.HavokFile

private const val PHYSICS_DATA = "hkpPhysicsData"
private const val MOPP = "hkpMoppBvTreeShape"

/** Deeper than a MOPP over a mesh, so a shape that names itself still ends */
private const val SHAPE_DEPTH_LIMIT = 8

/** Every rigid body of every 'hkpPhysicsData' the file's root container holds, from either edition */
public fun HavokFile.rigidBodies(): List<StaticBody> {
    val root = root ?: return emptyList()
    val layout = physicsLayout
    val variants = data.array(root.offset + layout.container.namedVariants)
    if (variants.isEmpty) return emptyList()

    return (0 until variants.count).flatMap { index ->
        val variant = checkNotNull(variants.offset) + index * layout.container.variantSize
        val physics = data.pointer(variant + layout.container.variant)
        if (physics == null || objectAt(physics)?.className != PHYSICS_DATA) emptyList() else bodiesOf(physics, layout)
    }
}

private fun HavokFile.bodiesOf(physics: Int, layout: PhysicsLayout): List<StaticBody> =
    pointersIn(physics + layout.body.systems).flatMap { system ->
        pointersIn(system + layout.body.rigidBodies).map { body ->
            StaticBody(
                name = data.string(body + layout.body.name).orEmpty(),
                transform = data.transform(body + layout.body.transform),
                collisionFilterInfo = data.int(body + layout.body.filter),
                shape = data.pointer(body + layout.body.shape)?.let { shapeAt(it, layout, 0) } ?: StaticShape.Unsupported("null")
            )
        }
    }

/**
 * The shape at [at], seen through a MOPP tree to what it wraps. The class comes from the file's
 * own object table: 'hkpShape::m_type' is not saved in a packfile, so the type word is no use here
 */
private fun HavokFile.shapeAt(at: Int, layout: PhysicsLayout, depth: Int): StaticShape {
    val className = objectAt(at)?.className ?: return StaticShape.Unsupported("unknown")
    if (depth >= SHAPE_DEPTH_LIMIT) return StaticShape.Unsupported(className)

    return when (className) {
        MOPP -> data.pointer(at + layout.moppChild)?.let { shapeAt(it, layout, depth + 1) } ?: StaticShape.Unsupported(className)
        in MESH_CLASSES -> meshAt(at, layout.mesh, className == CUSTOM_PARAM_MESH)
        CONVEX_VERTICES -> convexAt(at, layout.convex)
        else -> StaticShape.Unsupported(className)
    }
}

/** The elements of the 'hkArray' of pointers at [at] */
internal fun HavokFile.pointersIn(at: Int): List<Int> {
    val array = data.array(at)
    if (array.isEmpty) return emptyList()
    val first = checkNotNull(array.offset)
    return (0 until array.count).mapNotNull { data.pointer(first + it * data.pointerSize) }
}
