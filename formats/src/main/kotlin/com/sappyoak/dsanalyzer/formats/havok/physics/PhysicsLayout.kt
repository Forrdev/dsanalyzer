package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.HavokFile

/**
 * Where the physics readers find each member. The object graph is the same in both editions;
 * only the offsets move, so the readers ask this rather than holding offsets of their own. Each
 * part covers one class's objects, and a file need only describe the classes it holds
 */
internal interface PhysicsLayout {
    val container: ContainerLayout
    val body: BodyLayout
    /** 'hkpMoppBvTreeShape::child' through to the shape it holds */
    val moppChild: Int
    val mesh: MeshLayout
    val convex: ConvexLayout
}

/** 'hkRootLevelContainer' and its named variants, which every file has even when it holds no physics */
internal class ContainerLayout(val namedVariants: Int, val variantSize: Int, val variant: Int)

/** 'hkpPhysicsData' down to each 'hkpRigidBody' */
internal class BodyLayout(
    val systems: Int,
    val rigidBodies: Int,
    val name: Int,
    val shape: Int,
    val filter: Int,
    val transform: Int
)

/** The storage mesh, its triangle subparts, their storage, and FromSoft's per-subpart parameters */
class MeshLayout(
    val trianglesSubparts: Int,
    val storage: Int,
    val aabbCenter: Int,
    val aabbHalfExtents: Int,
    val customParams: Int,
    val subpartSize: Int,
    val subpartTriangles: Int,
    val subpartIndexStriding: Int,
    val subpartStridingType: Int,
    val subpartFlip: Int,
    val subpartTransform: Int,
    val vertices: Int,
    val indices8: Int,
    val indices16: Int,
    val indices32: Int,
    val materialNameData: Int
)

/** 'hkpConvexVerticesShape' and its blocks of four transposed points */
class ConvexLayout(
    val radius: Int,
    val rotatedVertices: Int,
    val numVertices: Int,
    val planeEquations: Int,
    val blockSize: Int,
    val blockX: Int,
    val blockY: Int,
    val blockZ: Int
)

/** A tagfile describes its own layout; a packfile is PTDE's, whose layout is generated */
internal val HavokFile.physicsLayout: PhysicsLayout
    get() = types?.let(::DescribedPhysicsLayout) ?: PTDEPhysicsLayout
