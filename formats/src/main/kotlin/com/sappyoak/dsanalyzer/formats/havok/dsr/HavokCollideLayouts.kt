// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateDSRHavokLayouts
//
// Source: tools/havok/dsr-reflection.tsv, extracted from DarkSoulsRemastered.exe (hk_2015.1.0-r1, 64-bit)
//
// Collide: 60 of 866 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.dsr

/**
 * `hkcdAdf`, 0x80 bytes.
 */
public object HkcdAdf {
    public const val ByteSize: Int = 0x80

    /** `float` */
    public const val Accuracy: Int = 0x0

    /** `struct` */
    public const val Domain: Int = 0x10

    /** `hkVector4` */
    public const val Origin: Int = 0x30

    /** `hkVector4` */
    public const val Scale: Int = 0x40

    /** `float[2]` */
    public const val Range: Int = 0x50

    /** `hkArray<uint32>` */
    public const val Nodes: Int = 0x58

    /** `hkArray<uint16>` */
    public const val Voxels: Int = 0x68
}

/**
 * `hkcdDynamicAabbTree`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdDynamicAabbTree`
 */
public object HkcdDynamicAabbTree {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `bool` */
    public const val ShouldDeleteTree: Int = 0x10

    /** `struct*` */
    public const val TreePtr: Int = 0x18
}

/**
 * `hkcdDynamicAabbTree::Impl`, 0x28 bytes.
 *
 * `hkcdDynamicTree::AnisotropicMetric` -> `hkcdDynamicTree::DynamicStorage` -> `hkcdDynamicTree::DefaultDynamicStorage` -> `hkcdDynamicTree::Tree` -> `hkcdDynamicAabbTree::Impl`
 */
public object HkcdDynamicAabbTreeImpl {
    public const val ByteSize: Int = 0x28

    /** `hkArray<struct>`, from `hkcdDynamicTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `uint32`, from `hkcdDynamicTree::DynamicStorage` */
    public const val FirstFree: Int = 0x10

    /** `uint32`, from `hkcdDynamicTree::Tree` */
    public const val NumLeaves: Int = 0x18

    /** `uint32`, from `hkcdDynamicTree::Tree` */
    public const val Path: Int = 0x1c

    /** `uint32`, from `hkcdDynamicTree::Tree` */
    public const val Root: Int = 0x20
}

/**
 * `hkcdDynamicTree::AnisotropicMetric`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdDynamicTreeAnisotropicMetric {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdDynamicTree::BalanceMetric`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdDynamicTreeBalanceMetric {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdDynamicTree::CentroidMetric`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdDynamicTreeCentroidMetric {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdDynamicTree::Codec18`, 0x12 bytes.
 */
public object HkcdDynamicTreeCodec18 {
    public const val ByteSize: Int = 0x12

    /** `struct` */
    public const val Aabb: Int = 0x0

    /** `uint16` */
    public const val Parent: Int = 0x10
}

/**
 * `hkcdDynamicTree::Codec32`, 0x20 bytes.
 */
public object HkcdDynamicTreeCodec32 {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val Aabb: Int = 0x0
}

/**
 * `hkcdDynamicTree::CodecInt16`, 0x20 bytes.
 */
public object HkcdDynamicTreeCodecInt16 {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val Aabb: Int = 0x0

    /** `uint32` */
    public const val Parent: Int = 0x10

    /** `uint32[2]` */
    public const val Children: Int = 0x14

    /** `uint32` */
    public const val Pad: Int = 0x1c
}

/**
 * `hkcdDynamicTree::CodecInt16::IntAabb`, 0x10 bytes.
 *
 * `hkAabb16` -> `hkcdDynamicTree::CodecInt16::IntAabb`
 */
public object HkcdDynamicTreeCodecInt16IntAabb {
    public const val ByteSize: Int = 0x10

    /** `uint16[3]`, from `hkAabb16` */
    public const val Min: Int = 0x0

    /** `uint16`, from `hkAabb16` */
    public const val Key: Int = 0x6

    /** `uint16[3]`, from `hkAabb16` */
    public const val Max: Int = 0x8

    /** `uint16`, from `hkAabb16` */
    public const val Key1: Int = 0xe
}

/**
 * `hkcdDynamicTree::CodecRaw`, 0x40 bytes.
 */
public object HkcdDynamicTreeCodecRaw {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val Aabb: Int = 0x0

