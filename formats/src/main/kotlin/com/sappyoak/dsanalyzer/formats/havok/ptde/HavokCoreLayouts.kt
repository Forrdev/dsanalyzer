// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generatePTDEHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Core: 87 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.ptde

/**
 * `hkAabb`, 0x20 bytes.
 */
public object HkAabb {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val Min: Int = 0x0

    /** `hkVector4` */
    public const val Max: Int = 0x10
}

/**
 * `hkAabbHalf`, 0x10 bytes.
 */
public object HkAabbHalf {
    public const val ByteSize: Int = 0x10

    /** `uint16[6]` */
    public const val Data: Int = 0x0

    /** `uint16[2]` */
    public const val Extras: Int = 0xc
}

/**
 * `hkAabbUint32`, 0x20 bytes.
 */
public object HkAabbUint32 {
    public const val ByteSize: Int = 0x20

    /** `uint32[3]` */
    public const val Min: Int = 0x0

    /** `uint8[3]` */
    public const val ExpansionMin: Int = 0xc

    /** `uint8` */
    public const val ExpansionShift: Int = 0xf

    /** `uint32[3]` */
    public const val Max: Int = 0x10

    /** `uint8[3]` */
    public const val ExpansionMax: Int = 0x1c

    /** `uint8` */
    public const val ShapeKeyByte: Int = 0x1f
}

/**
 * `hkAlignSceneToNodeOptions`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkAlignSceneToNodeOptions`
 */
public object HkAlignSceneToNodeOptions {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `bool` */
    public const val Invert: Int = 0x8

    /** `bool` */
    public const val TransformPositionX: Int = 0x9

    /** `bool` */
    public const val TransformPositionY: Int = 0xa

    /** `bool` */
    public const val TransformPositionZ: Int = 0xb

    /** `bool` */
    public const val TransformRotation: Int = 0xc

    /** `bool` */
    public const val TransformScale: Int = 0xd

    /** `bool` */
    public const val TransformSkew: Int = 0xe

    /** `int32` */
    public const val Keyframe: Int = 0x10

    /** `hkStringPtr` */
    public const val NodeName: Int = 0x14
}

/**
 * `hkArrayStreamWriter`, 0x14 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkArrayStreamWriter {
    public const val ByteSize: Int = 0x14
}

/**
 * `hkArrayTypeAttribute`, 0x1 bytes.
 */
public object HkArrayTypeAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkBaseObject`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkBaseObject {
    public const val ByteSize: Int = 0x4
}

/**
 * `hkBitField`, 0x10 bytes.
 */
public object HkBitField {
    public const val ByteSize: Int = 0x10

    /** `hkArray<uint32>` */
    public const val Words: Int = 0x0

    /** `int32` */
    public const val NumBits: Int = 0xc
}

/**
 * `hkBufferedStreamWriter`, 0x1c bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkBufferedStreamWriter {
    public const val ByteSize: Int = 0x1c
}

/**
 * `hkClass`, 0x30 bytes.
 */
public object HkClass {
    public const val ByteSize: Int = 0x30

    /** `char*` */
    public const val Unnamed0x0: Int = 0x0

    /** `struct*` */
    public const val Parent: Int = 0x4

    /** `int32` */
    public const val ObjectSize: Int = 0x8

    /** `int32` */
    public const val NumImplementedInterfaces: Int = 0xc

    /** `hkSimpleArray<struct>` */
    public const val DeclaredEnums: Int = 0x10

    /** `hkSimpleArray<struct>` */
    public const val DeclaredMembers: Int = 0x18

    /** `bool` */
    public const val Unnamed0x20: Int = 0x20

    /** `void*` */
    public const val Defaults: Int = 0x20

    /** `struct*` */
    public const val Attributes: Int = 0x24

    /** `flags:uint32` */
    public const val Flags: Int = 0x28

    /** `int32` */
    public const val DescribedVersion: Int = 0x2c
}

/**
 * `hkClassEnum`, 0xc bytes.
 */
public object HkClassEnum {
    public const val ByteSize: Int = 0xc

    /** `char*` */
    public const val Unnamed0x0: Int = 0x0

    /** `hkSimpleArray<struct>` */
    public const val Items: Int = 0x4

    /** `struct*` */
    public const val Attributes: Int = 0xc

    /** `flags:uint32` */
    public const val Flags: Int = 0x10
}

/**
 * `hkClassEnumItem`, 0x8 bytes.
 */
public object HkClassEnumItem {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val Value: Int = 0x0

    /** `char*` */
    public const val Unnamed0x4: Int = 0x4
}

/**
 * `hkClassMember`, 0x18 bytes.
 */
public object HkClassMember {
    public const val ByteSize: Int = 0x18

    /** `char*` */
    public const val Unnamed0x0: Int = 0x0

    /** `struct*` */
    public const val Class: Int = 0x4

    /** `struct*` */
    public const val Enum: Int = 0x8

    /** `enum:uint8` */
    public const val Type: Int = 0xc

    /** `enum:uint8` */
    public const val Subtype: Int = 0xd

    /** `int16` */
    public const val CArraySize: Int = 0xe

    /** `flags:uint16` */
    public const val Flags: Int = 0x10

    /** `uint16` */
    public const val Offset: Int = 0x12

    /** `struct*` */
    public const val Attributes: Int = 0x14
}

/**
 * `hkColor`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkColor {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkContactPoint`, 0x20 bytes.
 */
