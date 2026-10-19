// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Core: 87 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.runtime.ptde.havok

/**
 * `hkAabb`, 0x20 bytes.
 */
public object HkAabb {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val min: Int = 0x0

    /** `hkVector4` */
    public const val max: Int = 0x10
}

/**
 * `hkAabbHalf`, 0x10 bytes.
 */
public object HkAabbHalf {
    public const val ByteSize: Int = 0x10

    /** `uint16[6]` */
    public const val data: Int = 0x0

    /** `uint16[2]` */
    public const val extras: Int = 0xc
}

/**
 * `hkAabbUint32`, 0x20 bytes.
 */
public object HkAabbUint32 {
    public const val ByteSize: Int = 0x20

    /** `uint32[3]` */
    public const val min: Int = 0x0

    /** `uint8[3]` */
    public const val expansionMin: Int = 0xc

    /** `uint8` */
    public const val expansionShift: Int = 0xf

    /** `uint32[3]` */
    public const val max: Int = 0x10

    /** `uint8[3]` */
    public const val expansionMax: Int = 0x1c

    /** `uint8` */
    public const val shapeKeyByte: Int = 0x1f
}

/**
 * `hkAlignSceneToNodeOptions`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkAlignSceneToNodeOptions`
 */
public object HkAlignSceneToNodeOptions {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `bool` */
    public const val invert: Int = 0x8

    /** `bool` */
    public const val transformPositionX: Int = 0x9

    /** `bool` */
    public const val transformPositionY: Int = 0xa

    /** `bool` */
    public const val transformPositionZ: Int = 0xb

    /** `bool` */
    public const val transformRotation: Int = 0xc

    /** `bool` */
    public const val transformScale: Int = 0xd

    /** `bool` */
    public const val transformSkew: Int = 0xe

    /** `int32` */
    public const val keyframe: Int = 0x10

    /** `hkStringPtr` */
    public const val nodeName: Int = 0x14
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
    public const val type: Int = 0x0
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
    public const val words: Int = 0x0

    /** `int32` */
    public const val numBits: Int = 0xc
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
    public const val unnamed0x0: Int = 0x0

    /** `struct*` */
    public const val parent: Int = 0x4

    /** `int32` */
    public const val objectSize: Int = 0x8

    /** `int32` */
    public const val numImplementedInterfaces: Int = 0xc

    /** `hkSimpleArray<struct>` */
    public const val declaredEnums: Int = 0x10

    /** `hkSimpleArray<struct>` */
    public const val declaredMembers: Int = 0x18

    /** `bool` */
    public const val unnamed0x20: Int = 0x20

    /** `void*` */
    public const val defaults: Int = 0x20

    /** `struct*` */
    public const val attributes: Int = 0x24

    /** `flags:uint32` */
    public const val flags: Int = 0x28

    /** `int32` */
    public const val describedVersion: Int = 0x2c
}

/**
 * `hkClassEnum`, 0xc bytes.
 */
public object HkClassEnum {
    public const val ByteSize: Int = 0xc

    /** `char*` */
    public const val unnamed0x0: Int = 0x0

    /** `hkSimpleArray<struct>` */
    public const val items: Int = 0x4

    /** `struct*` */
    public const val attributes: Int = 0xc

    /** `flags:uint32` */
    public const val flags: Int = 0x10
}

/**
 * `hkClassEnumItem`, 0x8 bytes.
 */
public object HkClassEnumItem {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val value: Int = 0x0

    /** `char*` */
    public const val unnamed0x4: Int = 0x4
}

/**
 * `hkClassMember`, 0x18 bytes.
 */
public object HkClassMember {
    public const val ByteSize: Int = 0x18

    /** `char*` */
    public const val unnamed0x0: Int = 0x0

    /** `struct*` */
    public const val `class`: Int = 0x4

    /** `struct*` */
    public const val enum: Int = 0x8

    /** `enum:uint8` */
    public const val type: Int = 0xc

    /** `enum:uint8` */
    public const val subtype: Int = 0xd

    /** `int16` */
    public const val cArraySize: Int = 0xe

    /** `flags:uint16` */
    public const val flags: Int = 0x10

    /** `uint16` */
    public const val offset: Int = 0x12

    /** `struct*` */
    public const val attributes: Int = 0x14
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
    public const val position: Int = 0x0