    /** `hkUlong` */
    public const val Parent: Int = 0x20

    /** `hkUlong[2]` */
    public const val Children: Int = 0x28
}

/**
 * `hkcdDynamicTree::DefaultDynamicStorage`, 0x18 bytes.
 *
 * `hkcdDynamicTree::AnisotropicMetric` -> `hkcdDynamicTree::DynamicStorage` -> `hkcdDynamicTree::DefaultDynamicStorage`
 */
public object HkcdDynamicTreeDefaultDynamicStorage {
    public const val ByteSize: Int = 0x18

    /** `hkArray<struct>`, from `hkcdDynamicTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `uint32`, from `hkcdDynamicTree::DynamicStorage` */
    public const val FirstFree: Int = 0x10
}

/**
 * `hkcdDynamicTree::DynamicStorage`, 0x18 bytes.
 *
 * `hkcdDynamicTree::AnisotropicMetric` -> `hkcdDynamicTree::DynamicStorage`
 */
public object HkcdDynamicTreeDynamicStorage {
    public const val ByteSize: Int = 0x18

    /** `hkArray<struct>` */
    public const val Nodes: Int = 0x0

    /** `uint32` */
    public const val FirstFree: Int = 0x10
}

/**
 * `hkcdDynamicTree::Tree`, 0x28 bytes.
 *
 * `hkcdDynamicTree::AnisotropicMetric` -> `hkcdDynamicTree::DynamicStorage` -> `hkcdDynamicTree::DefaultDynamicStorage` -> `hkcdDynamicTree::Tree`
 */
public object HkcdDynamicTreeTree {
    public const val ByteSize: Int = 0x28

    /** `hkArray<struct>`, from `hkcdDynamicTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `uint32`, from `hkcdDynamicTree::DynamicStorage` */
    public const val FirstFree: Int = 0x10

    /** `uint32` */
    public const val NumLeaves: Int = 0x18

    /** `uint32` */
    public const val Path: Int = 0x1c

    /** `uint32` */
    public const val Root: Int = 0x20
}

/**
 * `hkcdFourAabb`, 0x60 bytes.
 */
public object HkcdFourAabb {
    public const val ByteSize: Int = 0x60

    /** `hkVector4` */
    public const val Lx: Int = 0x0

    /** `hkVector4` */
    public const val Hx: Int = 0x10

    /** `hkVector4` */
    public const val Ly: Int = 0x20

    /** `hkVector4` */
    public const val Hy: Int = 0x30

    /** `hkVector4` */
    public const val Lz: Int = 0x40

    /** `hkVector4` */
    public const val Hz: Int = 0x50
}

/**
 * `hkcdFractureEngine`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdFractureEngine`
 */
public object HkcdFractureEngine {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkcdObb`, 0x40 bytes.
 */
public object HkcdObb {
    public const val ByteSize: Int = 0x40

    /** `hkTransform` */
    public const val Transform: Int = 0x0
}

/**
 * `hkcdPlanarCsgOperand`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarCsgOperand`
 */
public object HkcdPlanarCsgOperand {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Solid: Int = 0x10

    /** `struct*` */
    public const val DanglingGeometry: Int = 0x18

