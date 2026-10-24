package com.sappyoak.dsanalyzer.formats.havok

import kotlin.math.sqrt

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

/**
 * A map collision file built by hand, shaped as the game's are: a root container naming one
 * 'hkpPhysicsData', one system, and its bodies. Three bodies here: a FromSoft mesh behind a MOPP,
 * a convex hull, and a sphere no reader handles.
 *
 * Every object sits at a constant, and member offsets are the ones read out of the game's files,
 * so the layout is stated here rather than borrowed from the reader
 */
internal const val MESH_BODY = "h0001B0"
internal const val CONVEX_BODY = "h0002B0"
internal const val SPHERE_BODY = "h0003B0"
internal const val MESH_FILTER = 0x2B_0001
internal const val MATERIAL_NAME_DATA = 7
internal const val CONVEX_RADIUS = 0.05f
internal const val SPHERE_CLASS = "hkpSphereShape"

private const val ROOT = 0x000
private const val VARIANT = 0x010
private const val PHYSICS = 0x020
private const val SYSTEMS = 0x040
private const val SYSTEM = 0x050
private const val BODIES = 0x0A0
private const val MESH_BODY_AT = 0x0B0
private const val CONVEX_BODY_AT = 0x2D0
private const val SPHERE_BODY_AT = 0x4F0
private const val MOPP = 0x710
private const val MESH = 0x750
private const val SUBPARTS = 0x870
private const val STORAGE_LIST = 0x8E0
private const val PARAM_LIST = 0x8F0
private const val STORAGE = 0x900
private const val PARAM = 0x970
private const val VERTICES = 0x9A0
private const val INDICES = 0x9E0
private const val CONVEX = 0xA00
private const val HULL_POINTS = 0xA70
private const val PLANES = 0xAD0
private const val SPHERE = 0xAF0
private const val STRINGS = 0xB00
private const val SIZE = 0xB80

/** 'hkpRigidBody' members, by the paths the game's files were read through */
private const val BODY_NAME = 0x78
private const val BODY_SHAPE = 0x10
private const val BODY_FILTER = 0x10 + 0x14 + 0x08
private const val BODY_TRANSFORM = 0xE0 + 0x10

private val HALF_TURN_COMPONENT = sqrt(0.5f)

internal fun collisionFile(layout: Int = PC_32_LAYOUT, magic: Int = PACKFILE_MAGIC_0): BinaryReader =
    BinaryReader.of(packfile(collisionData(), layout, magic))

private fun collisionData(): DataSection = DataSection(SIZE).apply {
    obj(ROOT, "hkRootLevelContainer").array(ROOT, VARIANT, 1)
    text(VARIANT, STRINGS, "Physics Data").text(VARIANT + 4, STRINGS + 0x10, "hkpPhysicsData").pointer(VARIANT + 8, PHYSICS)
    obj(PHYSICS, "hkpPhysicsData").array(PHYSICS + 0xC, SYSTEMS, 1).pointer(SYSTEMS, SYSTEM)
    obj(SYSTEM, "hkpPhysicsSystem").array(SYSTEM + 0x8, BODIES, 3)
        .pointer(BODIES, MESH_BODY_AT).pointer(BODIES + 4, CONVEX_BODY_AT).pointer(BODIES + 8, SPHERE_BODY_AT)

    body(MESH_BODY_AT, MESH_BODY, STRINGS + 0x20, MOPP)
    int(MESH_BODY_AT + BODY_FILTER, MESH_FILTER)
    // Havok leaves the rotation columns' w lanes as whatever they were; the reader must not keep them
    floats(MESH_BODY_AT + BODY_TRANSFORM, 1f, 0f, 0f, 0.5f, 0f, 1f, 0f, 0.5f, 0f, 0f, 1f, 0.5f, 1f, 2f, 3f, 0.5f)
    body(CONVEX_BODY_AT, CONVEX_BODY, STRINGS + 0x30, CONVEX)
    body(SPHERE_BODY_AT, SPHERE_BODY, STRINGS + 0x40, SPHERE)
    obj(SPHERE, SPHERE_CLASS)

    obj(MOPP, "hkpMoppBvTreeShape").pointer(MOPP + 0x30 + 4, MESH)
    mesh()
    convex()
}

private fun DataSection.body(at: Int, name: String, nameAt: Int, shape: Int) {
    obj(at, "hkpRigidBody").text(at + BODY_NAME, nameAt, name).pointer(at + BODY_SHAPE, shape)
    floats(at + BODY_TRANSFORM, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
}

/**
 * A unit square in the y = 0 plane, as two triangles stored 'int16' four to a row. The subpart is
 * flipped, so the second triangle comes out wound the other way, and placed: scaled by 2, turned a
 * quarter about y, moved 10 along x
 */
private fun DataSection.mesh() {
    obj(MESH, "CustomParamStorageExtendedMeshShape")
    floats(MESH + 0x90, 0.5f, 0f, 0.5f, 0f).floats(MESH + 0xA0, 0.5f, 0f, 0.5f, 0f)
    array(MESH + 0xB8, SUBPARTS, 1).array(MESH + 0xF0, STORAGE_LIST, 1).array(MESH + 0x110, PARAM_LIST, 1)
    pointer(STORAGE_LIST, STORAGE).pointer(PARAM_LIST, PARAM)

    int(SUBPARTS + 0x14, 2).short(SUBPARTS + 0x2C, 8).byte(SUBPARTS + 0x2E, 2).byte(SUBPARTS + 0x2F, 1)
    floats(SUBPARTS + 0x40, 10f, 0f, 0f, 0f, 0f, HALF_TURN_COMPONENT, 0f, HALF_TURN_COMPONENT, 2f, 2f, 2f, 0f)

    obj(STORAGE, "hkpStorageExtendedMeshShapeMeshSubpartStorage")
    array(STORAGE + 0x8, VERTICES, 4).array(STORAGE + 0x20, INDICES, 8)
    floats(VERTICES, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 1f, 0f)
    intArrayOf(0, 1, 2, 0, 0, 2, 3, 0).forEachIndexed { i, index -> short(INDICES + i * 2, index) }

    obj(PARAM, "CustomMeshParameter").int(PARAM + 0x20, MATERIAL_NAME_DATA)
}

/** Five points, transposed four to a block; the second block's unused lanes hold 9 to be ignored */
private fun DataSection.convex() {
    obj(CONVEX, "hkpConvexVerticesShape").floats(CONVEX + 0x10, CONVEX_RADIUS)
    array(CONVEX + 0x40, HULL_POINTS, 2).int(CONVEX + 0x4C, 5).array(CONVEX + 0x58, PLANES, 2)
    floats(HULL_POINTS, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    floats(HULL_POINTS + 0x30, 1f, 9f, 9f, 9f, 1f, 9f, 9f, 9f, 1f, 9f, 9f, 9f)
    floats(PLANES, 1f, 0f, 0f, -1.05f, 0f, 1f, 0f, -1.05f)
}