public object HkContactPoint {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val Position: Int = 0x0

    /** `hkVector4` */
    public const val SeparatingNormal: Int = 0x10
}

/**
 * `hkContactPointMaterial`, 0x8 bytes.
 */
public object HkContactPointMaterial {
    public const val ByteSize: Int = 0x8

    /** `hkUlong` */
    public const val UserData: Int = 0x0

    /** `uint8` */
    public const val Friction: Int = 0x4

    /** `uint8` */
    public const val Restitution: Int = 0x5

    /** `uint8` */
    public const val MaxImpulse: Int = 0x6

    /** `uint8` */
    public const val Flags: Int = 0x7
}

/**
 * `hkCustomAttributes`, 0x8 bytes.
 */
public object HkCustomAttributes {
    public const val ByteSize: Int = 0x8

    /** `hkSimpleArray<struct>` */
    public const val Attributes: Int = 0x0
}

/**
 * `hkCustomAttributesAttribute`, 0xc bytes.
 */
public object HkCustomAttributesAttribute {
    public const val ByteSize: Int = 0xc

    /** `char*` */
    public const val Unnamed0x0: Int = 0x0

    /** `homogeneousArray` */
    public const val Value: Int = 0x4
}

/**
 * `hkDataObjectTypeAttribute`, 0x4 bytes.
 */
public object HkDataObjectTypeAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val TypeName: Int = 0x0
}

/**
 * `hkDefaultMemoryTracker`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkDefaultMemoryTracker`
 */