    /** `void*` */
    public const val Regions: Int = 0x20
}

/**
 * `hkcdPlanarEntity`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarEntity`
 */
public object HkcdPlanarEntity {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*` */
    public const val Debugger: Int = 0x10
}

/**
 * `hkcdPlanarGeometry`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarEntity` -> `hkcdPlanarGeometry`
 */
public object HkcdPlanarGeometry {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkcdPlanarEntity` */
    public const val Debugger: Int = 0x10

    /** `struct*` */
    public const val Planes: Int = 0x18

    /** `struct*` */
    public const val Polys: Int = 0x20

    /** `void*` */
    public const val Vertices: Int = 0x28
}

/**
 * `hkcdPlanarGeometryPlanesCollection`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarGeometryPlanesCollection`
 */
public object HkcdPlanarGeometryPlanesCollection {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkVector4` */
    public const val OffsetAndScale: Int = 0x10

    /** `hkArray<struct>` */
    public const val Planes: Int = 0x20

    /** `void*` */
    public const val Cache: Int = 0x30

    /** `void*` */
    public const val CriticalAccess: Int = 0x38
}

/**
 * `hkcdPlanarGeometryPolygonCollection`, 0xd90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarGeometryPrimitives::Collection` -> `hkcdPlanarGeometryPolygonCollection`
 */
public object HkcdPlanarGeometryPolygonCollection {
    public const val ByteSize: Int = 0xd90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>`, from `hkcdPlanarGeometryPrimitives::Collection` */
    public const val Storage: Int = 0x10

    /** `uint32`, from `hkcdPlanarGeometryPrimitives::Collection` */
    public const val PrimaryBitmap: Int = 0x20

    /** `uint32[26]`, from `hkcdPlanarGeometryPrimitives::Collection` */
    public const val SecondaryBitmaps: Int = 0x24

    /** `uint32[32]`, from `hkcdPlanarGeometryPrimitives::Collection` */
    public const val FreeBlocks: Int = 0x8c
}

/**
 * `hkcdPlanarGeometryPolygonCollection::Material`, 0x8 bytes.
 */
public object HkcdPlanarGeometryPolygonCollectionMaterial {
    public const val ByteSize: Int = 0x8

    /** `int64` */
    public const val Val: Int = 0x0
}

/**
 * `hkcdPlanarGeometryPrimitives::Collection`, 0xd90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarGeometryPrimitives::Collection`
 */
public object HkcdPlanarGeometryPrimitivesCollection {
    public const val ByteSize: Int = 0xd90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val Storage: Int = 0x10

    /** `uint32` */
    public const val PrimaryBitmap: Int = 0x20

    /** `uint32[26]` */
    public const val SecondaryBitmaps: Int = 0x24

    /** `uint32[32]` */
    public const val FreeBlocks: Int = 0x8c
}

/**
 * `hkcdPlanarGeometryPrimitives::Plane`, 0x40 bytes.
 */
public object HkcdPlanarGeometryPrimitivesPlane {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val IEqn: Int = 0x0

    /** `float[4]` */
    public const val DEqn: Int = 0x20
}

/**
 * `hkcdPlanarGeometryPrimitives::Plane::Int64Vector4`, 0x20 bytes.
 */
public object HkcdPlanarGeometryPrimitivesPlaneInt64Vector4 {
    public const val ByteSize: Int = 0x20

    /** `type9[4]` */
    public const val Vec: Int = 0x0
}

/**
 * `hkcdPlanarSolid`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarEntity` -> `hkcdPlanarSolid`
 */
public object HkcdPlanarSolid {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkcdPlanarEntity` */
    public const val Debugger: Int = 0x10

    /** `struct*` */
    public const val Nodes: Int = 0x18

    /** `struct*` */
    public const val Planes: Int = 0x20

    /** `uint32` */
    public const val RootNodeId: Int = 0x28
}

/**
 * `hkcdPlanarSolid::Node`, 0x28 bytes.
 */
public object HkcdPlanarSolidNode {
    public const val ByteSize: Int = 0x28

    /** `uint32` */
    public const val Parent: Int = 0x0

    /** `uint32` */
    public const val Left: Int = 0x4

    /** `uint32` */
    public const val Right: Int = 0x8

    /** `uint32` */
    public const val NextFreeNodeId: Int = 0xc

    /** `uint32` */
    public const val PlaneId: Int = 0x10

    /** `uint32` */
    public const val AabbId: Int = 0x14

    /** `struct` */
    public const val Material: Int = 0x18

    /** `uint32` */
    public const val Data: Int = 0x20

    /** `uint16` */
    public const val Type: Int = 0x24

    /** `uint16` */
    public const val Flags: Int = 0x26
}

/**
 * `hkcdPlanarSolid::NodeStorage`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdPlanarSolid::NodeStorage`
 */
public object HkcdPlanarSolidNodeStorage {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Storage: Int = 0x10

    /** `hkArray<struct>` */
    public const val Aabbs: Int = 0x20

    /** `uint32` */
    public const val FirstFreeNodeId: Int = 0x30
}

/**
 * `hkcdShape`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape`
 */
public object HkcdShape {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8` */
    public const val Type: Int = 0x10

    /** `enum:uint8` */
    public const val DispatchType: Int = 0x11

    /** `uint8` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8` */
    public const val ShapeInfoCodecType: Int = 0x13
}

/**
 * `hkcdShapeDispatchType`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdShapeDispatchType {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdShapeInfoCodecType`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdShapeInfoCodecType {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdShapeType`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdShapeType {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdSimdTree`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkcdSimdTree`
 */
public object HkcdSimdTree {
    public const val ByteSize: Int = 0x18

