package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.dsr.CustomMeshParameter
import com.sappyoak.dsanalyzer.formats.havok.dsr.CustomParamStorageExtendedMeshShape
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkFourTransposedPointsf
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkMotionState
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkcdShape
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpCdBody
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpCollidable
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpConvexVerticesShape
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpExtendedMeshShapeTrianglesSubpart
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpMoppBvTreeShape
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpMotion
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpRigidBody
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpSingleShapeContainer
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpStorageExtendedMeshShape
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpStorageExtendedMeshShapeMeshSubpartStorage
import com.sappyoak.dsanalyzer.formats.havok.dsr.HkpTypedBroadPhaseHandle

private const val VECTOR4_BYTES = 16

public object DsrRuntimeLayout : RuntimeHavokLayout {
    override val body: RuntimeBodyLayout = RuntimeBodyLayout(
        name = HkpRigidBody.Name,
        shape = HkpRigidBody.Collidable + HkpCdBody.Shape,
        filter = HkpRigidBody.Collidable + HkpCollidable.BroadPhaseHandle +
                HkpTypedBroadPhaseHandle.CollisionFilterInfo,
        transform = HkpRigidBody.Motion + HkpMotion.MotionState + HkMotionState.Transform
    )

    override val moppChild: Int = HkpMoppBvTreeShape.Child + HkpSingleShapeContainer.ChildShape

    override val shapeType: Int = HkcdShape.Type

    override val mesh: MeshLayout = MeshLayout(
        trianglesSubparts = HkpStorageExtendedMeshShape.TrianglesSubparts,
        storage = HkpStorageExtendedMeshShape.MeshStorage,
        aabbCenter = HkpStorageExtendedMeshShape.AabbCenter,
        aabbHalfExtents = HkpStorageExtendedMeshShape.AabbHalfExtents,
        customParams = CustomParamStorageExtendedMeshShape.MaterialArray,
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
        materialNameData = CustomMeshParameter.MaterialNameData
    )

    override val convex: ConvexLayout = ConvexLayout(
        radius = HkpConvexVerticesShape.Radius,
        rotatedVertices = HkpConvexVerticesShape.RotatedVertices,
        numVertices = HkpConvexVerticesShape.NumVertices,
        planeEquations = HkpConvexVerticesShape.PlaneEquations,
        blockSize = HkFourTransposedPointsf.ByteSize,
        blockX = 0,
        blockY = VECTOR4_BYTES,
        blockZ = 2 * VECTOR4_BYTES
    )
}