public object HkDefaultMemoryTracker {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkDescriptionAttribute`, 0x4 bytes.
 */
public object HkDescriptionAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val String: Int = 0x0
}

/**
 * `hkDocumentationAttribute`, 0x4 bytes.
 */
public object HkDocumentationAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val DocsSectionTag: Int = 0x0
}

/**
 * `hkGeometry`, 0x18 bytes.
 */
public object HkGeometry {
    public const val ByteSize: Int = 0x18

    /** `hkArray<hkVector4>` */
    public const val Vertices: Int = 0x0

    /** `hkArray<struct>` */
    public const val Triangles: Int = 0xc
}

/**
 * `hkGeometryTriangle`, 0x10 bytes.
 */
public object HkGeometryTriangle {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val A: Int = 0x0

    /** `int32` */
    public const val B: Int = 0x4

    /** `int32` */
    public const val Unnamed0x8: Int = 0x8

    /** `int32` */
    public const val Material: Int = 0xc
}

/**
 * `hkGizmoAttribute`, 0xc bytes.
 */
public object HkGizmoAttribute {
    public const val ByteSize: Int = 0xc

    /** `bool` */
    public const val Visible: Int = 0x0

    /** `char*` */
    public const val Label: Int = 0x4

    /** `enum:int8` */
    public const val Type: Int = 0x8
}

/**
 * `hkHalf8`, 0x10 bytes.
 */
public object HkHalf8 {
    public const val ByteSize: Int = 0x10

    /** `hkHalf[8]` */
    public const val Quad: Int = 0x0
}

/**
 * `hkIndexedTransformSet`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkIndexedTransformSet`
 */
public object HkIndexedTransformSet {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkMatrix4>` */
    public const val Matrices: Int = 0x8

    /** `hkArray<hkMatrix4>` */
    public const val InverseMatrices: Int = 0x14

    /** `hkArray<int16>` */
    public const val MatricesOrder: Int = 0x20

    /** `hkArray<hkStringPtr>` */
    public const val MatricesNames: Int = 0x2c

    /** `hkArray<struct>` */
    public const val IndexMappings: Int = 0x38

    /** `bool` */
    public const val AllMatricesAreAffine: Int = 0x44
}

/**
 * `hkLinkAttribute`, 0x1 bytes.
 */
public object HkLinkAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkLocalFrame`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrame`
 */
public object HkLocalFrame {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkLocalFrameGroup`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrameGroup`
 */
public object HkLocalFrameGroup {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Unnamed0x8: Int = 0x8
}

/**
 * `hkMemoryMeshBody`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshBody` -> `hkMemoryMeshBody`
 */
public object HkMemoryMeshBody {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkMatrix4` */
    public const val Transform: Int = 0x10

    /** `struct*` */
    public const val TransformSet: Int = 0x50

    /** `struct*` */
    public const val Shape: Int = 0x54

    /** `hkArray<ptr>` */
    public const val VertexBuffers: Int = 0x58

    /** `hkStringPtr` */
    public const val Unnamed0x64: Int = 0x64
}

/**
 * `hkMemoryMeshMaterial`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshMaterial` -> `hkMemoryMeshMaterial`
 */
public object HkMemoryMeshMaterial {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val MaterialName: Int = 0x8

    /** `hkArray<ptr>` */
    public const val Textures: Int = 0xc
}

/**
 * `hkMemoryMeshShape`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkMemoryMeshShape`
 */
public object HkMemoryMeshShape {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val Sections: Int = 0x8

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x14

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x20

    /** `hkStringPtr` */
    public const val Unnamed0x2c: Int = 0x2c
}

/**
 * `hkMemoryMeshTexture`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshTexture` -> `hkMemoryMeshTexture`
 */
public object HkMemoryMeshTexture {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Filename: Int = 0x8

    /** `hkArray<uint8>` */
    public const val Data: Int = 0xc

    /** `enum:int8` */
    public const val Format: Int = 0x18

    /** `bool` */
    public const val HasMipMaps: Int = 0x19

    /** `enum:int8` */
    public const val FilterMode: Int = 0x1a

    /** `enum:int8` */
    public const val UsageHint: Int = 0x1b

    /** `int32` */
    public const val TextureCoordChannel: Int = 0x1c
}

/**
 * `hkMemoryMeshVertexBuffer`, 0x1a8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer` -> `hkMemoryMeshVertexBuffer`
 */
public object HkMemoryMeshVertexBuffer {
    public const val ByteSize: Int = 0x1a8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct` */
    public const val Format: Int = 0x8

    /** `int32[32]` */
    public const val ElementOffsets: Int = 0x10c

    /** `hkArray<uint8>` */
    public const val Memory: Int = 0x18c

    /** `int32` */
    public const val VertexStride: Int = 0x198

    /** `bool` */
    public const val Locked: Int = 0x19c

    /** `int32` */
    public const val NumVertices: Int = 0x1a0

    /** `bool` */
    public const val IsBigEndian: Int = 0x1a4

    /** `bool` */
    public const val IsSharable: Int = 0x1a5
}

/**
 * `hkMemoryResourceContainer`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceContainer` -> `hkMemoryResourceContainer`
 */
public object HkMemoryResourceContainer {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Unnamed0x8: Int = 0x8