    /** `hkArray<struct>` */
    public const val Nodes: Int = 0x8
}

/**
 * `hkcdSimdTree::Node`, 0x70 bytes.
 *
 * `hkcdFourAabb` -> `hkcdSimdTree::Node`
 */
public object HkcdSimdTreeNode {
    public const val ByteSize: Int = 0x70

    /** `hkVector4`, from `hkcdFourAabb` */
    public const val Lx: Int = 0x0

    /** `hkVector4`, from `hkcdFourAabb` */
    public const val Hx: Int = 0x10

    /** `hkVector4`, from `hkcdFourAabb` */
    public const val Ly: Int = 0x20

    /** `hkVector4`, from `hkcdFourAabb` */
    public const val Hy: Int = 0x30

    /** `hkVector4`, from `hkcdFourAabb` */
    public const val Lz: Int = 0x40

    /** `hkVector4`, from `hkcdFourAabb` */
    public const val Hz: Int = 0x50

    /** `uint32[4]` */
    public const val Data: Int = 0x60
}

/**
 * `hkcdStaticAabbTree`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdStaticAabbTree`
 */
public object HkcdStaticAabbTree {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `bool` */
    public const val ShouldDeleteTree: Int = 0x10

    /** `struct*` */
    public const val TreePtr: Int = 0x18
}

/**
 * `hkcdStaticAabbTree::Impl`, 0x30 bytes.
 *
 * `hkcdStaticTree::DynamicStorage` -> `hkcdStaticTree::Tree` -> `hkcdStaticAabbTree::Impl`
 */
public object HkcdStaticAabbTreeImpl {
    public const val ByteSize: Int = 0x30

    /** `hkArray<struct>`, from `hkcdStaticTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `struct`, from `hkcdStaticTree::Tree` */
    public const val Domain: Int = 0x10
}

/**
 * `hkcdStaticMeshTree`, 0xa0 bytes.
 *
 * `hkcdStaticTree::DynamicStorage` -> `hkcdStaticTree::Tree` -> `hkcdStaticMeshTreeBase` -> `hkcdStaticMeshTree`
 */
public object HkcdStaticMeshTree {
    public const val ByteSize: Int = 0xa0

    /** `hkArray<struct>`, from `hkcdStaticTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `struct`, from `hkcdStaticTree::Tree` */
    public const val Domain: Int = 0x10

    /** `int32`, from `hkcdStaticMeshTreeBase` */
    public const val NumPrimitiveKeys: Int = 0x30

    /** `int32`, from `hkcdStaticMeshTreeBase` */
    public const val BitsPerKey: Int = 0x34

    /** `uint32`, from `hkcdStaticMeshTreeBase` */
    public const val MaxKeyValue: Int = 0x38

    /** `uint8`, from `hkcdStaticMeshTreeBase` */
    public const val PrimitiveStoresIsFlatConvex: Int = 0x3c

    /** `hkArray<struct>`, from `hkcdStaticMeshTreeBase` */
    public const val Sections: Int = 0x40

    /** `hkArray<struct>`, from `hkcdStaticMeshTreeBase` */
    public const val Primitives: Int = 0x50

    /** `hkArray<uint16>`, from `hkcdStaticMeshTreeBase` */
    public const val SharedVerticesIndex: Int = 0x60

    /** `hkArray<uint32>` */
    public const val PackedVertices: Int = 0x70

    /** `hkArray<int64>` */
    public const val SharedVertices: Int = 0x80

    /** `hkArray<struct>` */
    public const val PrimitiveDataRuns: Int = 0x90
}

/**
 * `hkcdStaticMeshTreeBase`, 0x70 bytes.
 *
 * `hkcdStaticTree::DynamicStorage` -> `hkcdStaticTree::Tree` -> `hkcdStaticMeshTreeBase`
 */
public object HkcdStaticMeshTreeBase {
    public const val ByteSize: Int = 0x70

    /** `hkArray<struct>`, from `hkcdStaticTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `struct`, from `hkcdStaticTree::Tree` */
    public const val Domain: Int = 0x10

    /** `int32` */
    public const val NumPrimitiveKeys: Int = 0x30

    /** `int32` */
    public const val BitsPerKey: Int = 0x34

    /** `uint32` */
    public const val MaxKeyValue: Int = 0x38

