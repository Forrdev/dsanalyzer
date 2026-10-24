package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.HavokTypes

/**
 * What a Remastered tagfile stores a convex hull's blocks of four transposed points as: an
 * 'hkMatrix3f', twelve floats in three rows of four, x then y then z
 */
private const val POINT_BLOCK = "hkMatrix3f"
private const val VECTOR4_BYTES = 16

/**
 * A layout read from the file's own type descriptions, by member name. Each part is looked up
 * only when first needed, so a file with no convex shapes need not describe one
 */
internal class DescribedPhysicsLayout(private val types: HavokTypes) : PhysicsLayout {
    override val container by lazy {
        ContainerLayout(
            namedVariants = at("hkRootLevelContainer", "namedVariants"),
            variantSize = size("hkRootLevelContainer::NamedVariant"),
            variant = at("hkRootLevelContainer::NamedVariant", "variant")
        )
    }

    override val body by lazy {
        BodyLayout(
            systems = at("hkpPhysicsData", "systems"),
            rigidBodies = at("hkpPhysicsSystem", "rigidBodies"),
            name = at("hkpRigidBody", "name"),
            shape = at("hkpRigidBody", "collidable.shape"),
            filter = at("hkpRigidBody", "collidable.broadPhaseHandle.collisionFilterInfo"),
            transform = at("hkpRigidBody", "motion.motionState.transform")
        )
    }

    override val moppChild by lazy { at("hkpMoppBvTreeShape", "child.childShape") }

    override val mesh by lazy {
        val subpart = "hkpExtendedMeshShape::TrianglesSubpart"
        val storage = "hkpStorageExtendedMeshShape::MeshSubpartStorage"
        MeshLayout(
            trianglesSubparts = at(STORAGE_MESH, "trianglesSubparts"),
            storage = at(STORAGE_MESH, "meshstorage"),
            aabbCenter = at(STORAGE_MESH, "aabbCenter"),
            aabbHalfExtents = at(STORAGE_MESH, "aabbHalfExtents"),
            customParams = at(CUSTOM_PARAM_MESH, "materialArray"),
            subpartSize = size(subpart),
            subpartTriangles = at(subpart, "numTriangleShapes"),
            subpartIndexStriding = at(subpart, "indexStriding"),
            subpartStridingType = at(subpart, "stridingType"),
            subpartFlip = at(subpart, "flipAlternateTriangles"),
            subpartTransform = at(subpart, "transform"),
            vertices = at(storage, "vertices"),
            indices8 = at(storage, "indices8"),
            indices16 = at(storage, "indices16"),
            indices32 = at(storage, "indices32"),
            materialNameData = at("CustomMeshParameter", "materialNameData")
        )
    }

    override val convex by lazy {
        ConvexLayout(
            radius = at(CONVEX_VERTICES, "radius"),
            rotatedVertices = at(CONVEX_VERTICES, "rotatedVertices"),
            numVertices = at(CONVEX_VERTICES, "numVertices"),
            planeEquations = at(CONVEX_VERTICES, "planeEquations"),
            blockSize = size(POINT_BLOCK),
            blockX = 0,
            blockY = VECTOR4_BYTES,
            blockZ = 2 * VECTOR4_BYTES
        )
    }

    private fun at(type: String, path: String): Int =
        checkNotNull(types.offsetOf(type, path)) { "The file does not describe $type.$path" }

    private fun size(type: String): Int = checkNotNull(types.sizeOf(type)) { "The file does not describe $type" }
}