    /** `hkVector4` */
    public const val separatingNormal: Int = 0x10
}

/**
 * `hkContactPointMaterial`, 0x8 bytes.
 */
public object HkContactPointMaterial {
    public const val ByteSize: Int = 0x8

    /** `hkUlong` */
    public const val userData: Int = 0x0

    /** `uint8` */
    public const val friction: Int = 0x4

    /** `uint8` */
    public const val restitution: Int = 0x5

    /** `uint8` */
    public const val maxImpulse: Int = 0x6

    /** `uint8` */
    public const val flags: Int = 0x7
}

/**
 * `hkCustomAttributes`, 0x8 bytes.
 */
public object HkCustomAttributes {
    public const val ByteSize: Int = 0x8

    /** `hkSimpleArray<struct>` */
    public const val attributes: Int = 0x0
}

/**
 * `hkCustomAttributesAttribute`, 0xc bytes.
 */
public object HkCustomAttributesAttribute {
    public const val ByteSize: Int = 0xc

    /** `char*` */
    public const val unnamed0x0: Int = 0x0

    /** `homogeneousArray` */
    public const val value: Int = 0x4
}

/**
 * `hkDataObjectTypeAttribute`, 0x4 bytes.
 */
public object HkDataObjectTypeAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val typeName: Int = 0x0
}

/**
 * `hkDefaultMemoryTracker`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkDefaultMemoryTracker`
 */
public object HkDefaultMemoryTracker {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkDescriptionAttribute`, 0x4 bytes.
 */
public object HkDescriptionAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val string: Int = 0x0
}

/**
 * `hkDocumentationAttribute`, 0x4 bytes.
 */
public object HkDocumentationAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val docsSectionTag: Int = 0x0
}

/**
 * `hkGeometry`, 0x18 bytes.
 */
public object HkGeometry {
    public const val ByteSize: Int = 0x18

    /** `hkArray<hkVector4>` */
    public const val vertices: Int = 0x0

    /** `hkArray<struct>` */
    public const val triangles: Int = 0xc
}

/**
 * `hkGeometryTriangle`, 0x10 bytes.
 */
public object HkGeometryTriangle {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val a: Int = 0x0

    /** `int32` */
    public const val b: Int = 0x4

    /** `int32` */
    public const val unnamed0x8: Int = 0x8

    /** `int32` */
    public const val material: Int = 0xc
}

/**
 * `hkGizmoAttribute`, 0xc bytes.
 */
public object HkGizmoAttribute {
    public const val ByteSize: Int = 0xc

    /** `bool` */
    public const val visible: Int = 0x0

    /** `char*` */
    public const val label: Int = 0x4

    /** `enum:int8` */
    public const val type: Int = 0x8
}

/**
 * `hkHalf8`, 0x10 bytes.
 */
public object HkHalf8 {
    public const val ByteSize: Int = 0x10

    /** `hkHalf[8]` */
    public const val quad: Int = 0x0
}

/**
 * `hkIndexedTransformSet`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkIndexedTransformSet`
 */
public object HkIndexedTransformSet {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkMatrix4>` */
    public const val matrices: Int = 0x8

    /** `hkArray<hkMatrix4>` */
    public const val inverseMatrices: Int = 0x14

    /** `hkArray<int16>` */
    public const val matricesOrder: Int = 0x20

    /** `hkArray<hkStringPtr>` */
    public const val matricesNames: Int = 0x2c

    /** `hkArray<struct>` */
    public const val indexMappings: Int = 0x38

    /** `bool` */
    public const val allMatricesAreAffine: Int = 0x44
}

/**
 * `hkLinkAttribute`, 0x1 bytes.
 */
public object HkLinkAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val type: Int = 0x0
}

/**
 * `hkLocalFrame`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrame`
 */
public object HkLocalFrame {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkLocalFrameGroup`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrameGroup`
 */
public object HkLocalFrameGroup {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val unnamed0x8: Int = 0x8
}

/**
 * `hkMemoryMeshBody`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshBody` -> `hkMemoryMeshBody`
 */
public object HkMemoryMeshBody {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkMatrix4` */
    public const val transform: Int = 0x10

    /** `struct*` */
    public const val transformSet: Int = 0x50

    /** `struct*` */
    public const val shape: Int = 0x54

    /** `hkArray<ptr>` */
    public const val vertexBuffers: Int = 0x58

    /** `hkStringPtr` */
    public const val unnamed0x64: Int = 0x64
}

/**
 * `hkMemoryMeshMaterial`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshMaterial` -> `hkMemoryMeshMaterial`
 */
public object HkMemoryMeshMaterial {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val materialName: Int = 0x8