    /** `uint8` */
    public const val PrimitiveStoresIsFlatConvex: Int = 0x3c

    /** `hkArray<struct>` */
    public const val Sections: Int = 0x40

    /** `hkArray<struct>` */
    public const val Primitives: Int = 0x50

    /** `hkArray<uint16>` */
    public const val SharedVerticesIndex: Int = 0x60
}

/**
 * `hkcdStaticMeshTreeBase::Connectivity`, 0x30 bytes.
 */
public object HkcdStaticMeshTreeBaseConnectivity {
    public const val ByteSize: Int = 0x30

    /** `hkArray<struct>` */
    public const val Headers: Int = 0x0

    /** `hkArray<uint8>` */
    public const val LocalLinks: Int = 0x10

    /** `hkArray<uint32>` */
    public const val GlobalLinks: Int = 0x20
}

/**
 * `hkcdStaticMeshTreeBase::Connectivity::SectionHeader`, 0x8 bytes.
 */
public object HkcdStaticMeshTreeBaseConnectivitySectionHeader {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val BaseLocal: Int = 0x0

    /** `uint32` */
    public const val BaseGlobal: Int = 0x4
}

/**
 * `hkcdStaticMeshTreeBase::Edge`, 0x4 bytes.
 */
public object HkcdStaticMeshTreeBaseEdge {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val KeyAndIndex: Int = 0x0
}

/**
 * `hkcdStaticMeshTreeBase::Links`, 0xc bytes.
 */
public object HkcdStaticMeshTreeBaseLinks {
    public const val ByteSize: Int = 0xc

    /** `struct[3]` */
    public const val Links: Int = 0x0
}

/**
 * `hkcdStaticMeshTreeBase::Primitive`, 0x4 bytes.
 */
public object HkcdStaticMeshTreeBasePrimitive {
    public const val ByteSize: Int = 0x4

    /** `uint8[4]` */
    public const val Indices: Int = 0x0
}

/**
 * `hkcdStaticMeshTreeBase::PrimitiveDataRunBase`, 0x8 bytes.
 */
public object HkcdStaticMeshTreeBasePrimitiveDataRunBase {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val Value: Int = 0x0

    /** `uint8` */
    public const val Index: Int = 0x4

    /** `uint8` */
    public const val Count: Int = 0x5
}

/**
 * `hkcdStaticMeshTreeBase::Section`, 0x60 bytes.
 *
 * `hkcdStaticTree::DynamicStorage` -> `hkcdStaticTree::Tree` -> `hkcdStaticMeshTreeBase::Section`
 */
public object HkcdStaticMeshTreeBaseSection {
    public const val ByteSize: Int = 0x60

    /** `hkArray<struct>`, from `hkcdStaticTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `struct`, from `hkcdStaticTree::Tree` */
    public const val Domain: Int = 0x10

    /** `float[6]` */
    public const val CodecParms: Int = 0x30

    /** `uint32` */
    public const val FirstPackedVertex: Int = 0x48

    /** `struct` */
    public const val SharedVertices: Int = 0x4c

    /** `struct` */
    public const val Primitives: Int = 0x50

    /** `struct` */
    public const val DataRuns: Int = 0x54

    /** `uint8` */
    public const val NumPackedVertices: Int = 0x58

    /** `uint8` */
    public const val NumSharedIndices: Int = 0x59

    /** `uint16` */
    public const val LeafIndex: Int = 0x5a

    /** `uint8` */
    public const val Page: Int = 0x5c

    /** `uint8` */
    public const val Flags: Int = 0x5d

    /** `uint8` */
    public const val LayerData: Int = 0x5e

    /** `uint8` */
    public const val UnusedData: Int = 0x5f
}

/**
 * `hkcdStaticMeshTreeBase::Section::DataRuns`, 0x4 bytes.
 */
public object HkcdStaticMeshTreeBaseSectionDataRuns {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val Data: Int = 0x0
}

/**
 * `hkcdStaticMeshTreeBase::Section::Primitives`, 0x4 bytes.
 */
public object HkcdStaticMeshTreeBaseSectionPrimitives {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val Data: Int = 0x0
}

/**
 * `hkcdStaticMeshTreeBase::Section::SharedVertices`, 0x4 bytes.
 */
public object HkcdStaticMeshTreeBaseSectionSharedVertices {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val Data: Int = 0x0
}

/**
 * `hkcdStaticMeshTreeCommonConfig`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkcdStaticMeshTreeCommonConfig {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkcdStaticMeshTreeDefaultDataRun`, 0x4 bytes.
 *
 * `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` -> `hkcdStaticMeshTreeDefaultDataRun`
 */
public object HkcdStaticMeshTreeDefaultDataRun {
    public const val ByteSize: Int = 0x4

