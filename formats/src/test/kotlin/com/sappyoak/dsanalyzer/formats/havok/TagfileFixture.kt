package com.sappyoak.dsanalyzer.formats.havok

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * A Remastered-style collision tagfile built by hand: a root container naming one
 * 'hkpPhysicsData', one system, and one rigid body over a convex hull.
 *
 * The types are compact layouts of the fixture's own, nothing like the game's, so a reader that
 * found its offsets anywhere but the file's descriptions would read nonsense. Inheritance is
 * real: the body's name and collidable come from its parent 'hkpWorldObject', the collidable's
 * shape from 'hkpCdBody', and the point blocks' size from 'hkMatrix3Impl'
 */
internal const val TAGGED_BODY = "h0001B1"
internal const val TAGGED_FILTER = 0x42
internal const val TAGGED_RADIUS = 0.05f

/** A word in the root that holds a valid item index but is no slot */
internal const val DECOY_AT = 0x08
internal const val NAMED_VARIANTS_AT = 0x10

private const val ROOT = 0x000
private const val NAME = 0x030
private const val CLASS_NAME = 0x040
private const val PHYSICS = 0x050
private const val SYSTEMS = 0x070
private const val SYSTEM = 0x080
private const val BODIES = 0x0A0
private const val BODY = 0x0B0
private const val BODY_NAME = 0x150
private const val CONVEX = 0x160
private const val BLOCKS = 0x1A0
private const val PLANES = 0x200
private const val SIZE = 0x220

internal val TAGGED_TYPES = listOf(
    FixtureType("hkArray", size = 0x10),
    FixtureType("hkRootLevelContainer", size = 0x10, members = listOf(Triple("namedVariants", 0x0, "hkArray"))),
    FixtureType(
        "hkRootLevelContainer::NamedVariant", size = 0x18,
        members = listOf(Triple("name", 0x0, null), Triple("className", 0x8, null), Triple("variant", 0x10, null))
    ),
    FixtureType("hkpPhysicsData", size = 0x20, members = listOf(Triple("systems", 0x10, "hkArray"))),
    FixtureType("hkpPhysicsSystem", size = 0x20, members = listOf(Triple("rigidBodies", 0x8, "hkArray"))),
    FixtureType("hkpCdBody", size = 0x10, members = listOf(Triple("shape", 0x0, null))),
    FixtureType("hkpTypedBroadPhaseHandle", size = 0xC, members = listOf(Triple("collisionFilterInfo", 0x8, null))),
    FixtureType("hkpLinkedCollidable", "hkpCdBody", 0x20, listOf(Triple("broadPhaseHandle", 0x10, "hkpTypedBroadPhaseHandle"))),
    FixtureType("hkpWorldObject", size = 0x40, members = listOf(Triple("collidable", 0x8, "hkpLinkedCollidable"), Triple("name", 0x30, null))),
    FixtureType("hkMotionState", size = 0x50, members = listOf(Triple("transform", 0x0, null))),
    FixtureType("hkpMaxSizeMotion", size = 0x60, members = listOf(Triple("motionState", 0x10, "hkMotionState"))),
    FixtureType("hkpRigidBody", "hkpWorldObject", 0xA0, listOf(Triple("motion", 0x40, "hkpMaxSizeMotion"))),
    FixtureType(
        "hkpConvexVerticesShape", size = 0x40,
        members = listOf(
            Triple("radius", 0x10, null), Triple("rotatedVertices", 0x18, "hkArray"),
            Triple("numVertices", 0x28, null), Triple("planeEquations", 0x30, "hkArray")
        )
    ),
    FixtureType("hkMatrix3Impl", size = 0x30),
    FixtureType("hkMatrix3f", parent = "hkMatrix3Impl"),
    FixtureType("char", size = 1)
)

internal fun taggedCollision(sdk: String = "20150100"): BinaryReader = BinaryReader.of(taggedWriter().write(sdk))

/** Only an empty root container, which has no pointers and so no PTCH, as one game file does */
internal fun emptyTagfile(): BinaryReader = BinaryReader.of(
    TagfileWriter(TAGGED_TYPES, 0x10).apply { item(FixtureItem("hkRootLevelContainer", true, 0, 1)) }.write(patches = false)
)

private fun taggedWriter(): TagfileWriter = TagfileWriter(TAGGED_TYPES, SIZE).apply {
    val root = item(FixtureItem("hkRootLevelContainer", true, ROOT, 1))
    val variants = item(FixtureItem("hkRootLevelContainer::NamedVariant", false, NAMED_VARIANTS_AT, 1))
    val name = item(FixtureItem("char", false, NAME, 13))
    val className = item(FixtureItem("char", false, CLASS_NAME, 15))
    val physics = item(FixtureItem("hkpPhysicsData", true, PHYSICS, 1))
    val systems = item(FixtureItem("hkArray", false, SYSTEMS, 1))
    val system = item(FixtureItem("hkpPhysicsSystem", true, SYSTEM, 1))
    val bodies = item(FixtureItem("hkArray", false, BODIES, 1))
    val body = item(FixtureItem("hkpRigidBody", true, BODY, 1))
    val bodyName = item(FixtureItem("char", false, BODY_NAME, 8))
    val convex = item(FixtureItem("hkpConvexVerticesShape", true, CONVEX, 1))
    val blocks = item(FixtureItem("hkMatrix3f", false, BLOCKS, 2))
    val planes = item(FixtureItem("hkArray", false, PLANES, 2))

    // The data's own size words are left 0: an array's count is the item's
    slot(ROOT, variants).int(DECOY_AT, root)
    slot(NAMED_VARIANTS_AT, name).slot(NAMED_VARIANTS_AT + 0x8, className).slot(NAMED_VARIANTS_AT + 0x10, physics)
    text(NAME, "Physics Data").text(CLASS_NAME, "hkpPhysicsData")
    slot(PHYSICS + 0x10, systems).slot(SYSTEMS, system)
    slot(SYSTEM + 0x8, bodies).slot(BODIES, body)

    slot(BODY + 0x30, bodyName).text(BODY_NAME, TAGGED_BODY)
    slot(BODY + 0x8, convex).int(BODY + 0x8 + 0x10 + 0x8, TAGGED_FILTER)
    floats(BODY + 0x50, 1f, 0f, 0f, 9f, 0f, 1f, 0f, 9f, 0f, 0f, 1f, 9f, 5f, 6f, 7f, 9f)

    floats(CONVEX + 0x10, TAGGED_RADIUS).int(CONVEX + 0x28, 5)
    slot(CONVEX + 0x18, blocks).slot(CONVEX + 0x30, planes)
    floats(BLOCKS, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    floats(BLOCKS + 0x30, 1f, 9f, 9f, 9f, 1f, 9f, 9f, 9f, 1f, 9f, 9f, 9f)
    floats(PLANES, 1f, 0f, 0f, -1.05f, 0f, 1f, 0f, -1.05f)
}