    /** `hkArray<ptr>` */
    public const val textures: Int = 0xc
}

/**
 * `hkMemoryMeshShape`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkMemoryMeshShape`
 */
public object HkMemoryMeshShape {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val sections: Int = 0x8

    /** `hkArray<uint16>` */
    public const val indices16: Int = 0x14

    /** `hkArray<uint32>` */
    public const val indices32: Int = 0x20

    /** `hkStringPtr` */
    public const val unnamed0x2c: Int = 0x2c
}

/**
 * `hkMemoryMeshTexture`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshTexture` -> `hkMemoryMeshTexture`
 */
public object HkMemoryMeshTexture {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val filename: Int = 0x8

    /** `hkArray<uint8>` */
    public const val data: Int = 0xc

    /** `enum:int8` */
    public const val format: Int = 0x18

    /** `bool` */
    public const val hasMipMaps: Int = 0x19

    /** `enum:int8` */
    public const val filterMode: Int = 0x1a

    /** `enum:int8` */
    public const val usageHint: Int = 0x1b

    /** `int32` */
    public const val textureCoordChannel: Int = 0x1c
}

/**
 * `hkMemoryMeshVertexBuffer`, 0x1a8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer` -> `hkMemoryMeshVertexBuffer`
 */
public object HkMemoryMeshVertexBuffer {
    public const val ByteSize: Int = 0x1a8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct` */
    public const val format: Int = 0x8

    /** `int32[32]` */
    public const val elementOffsets: Int = 0x10c

    /** `hkArray<uint8>` */
    public const val memory: Int = 0x18c

    /** `int32` */
    public const val vertexStride: Int = 0x198

    /** `bool` */
    public const val locked: Int = 0x19c

    /** `int32` */
    public const val numVertices: Int = 0x1a0

    /** `bool` */
    public const val isBigEndian: Int = 0x1a4

    /** `bool` */
    public const val isSharable: Int = 0x1a5
}

/**
 * `hkMemoryResourceContainer`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceContainer` -> `hkMemoryResourceContainer`
 */
public object HkMemoryResourceContainer {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val unnamed0x8: Int = 0x8

    /** `struct*` */
    public const val parent: Int = 0xc

    /** `hkArray<ptr>` */
    public const val resourceHandles: Int = 0x10

    /** `hkArray<ptr>` */
    public const val children: Int = 0x1c
}

/**
 * `hkMemoryResourceHandle`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceHandle` -> `hkMemoryResourceHandle`
 */
public object HkMemoryResourceHandle {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val variant: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0xc: Int = 0xc

    /** `hkArray<struct>` */
    public const val references: Int = 0x10
}

/**
 * `hkMemoryResourceHandleExternalLink`, 0x8 bytes.
 */
public object HkMemoryResourceHandleExternalLink {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val memberName: Int = 0x0