    /** `struct*` */
    public const val Parent: Int = 0xc

    /** `hkArray<ptr>` */
    public const val ResourceHandles: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Children: Int = 0x1c
}

/**
 * `hkMemoryResourceHandle`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceHandle` -> `hkMemoryResourceHandle`
 */
public object HkMemoryResourceHandle {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val Variant: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0xc: Int = 0xc

    /** `hkArray<struct>` */
    public const val References: Int = 0x10
}

/**
 * `hkMemoryResourceHandleExternalLink`, 0x8 bytes.
 */
public object HkMemoryResourceHandleExternalLink {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val MemberName: Int = 0x0

    /** `hkStringPtr` */
    public const val ExternalId: Int = 0x4
}

/**
 * `hkMemoryTrackerAttribute`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkMemoryTrackerAttribute {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkMeshBody`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshBody`
 */
public object HkMeshBody {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkMeshBoneIndexMapping`, 0xc bytes.
 */
public object HkMeshBoneIndexMapping {
    public const val ByteSize: Int = 0xc

    /** `hkArray<int16>` */
    public const val Mapping: Int = 0x0
}

/**
 * `hkMeshMaterial`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshMaterial`
 */
public object HkMeshMaterial {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkMeshSection`, 0x28 bytes.
 */
public object HkMeshSection {
    public const val ByteSize: Int = 0x28

    /** `enum:uint8` */
    public const val PrimitiveType: Int = 0x0

    /** `int32` */
    public const val NumPrimitives: Int = 0x4

    /** `int32` */
    public const val NumIndices: Int = 0x8

    /** `int32` */
    public const val VertexStartIndex: Int = 0xc

    /** `int32` */
    public const val TransformIndex: Int = 0x10

    /** `enum:uint8` */
    public const val IndexType: Int = 0x14

    /** `void*` */
    public const val Indices: Int = 0x18

    /** `struct*` */
    public const val VertexBuffer: Int = 0x1c

    /** `struct*` */
    public const val Material: Int = 0x20

    /** `int32` */
    public const val SectionIndex: Int = 0x24
}

/**
 * `hkMeshSectionCinfo`, 0x20 bytes.
 */
public object HkMeshSectionCinfo {
    public const val ByteSize: Int = 0x20

    /** `struct*` */
    public const val VertexBuffer: Int = 0x0

    /** `struct*` */
    public const val Material: Int = 0x4

    /** `enum:uint8` */
    public const val PrimitiveType: Int = 0x8

    /** `int32` */
    public const val NumPrimitives: Int = 0xc

    /** `enum:uint8` */
    public const val IndexType: Int = 0x10

    /** `void*` */
    public const val Indices: Int = 0x14

    /** `int32` */
    public const val VertexStartIndex: Int = 0x18

    /** `int32` */
    public const val TransformIndex: Int = 0x1c
}

/**
 * `hkMeshShape`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape`
 */
public object HkMeshShape {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkMeshTexture`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshTexture`
 */
public object HkMeshTexture {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkMeshVertexBuffer`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer`
 */
public object HkMeshVertexBuffer {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkModelerNodeTypeAttribute`, 0x1 bytes.
 */
public object HkModelerNodeTypeAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkMonitorStreamColorTable`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMonitorStreamColorTable`
 */
public object HkMonitorStreamColorTable {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val ColorPairs: Int = 0x8

    /** `uint32` */
    public const val DefaultColor: Int = 0x14
}

/**
 * `hkMonitorStreamColorTableColorPair`, 0x8 bytes.
 */
public object HkMonitorStreamColorTableColorPair {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val ColorName: Int = 0x0

    /** `enum:uint32` */
    public const val Color: Int = 0x4
}

/**
 * `hkMonitorStreamFrameInfo`, 0x24 bytes.
 */
public object HkMonitorStreamFrameInfo {
    public const val ByteSize: Int = 0x24

    /** `hkStringPtr` */
    public const val Heading: Int = 0x0

    /** `int32` */
    public const val IndexOfTimer0: Int = 0x4

    /** `int32` */
    public const val IndexOfTimer1: Int = 0x8

    /** `enum:uint32` */
    public const val AbsoluteTimeCounter: Int = 0xc

    /** `float` */
    public const val TimerFactor0: Int = 0x10

    /** `float` */
    public const val TimerFactor1: Int = 0x14

    /** `int32` */
    public const val ThreadId: Int = 0x18

    /** `int32` */
    public const val FrameStreamStart: Int = 0x1c

    /** `int32` */
    public const val FrameStreamEnd: Int = 0x20
}

/**
 * `hkMonitorStreamStringMap`, 0xc bytes.
 */
public object HkMonitorStreamStringMap {
    public const val ByteSize: Int = 0xc

    /** `hkArray<struct>` */
    public const val Map: Int = 0x0
}

/**
 * `hkMonitorStreamStringMapStringMap`, 0x10 bytes.
 */
public object HkMonitorStreamStringMapStringMap {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val Id: Int = 0x0

    /** `hkStringPtr` */
    public const val String: Int = 0x8
}

/**
 * `hkMoppBvTreeShapeBase`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvTreeShape` -> `hkMoppBvTreeShapeBase`
 */
public object HkMoppBvTreeShapeBase {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val BvTreeType: Int = 0x10