    /** `uint32`, from `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` */
    public const val Value: Int = 0x0

    /** `uint8`, from `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` */
    public const val Index: Int = 0x4

    /** `uint8`, from `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` */
    public const val Count: Int = 0x5
}

/**
 * `hkcdStaticPvs`, 0x70 bytes.
 */
public object HkcdStaticPvs {
    public const val ByteSize: Int = 0x70

    /** `struct` */
    public const val Cells: Int = 0x0

    /** `int32` */
    public const val BytesPerCells: Int = 0x30

    /** `int32` */
    public const val CellsPerBlock: Int = 0x34

    /** `hkArray<uint8>` */
    public const val Pvs: Int = 0x38

    /** `hkArray<uint16>` */
    public const val Map: Int = 0x48

    /** `hkArray<struct>` */
    public const val Blocks: Int = 0x58
}

/**
 * `hkcdStaticPvs::BlockHeader`, 0x8 bytes.
 */
public object HkcdStaticPvsBlockHeader {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val Offset: Int = 0x0

    /** `uint32` */
    public const val Length: Int = 0x4
}

/**
 * `hkcdStaticTree::Codec3Axis`, 0x3 bytes.
 */
public object HkcdStaticTreeCodec3Axis {
    public const val ByteSize: Int = 0x3

    /** `uint8[3]` */
    public const val Xyz: Int = 0x0
}

/**
 * `hkcdStaticTree::Codec3Axis4`, 0x4 bytes.
 *
 * `hkcdStaticTree::Codec3Axis` -> `hkcdStaticTree::Codec3Axis4`
 */
public object HkcdStaticTreeCodec3Axis4 {
    public const val ByteSize: Int = 0x4

    /** `uint8[3]`, from `hkcdStaticTree::Codec3Axis` */
    public const val Xyz: Int = 0x0

    /** `uint8` */
    public const val Data: Int = 0x3
}

/**
 * `hkcdStaticTree::Codec3Axis5`, 0x5 bytes.
 *
 * `hkcdStaticTree::Codec3Axis` -> `hkcdStaticTree::Codec3Axis5`
 */
public object HkcdStaticTreeCodec3Axis5 {
    public const val ByteSize: Int = 0x5

    /** `uint8[3]`, from `hkcdStaticTree::Codec3Axis` */
    public const val Xyz: Int = 0x0

    /** `uint8` */
    public const val HiData: Int = 0x3

    /** `uint8` */
    public const val LoData: Int = 0x4
}

/**
 * `hkcdStaticTree::Codec3Axis6`, 0x6 bytes.
 *
 * `hkcdStaticTree::Codec3Axis` -> `hkcdStaticTree::Codec3Axis6`
 */
public object HkcdStaticTreeCodec3Axis6 {
    public const val ByteSize: Int = 0x6

    /** `uint8[3]`, from `hkcdStaticTree::Codec3Axis` */
    public const val Xyz: Int = 0x0

    /** `uint8` */
    public const val HiData: Int = 0x3

    /** `uint16` */
    public const val LoData: Int = 0x4
}

/**
 * `hkcdStaticTree::CodecRaw`, 0x20 bytes.
 */
public object HkcdStaticTreeCodecRaw {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val Aabb: Int = 0x0
}

/**
 * `hkcdStaticTree::DynamicStorage`, 0x10 bytes.
 */
public object HkcdStaticTreeDynamicStorage {
    public const val ByteSize: Int = 0x10

    /** `hkArray<struct>` */
    public const val Nodes: Int = 0x0
}

/**
 * `hkcdStaticTree::Tree`, 0x30 bytes.
 *
 * `hkcdStaticTree::DynamicStorage` -> `hkcdStaticTree::Tree`
 */
public object HkcdStaticTreeTree {
    public const val ByteSize: Int = 0x30

    /** `hkArray<struct>`, from `hkcdStaticTree::DynamicStorage` */
    public const val Nodes: Int = 0x0

    /** `struct` */
    public const val Domain: Int = 0x10
}