    /** `hkStringPtr` */
    public const val externalId: Int = 0x4
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
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkMeshBoneIndexMapping`, 0xc bytes.
 */
public object HkMeshBoneIndexMapping {
    public const val ByteSize: Int = 0xc

    /** `hkArray<int16>` */
    public const val mapping: Int = 0x0
}

/**
 * `hkMeshMaterial`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshMaterial`
 */
public object HkMeshMaterial {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkMeshSection`, 0x28 bytes.
 */
public object HkMeshSection {
    public const val ByteSize: Int = 0x28

    /** `enum:uint8` */
    public const val primitiveType: Int = 0x0

    /** `int32` */
    public const val numPrimitives: Int = 0x4

    /** `int32` */
    public const val numIndices: Int = 0x8

    /** `int32` */
    public const val vertexStartIndex: Int = 0xc

    /** `int32` */
    public const val transformIndex: Int = 0x10

    /** `enum:uint8` */
    public const val indexType: Int = 0x14

    /** `void*` */
    public const val indices: Int = 0x18

    /** `struct*` */
    public const val vertexBuffer: Int = 0x1c

    /** `struct*` */
    public const val material: Int = 0x20

    /** `int32` */
    public const val sectionIndex: Int = 0x24
}

/**
 * `hkMeshSectionCinfo`, 0x20 bytes.
 */
public object HkMeshSectionCinfo {
    public const val ByteSize: Int = 0x20

    /** `struct*` */
    public const val vertexBuffer: Int = 0x0

    /** `struct*` */
    public const val material: Int = 0x4

    /** `enum:uint8` */
    public const val primitiveType: Int = 0x8

    /** `int32` */
    public const val numPrimitives: Int = 0xc

    /** `enum:uint8` */
    public const val indexType: Int = 0x10

    /** `void*` */
    public const val indices: Int = 0x14

    /** `int32` */
    public const val vertexStartIndex: Int = 0x18

    /** `int32` */
    public const val transformIndex: Int = 0x1c
}

/**
 * `hkMeshShape`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape`
 */
public object HkMeshShape {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkMeshTexture`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshTexture`
 */
public object HkMeshTexture {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkMeshVertexBuffer`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer`
 */
public object HkMeshVertexBuffer {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkModelerNodeTypeAttribute`, 0x1 bytes.
 */
public object HkModelerNodeTypeAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val type: Int = 0x0
}

/**
 * `hkMonitorStreamColorTable`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMonitorStreamColorTable`
 */
public object HkMonitorStreamColorTable {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val colorPairs: Int = 0x8

    /** `uint32` */
    public const val defaultColor: Int = 0x14
}

/**
 * `hkMonitorStreamColorTableColorPair`, 0x8 bytes.
 */
public object HkMonitorStreamColorTableColorPair {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val colorName: Int = 0x0

    /** `enum:uint32` */
    public const val color: Int = 0x4
}

/**
 * `hkMonitorStreamFrameInfo`, 0x24 bytes.
 */
public object HkMonitorStreamFrameInfo {
    public const val ByteSize: Int = 0x24

    /** `hkStringPtr` */
    public const val heading: Int = 0x0

    /** `int32` */
    public const val indexOfTimer0: Int = 0x4

    /** `int32` */
    public const val indexOfTimer1: Int = 0x8

    /** `enum:uint32` */
    public const val absoluteTimeCounter: Int = 0xc

    /** `float` */
    public const val timerFactor0: Int = 0x10

    /** `float` */
    public const val timerFactor1: Int = 0x14

    /** `int32` */
    public const val threadId: Int = 0x18

    /** `int32` */
    public const val frameStreamStart: Int = 0x1c

    /** `int32` */
    public const val frameStreamEnd: Int = 0x20
}

/**
 * `hkMonitorStreamStringMap`, 0xc bytes.
 */
public object HkMonitorStreamStringMap {
    public const val ByteSize: Int = 0xc

    /** `hkArray<struct>` */
    public const val map: Int = 0x0
}

/**
 * `hkMonitorStreamStringMapStringMap`, 0x10 bytes.
 */
public object HkMonitorStreamStringMapStringMap {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val id: Int = 0x0

    /** `hkStringPtr` */
    public const val string: Int = 0x8
}

/**
 * `hkMoppBvTreeShapeBase`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvTreeShape` -> `hkMoppBvTreeShapeBase`
 */
public object HkMoppBvTreeShapeBase {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val bvTreeType: Int = 0x10

    /** `struct*` */
    public const val code: Int = 0x14

    /** `void*` */
    public const val moppData: Int = 0x18

    /** `uint32` */
    public const val moppDataSize: Int = 0x1c

    /** `hkVector4` */
    public const val codeInfoCopy: Int = 0x20
}

/**
 * `hkMotionState`, 0xb0 bytes.
 */
public object HkMotionState {
    public const val ByteSize: Int = 0xb0

    /** `hkTransform` */
    public const val transform: Int = 0x0

    /** `struct` */
    public const val sweptTransform: Int = 0x40

    /** `hkVector4` */
    public const val deltaAngle: Int = 0x90

    /** `float` */
    public const val objectRadius: Int = 0xa0

    /** `hkHalf` */
    public const val linearDamping: Int = 0xa4

    /** `hkHalf` */
    public const val angularDamping: Int = 0xa6