    /** `struct*` */
    public const val Code: Int = 0x14

    /** `void*` */
    public const val MoppData: Int = 0x18

    /** `uint32` */
    public const val MoppDataSize: Int = 0x1c

    /** `hkVector4` */
    public const val CodeInfoCopy: Int = 0x20
}

/**
 * `hkMotionState`, 0xb0 bytes.
 */
public object HkMotionState {
    public const val ByteSize: Int = 0xb0

    /** `hkTransform` */
    public const val Transform: Int = 0x0

    /** `struct` */
    public const val SweptTransform: Int = 0x40

    /** `hkVector4` */
    public const val DeltaAngle: Int = 0x90

    /** `float` */
    public const val ObjectRadius: Int = 0xa0

    /** `hkHalf` */
    public const val LinearDamping: Int = 0xa4

    /** `hkHalf` */
    public const val AngularDamping: Int = 0xa6

    /** `hkHalf` */
    public const val TimeFactor: Int = 0xa8

    /** `uint8` */
    public const val MaxLinearVelocity: Int = 0xaa

    /** `uint8` */
    public const val MaxAngularVelocity: Int = 0xab

    /** `uint8` */
    public const val DeactivationClass: Int = 0xac
}

/**
 * `hkMultiThreadCheck`, 0xc bytes.
 */
public object HkMultiThreadCheck {
    public const val ByteSize: Int = 0xc

    /** `uint32` */
    public const val ThreadId: Int = 0x0

    /** `int32` */
    public const val StackTraceId: Int = 0x4

    /** `uint16` */
    public const val MarkCount: Int = 0x8

