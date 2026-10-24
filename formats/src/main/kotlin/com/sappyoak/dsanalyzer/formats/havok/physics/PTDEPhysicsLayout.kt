package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.ptde.HkMotionState
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkRootLevelContainer
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkRootLevelContainerNamedVariant
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpCdBody
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpCollidable
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpConvexVerticesShape
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpConvexVerticesShapeFourVectors
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpExtendedMeshShapeTrianglesSubpart
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpMoppBvTreeShape
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpMotion
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpPhysicsData
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpPhysicsSystem
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpRigidBody
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpSingleShapeContainer
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpStorageExtendedMeshShape
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpStorageExtendedMeshShapeMeshSubpartStorage
import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpTypedBroadPhaseHandle

/**
 * PTDE's layout, from the reflection in DARKSOULS.exe. FromSoft's two classes have no reflection
 * there; their offsets are where the Remastered tagfiles' own descriptions put the same members
 * once laid out for 32 bits, and agree with PTDE's values for the same parts
 */
internal object PTDEPhysicsLayout : PhysicsLayout {
    /** 'CustomParamStorageExtendedMeshShape::materialArray', an 'hkArray<CustomMeshParameter*>' */
    private const val CUSTOM_PARAMS = 0x110

    /** 'CustomMeshParameter::materialNameData', after 'version' and two 'hkArray<hkUint8>' */
    private const val MATERIAL_NAME_DATA = 0x20

    override val container = ContainerLayout(
        namedVariants = HkRootLevelContainer.NamedVariants,
        variantSize = HkRootLevelContainerNamedVariant.ByteSize,
        variant = HkRootLevelContainerNamedVariant.Variant
    )

    override val body = BodyLayout(
        systems = HkpPhysicsData.Systems,
        rigidBodies = HkpPhysicsSystem.RigidBodies,
        // 'hkpWorldObject::m_name', which the reflection extraction left without a name
        name = HkpRigidBody.Unnamed0x78,
        shape = HkpRigidBody.Collidable + HkpCdBody.Shape,
        filter = HkpRigidBody.Collidable + HkpCollidable.BroadPhaseHandle + HkpTypedBroadPhaseHandle.CollisionFilterInfo,
        transform = HkpRigidBody.Motion + HkpMotion.MotionState + HkMotionState.Transform
    )

    override val moppChild = HkpMoppBvTreeShape.Child + HkpSingleShapeContainer.ChildShape

    override val mesh = MeshLayout(
        trianglesSubparts = HkpStorageExtendedMeshShape.TrianglesSubparts,
        storage = HkpStorageExtendedMeshShape.MeshStorage,
        aabbCenter = HkpStorageExtendedMeshShape.AabbCenter,
        aabbHalfExtents = HkpStorageExtendedMeshShape.AabbHalfExtents,
        customParams = CUSTOM_PARAMS,
        subpartSize = HkpExtendedMeshShapeTrianglesSubpart.ByteSize,
        subpartTriangles = HkpExtendedMeshShapeTrianglesSubpart.NumTriangleShapes,
        subpartIndexStriding = HkpExtendedMeshShapeTrianglesSubpart.IndexStriding,
        subpartStridingType = HkpExtendedMeshShapeTrianglesSubpart.StridingType,
        subpartFlip = HkpExtendedMeshShapeTrianglesSubpart.FlipAlternateTriangles,
        subpartTransform = HkpExtendedMeshShapeTrianglesSubpart.Transform,
        vertices = HkpStorageExtendedMeshShapeMeshSubpartStorage.Vertices,
        indices8 = HkpStorageExtendedMeshShapeMeshSubpartStorage.Indices8,
        indices16 = HkpStorageExtendedMeshShapeMeshSubpartStorage.Indices16,
        indices32 = HkpStorageExtendedMeshShapeMeshSubpartStorage.Indices32,
        materialNameData = MATERIAL_NAME_DATA
    )

    override val convex = ConvexLayout(
        radius = HkpConvexVerticesShape.Radius,
        rotatedVertices = HkpConvexVerticesShape.RotatedVertices,
        numVertices = HkpConvexVerticesShape.NumVertices,
        planeEquations = HkpConvexVerticesShape.PlaneEquations,
        blockSize = HkpConvexVerticesShapeFourVectors.ByteSize,
        blockX = HkpConvexVerticesShapeFourVectors.X,
        blockY = HkpConvexVerticesShapeFourVectors.Y,
        blockZ = HkpConvexVerticesShapeFourVectors.Z
    )
}