    /** `hkHalf` */
    public const val timeFactor: Int = 0xa8

    /** `uint8` */
    public const val maxLinearVelocity: Int = 0xaa

    /** `uint8` */
    public const val maxAngularVelocity: Int = 0xab

    /** `uint8` */
    public const val deactivationClass: Int = 0xac
}

/**
 * `hkMultiThreadCheck`, 0xc bytes.
 */
public object HkMultiThreadCheck {
    public const val ByteSize: Int = 0xc

    /** `uint32` */
    public const val threadId: Int = 0x0

    /** `int32` */
    public const val stackTraceId: Int = 0x4

    /** `uint16` */
    public const val markCount: Int = 0x8

    /** `uint16` */
    public const val markBitStack: Int = 0xa
}

/**
 * `hkMultipleVertexBuffer`, 0x144 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer` -> `hkMultipleVertexBuffer`
 */
public object HkMultipleVertexBuffer {
    public const val ByteSize: Int = 0x144

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct` */
    public const val vertexFormat: Int = 0x8

    /** `hkArray<struct>` */
    public const val lockedElements: Int = 0x10c

    /** `struct*` */
    public const val lockedBuffer: Int = 0x118

    /** `hkArray<struct>` */
    public const val elementInfos: Int = 0x11c

    /** `hkArray<struct>` */
    public const val vertexBufferInfos: Int = 0x128

    /** `int32` */
    public const val numVertices: Int = 0x134

    /** `bool` */
    public const val isLocked: Int = 0x138

    /** `uint32` */
    public const val updateCount: Int = 0x13c

    /** `bool` */
    public const val writeLock: Int = 0x140

    /** `bool` */
    public const val isSharable: Int = 0x141

    /** `bool` */
    public const val constructionComplete: Int = 0x142
}

/**
 * `hkMultipleVertexBufferElementInfo`, 0x2 bytes.
 */
public object HkMultipleVertexBufferElementInfo {
    public const val ByteSize: Int = 0x2

    /** `uint8` */
    public const val vertexBufferIndex: Int = 0x0

    /** `uint8` */
    public const val elementIndex: Int = 0x1
}

/**
 * `hkMultipleVertexBufferLockedElement`, 0x7 bytes.
 */
public object HkMultipleVertexBufferLockedElement {
    public const val ByteSize: Int = 0x7

    /** `uint8` */
    public const val vertexBufferIndex: Int = 0x0

    /** `uint8` */
    public const val elementIndex: Int = 0x1

    /** `uint8` */
    public const val lockedBufferIndex: Int = 0x2

    /** `uint8` */
    public const val vertexFormatIndex: Int = 0x3

    /** `uint8` */
    public const val lockFlags: Int = 0x4

    /** `uint8` */
    public const val outputBufferIndex: Int = 0x5

    /** `int8` */
    public const val emulatedIndex: Int = 0x6
}

/**
 * `hkMultipleVertexBufferVertexBufferInfo`, 0xc bytes.
 */
public object HkMultipleVertexBufferVertexBufferInfo {
    public const val ByteSize: Int = 0xc

    /** `struct*` */
    public const val vertexBuffer: Int = 0x0

    /** `void*` */
    public const val lockedVertices: Int = 0x4

    /** `bool` */
    public const val isLocked: Int = 0x8
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
    public const val values: Int = 0x0
}

/**
 * `hkPackfileHeader`, 0x40 bytes.
 */
public object HkPackfileHeader {
    public const val ByteSize: Int = 0x40

    /** `int32[2]` */
    public const val magic: Int = 0x0

    /** `int32` */
    public const val userTag: Int = 0x8

    /** `int32` */
    public const val fileVersion: Int = 0xc

    /** `uint8[4]` */
    public const val layoutRules: Int = 0x10

    /** `int32` */
    public const val numSections: Int = 0x14

    /** `int32` */
    public const val contentsSectionIndex: Int = 0x18

    /** `int32` */
    public const val contentsSectionOffset: Int = 0x1c

    /** `int32` */
    public const val contentsClassNameSectionIndex: Int = 0x20

    /** `int32` */
    public const val contentsClassNameSectionOffset: Int = 0x24

    /** `char[16]` */
    public const val contentsVersion: Int = 0x28

    /** `int32` */
    public const val flags: Int = 0x38

    /** `int32[1]` */
    public const val pad: Int = 0x3c
}

/**
 * `hkPackfileSectionHeader`, 0x30 bytes.
 */
public object HkPackfileSectionHeader {
    public const val ByteSize: Int = 0x30

    /** `char[19]` */
    public const val sectionTag: Int = 0x0

    /** `char` */
    public const val nullByte: Int = 0x13

    /** `int32` */
    public const val absoluteDataStart: Int = 0x14

    /** `int32` */
    public const val localFixupsOffset: Int = 0x18

    /** `int32` */
    public const val globalFixupsOffset: Int = 0x1c

    /** `int32` */
    public const val virtualFixupsOffset: Int = 0x20

    /** `int32` */
    public const val exportsOffset: Int = 0x24

    /** `int32` */
    public const val importsOffset: Int = 0x28

    /** `int32` */
    public const val endOffset: Int = 0x2c
}

/**
 * `hkPostFinishAttribute`, 0x4 bytes.
 */
public object HkPostFinishAttribute {
    public const val ByteSize: Int = 0x4

    /** `void*` */
    public const val postFinishFunction: Int = 0x0
}

/**
 * `hkQTransform`, 0x20 bytes.
 */
public object HkQTransform {
    public const val ByteSize: Int = 0x20

    /** `hkQuaternion` */
    public const val rotation: Int = 0x0

    /** `hkVector4` */
    public const val translation: Int = 0x10
}

/**
 * `hkRangeInt32Attribute`, 0x10 bytes.
 */
public object HkRangeInt32Attribute {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val absmin: Int = 0x0

    /** `int32` */
    public const val absmax: Int = 0x4

    /** `int32` */
    public const val softmin: Int = 0x8

    /** `int32` */
    public const val softmax: Int = 0xc
}

/**
 * `hkRangeRealAttribute`, 0x10 bytes.
 */
public object HkRangeRealAttribute {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val absmin: Int = 0x0

    /** `float` */
    public const val absmax: Int = 0x4

    /** `float` */
    public const val softmin: Int = 0x8

    /** `float` */
    public const val softmax: Int = 0xc
}

/**
 * `hkReferencedObject`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject`
 */
public object HkReferencedObject {
    public const val ByteSize: Int = 0x8