    /** `uint16` */
    public const val MarkBitStack: Int = 0xa
}

/**
 * `hkMultipleVertexBuffer`, 0x144 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer` -> `hkMultipleVertexBuffer`
 */
public object HkMultipleVertexBuffer {
    public const val ByteSize: Int = 0x144

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct` */
    public const val VertexFormat: Int = 0x8

    /** `hkArray<struct>` */
    public const val LockedElements: Int = 0x10c

    /** `struct*` */
    public const val LockedBuffer: Int = 0x118

    /** `hkArray<struct>` */
    public const val ElementInfos: Int = 0x11c

    /** `hkArray<struct>` */
    public const val VertexBufferInfos: Int = 0x128

    /** `int32` */
    public const val NumVertices: Int = 0x134

    /** `bool` */
    public const val IsLocked: Int = 0x138

    /** `uint32` */
    public const val UpdateCount: Int = 0x13c

    /** `bool` */
    public const val WriteLock: Int = 0x140

    /** `bool` */
    public const val IsSharable: Int = 0x141

    /** `bool` */
    public const val ConstructionComplete: Int = 0x142
}

/**
 * `hkMultipleVertexBufferElementInfo`, 0x2 bytes.
 */
public object HkMultipleVertexBufferElementInfo {
    public const val ByteSize: Int = 0x2

    /** `uint8` */
    public const val VertexBufferIndex: Int = 0x0

    /** `uint8` */
    public const val ElementIndex: Int = 0x1
}

/**
 * `hkMultipleVertexBufferLockedElement`, 0x7 bytes.
 */
public object HkMultipleVertexBufferLockedElement {
    public const val ByteSize: Int = 0x7

    /** `uint8` */
    public const val VertexBufferIndex: Int = 0x0

    /** `uint8` */
    public const val ElementIndex: Int = 0x1

    /** `uint8` */
    public const val LockedBufferIndex: Int = 0x2

    /** `uint8` */
    public const val VertexFormatIndex: Int = 0x3

    /** `uint8` */
    public const val LockFlags: Int = 0x4

    /** `uint8` */
    public const val OutputBufferIndex: Int = 0x5

    /** `int8` */
    public const val EmulatedIndex: Int = 0x6
}

/**
 * `hkMultipleVertexBufferVertexBufferInfo`, 0xc bytes.
 */
public object HkMultipleVertexBufferVertexBufferInfo {
    public const val ByteSize: Int = 0xc

    /** `struct*` */
    public const val VertexBuffer: Int = 0x0

    /** `void*` */
    public const val LockedVertices: Int = 0x4

    /** `bool` */
    public const val IsLocked: Int = 0x8
}

/**
 * `hkOstream`, 0xc bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkOstream {
    public const val ByteSize: Int = 0xc
}

/**
 * `hkPackedVector3`, 0x8 bytes.
 */
public object HkPackedVector3 {
    public const val ByteSize: Int = 0x8

    /** `int16[4]` */
    public const val Values: Int = 0x0
}

/**
 * `hkPackfileHeader`, 0x40 bytes.
 */
public object HkPackfileHeader {
    public const val ByteSize: Int = 0x40

    /** `int32[2]` */
    public const val Magic: Int = 0x0

    /** `int32` */
    public const val UserTag: Int = 0x8

    /** `int32` */
    public const val FileVersion: Int = 0xc

    /** `uint8[4]` */
    public const val LayoutRules: Int = 0x10

    /** `int32` */
    public const val NumSections: Int = 0x14

    /** `int32` */
    public const val ContentsSectionIndex: Int = 0x18

    /** `int32` */
    public const val ContentsSectionOffset: Int = 0x1c

    /** `int32` */
    public const val ContentsClassNameSectionIndex: Int = 0x20

    /** `int32` */
    public const val ContentsClassNameSectionOffset: Int = 0x24

    /** `char[16]` */
    public const val ContentsVersion: Int = 0x28

    /** `int32` */
    public const val Flags: Int = 0x38

    /** `int32[1]` */
    public const val Pad: Int = 0x3c
}

/**
 * `hkPackfileSectionHeader`, 0x30 bytes.
 */
public object HkPackfileSectionHeader {
    public const val ByteSize: Int = 0x30

    /** `char[19]` */
    public const val SectionTag: Int = 0x0

    /** `char` */
    public const val NullByte: Int = 0x13

    /** `int32` */
    public const val AbsoluteDataStart: Int = 0x14

    /** `int32` */
    public const val LocalFixupsOffset: Int = 0x18

    /** `int32` */
    public const val GlobalFixupsOffset: Int = 0x1c

    /** `int32` */
    public const val VirtualFixupsOffset: Int = 0x20

    /** `int32` */
    public const val ExportsOffset: Int = 0x24

    /** `int32` */
    public const val ImportsOffset: Int = 0x28

    /** `int32` */
    public const val EndOffset: Int = 0x2c
}

/**
 * `hkPostFinishAttribute`, 0x4 bytes.
 */
public object HkPostFinishAttribute {
    public const val ByteSize: Int = 0x4

    /** `void*` */
    public const val PostFinishFunction: Int = 0x0
}

/**
 * `hkQTransform`, 0x20 bytes.
 */
public object HkQTransform {
    public const val ByteSize: Int = 0x20

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x0

    /** `hkVector4` */
    public const val Translation: Int = 0x10
}

/**
 * `hkRangeInt32Attribute`, 0x10 bytes.
 */
public object HkRangeInt32Attribute {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val Absmin: Int = 0x0

    /** `int32` */
    public const val Absmax: Int = 0x4

    /** `int32` */
    public const val Softmin: Int = 0x8

    /** `int32` */
    public const val Softmax: Int = 0xc
}

/**
 * `hkRangeRealAttribute`, 0x10 bytes.
 */
public object HkRangeRealAttribute {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val Absmin: Int = 0x0

    /** `float` */
    public const val Absmax: Int = 0x4

    /** `float` */
    public const val Softmin: Int = 0x8

    /** `float` */
    public const val Softmax: Int = 0xc
}

/**
 * `hkReferencedObject`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject`
 */
public object HkReferencedObject {
    public const val ByteSize: Int = 0x8