    /** `uint16` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkReflectedFileAttribute`, 0x4 bytes.
 */
public object HkReflectedFileAttribute {
    public const val ByteSize: Int = 0x4

    /** `char*` */
    public const val value: Int = 0x0
}

/**
 * `hkResourceBase`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase`
 */
public object HkResourceBase {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkResourceContainer`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceContainer`
 */
public object HkResourceContainer {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkResourceHandle`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceHandle`
 */
public object HkResourceHandle {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkRootLevelContainer`, 0xc bytes.
 */
public object HkRootLevelContainer {
    public const val ByteSize: Int = 0xc

    /** `hkArray<struct>` */
    public const val namedVariants: Int = 0x0
}

/**
 * `hkRootLevelContainerNamedVariant`, 0xc bytes.
 */
public object HkRootLevelContainerNamedVariant {
    public const val ByteSize: Int = 0xc

    /** `hkStringPtr` */
    public const val unnamed0x0: Int = 0x0

    /** `hkStringPtr` */
    public const val className: Int = 0x4

    /** `struct*` */
    public const val variant: Int = 0x8
}

/**
 * `hkSemanticsAttribute`, 0x1 bytes.
 */
public object HkSemanticsAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val type: Int = 0x0
}

/**
 * `hkSimpleLocalFrame`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrame` -> `hkSimpleLocalFrame`
 */
public object HkSimpleLocalFrame {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkTransform` */
    public const val transform: Int = 0x10

    /** `hkArray<ptr>` */
    public const val children: Int = 0x50

    /** `struct*` */
    public const val parentFrame: Int = 0x5c

    /** `struct*` */
    public const val group: Int = 0x60

    /** `hkStringPtr` */
    public const val unnamed0x64: Int = 0x64
}

/**
 * `hkSphere`, 0x10 bytes.
 */
public object HkSphere {
    public const val ByteSize: Int = 0x10

    /** `hkVector4` */
    public const val pos: Int = 0x0
}

/**
 * `hkSweptTransform`, 0x50 bytes.
 */
public object HkSweptTransform {
    public const val ByteSize: Int = 0x50

    /** `hkVector4` */
    public const val centerOfMass0: Int = 0x0

    /** `hkVector4` */
    public const val centerOfMass1: Int = 0x10

    /** `hkQuaternion` */
    public const val rotation0: Int = 0x20

    /** `hkQuaternion` */
    public const val rotation1: Int = 0x30

    /** `hkVector4` */
    public const val centerOfMassLocal: Int = 0x40
}

/**
 * `hkTraceStreamTitle`, 0x20 bytes.
 */
public object HkTraceStreamTitle {
    public const val ByteSize: Int = 0x20

    /** `char[32]` */
    public const val value: Int = 0x0
}

/**
 * `hkTrackerSerializableScanSnapshot`, 0x5c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkTrackerSerializableScanSnapshot`
 */
public object HkTrackerSerializableScanSnapshot {
    public const val ByteSize: Int = 0x5c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val allocations: Int = 0x8