    /** `uint16` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkReflectedFileAttribute`, 0x4 bytes.
 */
public object HkReflectedFileAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val Value: Int = 0x0
}

/**
 * `hkResourceBase`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase`
 */
public object HkResourceBase {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkResourceContainer`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceContainer`
 */
public object HkResourceContainer {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkResourceHandle`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceHandle`
 */
public object HkResourceHandle {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkRootLevelContainer`, 0xc bytes.
 */
public object HkRootLevelContainer {
    public const val ByteSize: Int = 0xc

    /** `hkArray<struct>` */
    public const val NamedVariants: Int = 0x0
}

/**
 * `hkRootLevelContainerNamedVariant`, 0xc bytes.
 */
public object HkRootLevelContainerNamedVariant {
    public const val ByteSize: Int = 0xc

    /** `hkStringPtr` */
    public const val Unnamed0x0: Int = 0x0

    /** `hkStringPtr` */
    public const val ClassName: Int = 0x4

    /** `struct*` */
    public const val Variant: Int = 0x8
}

/**
 * `hkSemanticsAttribute`, 0x1 bytes.
 */
public object HkSemanticsAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkSimpleLocalFrame`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrame` -> `hkSimpleLocalFrame`
 */
public object HkSimpleLocalFrame {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkTransform` */
    public const val Transform: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Children: Int = 0x50

    /** `struct*` */
    public const val ParentFrame: Int = 0x5c

    /** `struct*` */
    public const val Group: Int = 0x60

    /** `hkStringPtr` */
    public const val Unnamed0x64: Int = 0x64
}

/**
 * `hkSphere`, 0x10 bytes.
 */
public object HkSphere {
    public const val ByteSize: Int = 0x10

    /** `hkVector4` */
    public const val Pos: Int = 0x0
}

/**
 * `hkSweptTransform`, 0x50 bytes.
 */
public object HkSweptTransform {
    public const val ByteSize: Int = 0x50

    /** `hkVector4` */
    public const val CenterOfMass0: Int = 0x0

    /** `hkVector4` */
    public const val CenterOfMass1: Int = 0x10

    /** `hkQuaternion` */
    public const val Rotation0: Int = 0x20

    /** `hkQuaternion` */
    public const val Rotation1: Int = 0x30