    /** `hkArray<struct>` */
    public const val blocks: Int = 0x14

    /** `hkArray<int32>` */
    public const val refs: Int = 0x20

    /** `hkArray<uint8>` */
    public const val typeNames: Int = 0x2c

    /** `hkArray<uint8>` */
    public const val traceText: Int = 0x38

    /** `hkArray<int64>` */
    public const val traceAddrs: Int = 0x44

    /** `hkArray<int32>` */
    public const val traceParents: Int = 0x50
}

/**
 * `hkTrackerSerializableScanSnapshotAllocation`, 0xc bytes.
 */
public object HkTrackerSerializableScanSnapshotAllocation {
    public const val ByteSize: Int = 0xc

    /** `hkUlong` */
    public const val start: Int = 0x0

    /** `hkUlong` */
    public const val size: Int = 0x4

    /** `int32` */
    public const val traceId: Int = 0x8
}

/**
 * `hkTrackerSerializableScanSnapshotBlock`, 0x18 bytes.
 */
public object HkTrackerSerializableScanSnapshotBlock {
    public const val ByteSize: Int = 0x18

    /** `int32` */
    public const val typeIndex: Int = 0x0

    /** `hkUlong` */
    public const val start: Int = 0x4

    /** `hkUlong` */
    public const val size: Int = 0x8

    /** `int32` */
    public const val arraySize: Int = 0xc

    /** `int32` */
    public const val startReferenceIndex: Int = 0x10

    /** `int32` */
    public const val numReferences: Int = 0x14
}

/**
 * `hkUiAttribute`, 0x14 bytes.
 */
public object HkUiAttribute {
    public const val ByteSize: Int = 0x14

    /** `bool` */
    public const val visible: Int = 0x0

    /** `enum:int8` */
    public const val hideInModeler: Int = 0x1

    /** `char*` */
    public const val label: Int = 0x4

    /** `char*` */
    public const val group: Int = 0x8

    /** `char*` */
    public const val hideBaseClassMembers: Int = 0xc

    /** `bool` */
    public const val endGroup: Int = 0x10

    /** `bool` */
    public const val endGroup2: Int = 0x11

    /** `bool` */
    public const val advanced: Int = 0x12
}

/**
 * `hkVertexFormat`, 0x104 bytes.
 */
public object HkVertexFormat {
    public const val ByteSize: Int = 0x104

    /** `struct[32]` */
    public const val elements: Int = 0x0

    /** `int32` */
    public const val numElements: Int = 0x100
}

/**
 * `hkVertexFormatElement`, 0x8 bytes.
 */
public object HkVertexFormatElement {
    public const val ByteSize: Int = 0x8

    /** `enum:uint8` */
    public const val dataType: Int = 0x0

    /** `uint8` */
    public const val numValues: Int = 0x1

    /** `enum:uint8` */
    public const val usage: Int = 0x2

    /** `uint8` */
    public const val subUsage: Int = 0x3

    /** `flags:uint8` */
    public const val flags: Int = 0x4

    /** `uint8[3]` */
    public const val pad: Int = 0x5
}

/**
 * `hkWorldMemoryAvailableWatchDog`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkWorldMemoryAvailableWatchDog`
 */
public object HkWorldMemoryAvailableWatchDog {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}