    /** `hkVector4` */
    public const val CenterOfMassLocal: Int = 0x40
}

/**
 * `hkTraceStreamTitle`, 0x20 bytes.
 */
public object HkTraceStreamTitle {
    public const val ByteSize: Int = 0x20

    /** `char[32]` */
    public const val Value: Int = 0x0
}

/**
 * `hkTrackerSerializableScanSnapshot`, 0x5c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkTrackerSerializableScanSnapshot`
 */
public object HkTrackerSerializableScanSnapshot {
    public const val ByteSize: Int = 0x5c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val Allocations: Int = 0x8

    /** `hkArray<struct>` */
    public const val Blocks: Int = 0x14

    /** `hkArray<int32>` */
    public const val Refs: Int = 0x20

    /** `hkArray<uint8>` */
    public const val TypeNames: Int = 0x2c

    /** `hkArray<uint8>` */
    public const val TraceText: Int = 0x38

    /** `hkArray<int64>` */
    public const val TraceAddrs: Int = 0x44

    /** `hkArray<int32>` */
    public const val TraceParents: Int = 0x50
}

/**
 * `hkTrackerSerializableScanSnapshotAllocation`, 0xc bytes.
 */
public object HkTrackerSerializableScanSnapshotAllocation {
    public const val ByteSize: Int = 0xc

    /** `hkUlong` */
    public const val Start: Int = 0x0

    /** `hkUlong` */
    public const val Size: Int = 0x4

    /** `int32` */
    public const val TraceId: Int = 0x8
}

/**
 * `hkTrackerSerializableScanSnapshotBlock`, 0x18 bytes.
 */
public object HkTrackerSerializableScanSnapshotBlock {
    public const val ByteSize: Int = 0x18

    /** `int32` */
    public const val TypeIndex: Int = 0x0

    /** `hkUlong` */
    public const val Start: Int = 0x4

    /** `hkUlong` */
    public const val Size: Int = 0x8

    /** `int32` */
    public const val ArraySize: Int = 0xc

    /** `int32` */
    public const val StartReferenceIndex: Int = 0x10

    /** `int32` */
    public const val NumReferences: Int = 0x14
}

/**
 * `hkUiAttribute`, 0x14 bytes.
 */
public object HkUiAttribute {
    public const val ByteSize: Int = 0x14

    /** `bool` */
    public const val Visible: Int = 0x0

    /** `enum:int8` */
    public const val HideInModeler: Int = 0x1

    /** `char*` */
    public const val Label: Int = 0x4

    /** `char*` */
    public const val Group: Int = 0x8

    /** `char*` */
    public const val HideBaseClassMembers: Int = 0xc

    /** `bool` */
    public const val EndGroup: Int = 0x10

    /** `bool` */
    public const val EndGroup2: Int = 0x11

    /** `bool` */
    public const val Advanced: Int = 0x12
}

/**
 * `hkVertexFormat`, 0x104 bytes.
 */
public object HkVertexFormat {
    public const val ByteSize: Int = 0x104

    /** `struct[32]` */
    public const val Elements: Int = 0x0

    /** `int32` */
    public const val NumElements: Int = 0x100
}

/**
 * `hkVertexFormatElement`, 0x8 bytes.
 */
public object HkVertexFormatElement {
    public const val ByteSize: Int = 0x8

    /** `enum:uint8` */
    public const val DataType: Int = 0x0

    /** `uint8` */
    public const val NumValues: Int = 0x1

    /** `enum:uint8` */
    public const val Usage: Int = 0x2

    /** `uint8` */
    public const val SubUsage: Int = 0x3

    /** `flags:uint8` */
    public const val Flags: Int = 0x4

    /** `uint8[3]` */
    public const val Pad: Int = 0x5
}

/**
 * `hkWorldMemoryAvailableWatchDog`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkWorldMemoryAvailableWatchDog`
 */
public object HkWorldMemoryAvailableWatchDog {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}
