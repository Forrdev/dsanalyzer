// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generatePTDEHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Scene: 44 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.ptde

/**
 * `hkxAnimatedFloat`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedFloat`
 */
public object HkxAnimatedFloat {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<float>` */
    public const val Floats: Int = 0x8

    /** `enum:uint8` */
    public const val Hint: Int = 0x14
}

/**
 * `hkxAnimatedMatrix`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedMatrix`
 */
public object HkxAnimatedMatrix {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkMatrix4>` */
    public const val Matrices: Int = 0x8

    /** `enum:uint8` */
    public const val Hint: Int = 0x14
}

/**
 * `hkxAnimatedQuaternion`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedQuaternion`
 */
public object HkxAnimatedQuaternion {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkQuaternion>` */
    public const val Quaternions: Int = 0x8
}

/**
 * `hkxAnimatedVector`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedVector`
 */
public object HkxAnimatedVector {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkVector4>` */
    public const val Vectors: Int = 0x8

    /** `enum:uint8` */
    public const val Hint: Int = 0x14
}

/**
 * `hkxAttribute`, 0x8 bytes.
 */
public object HkxAttribute {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0x0: Int = 0x0

    /** `struct*` */
    public const val Value: Int = 0x4
}

/**
 * `hkxAttributeGroup`, 0x10 bytes.
 */
public object HkxAttributeGroup {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val Unnamed0x0: Int = 0x0

    /** `hkArray<struct>` */
    public const val Attributes: Int = 0x4
}

/**
 * `hkxAttributeHolder`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder`
 */
public object HkxAttributeHolder {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val AttributeGroups: Int = 0x8
}

/**
 * `hkxCamera`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxCamera`
 */
public object HkxCamera {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkVector4` */
    public const val From: Int = 0x10

    /** `hkVector4` */
    public const val Focus: Int = 0x20

    /** `hkVector4` */
    public const val Up: Int = 0x30

    /** `float` */
    public const val Fov: Int = 0x40

    /** `float` */
    public const val Far: Int = 0x44

    /** `float` */
    public const val Near: Int = 0x48

    /** `bool` */
    public const val LeftHanded: Int = 0x4c
}

/**
 * `hkxEdgeSelectionChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEdgeSelectionChannel`
 */
public object HkxEdgeSelectionChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val SelectedEdges: Int = 0x8
}

/**
 * `hkxEnum`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEnum`
 */
public object HkxEnum {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val Items: Int = 0x8
}

/**
 * `hkxEnumItem`, 0x8 bytes.
 */
public object HkxEnumItem {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val Value: Int = 0x0

    /** `hkStringPtr` */
    public const val Unnamed0x4: Int = 0x4
}

/**
 * `hkxEnvironment`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEnvironment`
 */
public object HkxEnvironment {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val Variables: Int = 0x8
}

/**
 * `hkxEnvironmentVariable`, 0x8 bytes.
 */
public object HkxEnvironmentVariable {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0x0: Int = 0x0

    /** `hkStringPtr` */
    public const val Value: Int = 0x4
}

/**
 * `hkxIndexBuffer`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxIndexBuffer`
 */
public object HkxIndexBuffer {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8` */
    public const val IndexType: Int = 0x8

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0xc

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x18

    /** `uint32` */
    public const val VertexBaseOffset: Int = 0x24

    /** `uint32` */
    public const val Length: Int = 0x28
}

/**
 * `hkxLight`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxLight`
 */
public object HkxLight {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8` */
    public const val Type: Int = 0x8

    /** `hkVector4` */
    public const val Position: Int = 0x10

    /** `hkVector4` */
    public const val Direction: Int = 0x20

    /** `uint32` */
    public const val Color: Int = 0x30

    /** `float` */
    public const val Angle: Int = 0x34
}

/**
 * `hkxMaterial`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxMaterial`
 */
public object HkxMaterial {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0x14: Int = 0x14

    /** `hkArray<struct>` */
    public const val Stages: Int = 0x18

    /** `hkVector4` */
    public const val DiffuseColor: Int = 0x30

    /** `hkVector4` */
    public const val AmbientColor: Int = 0x40

    /** `hkVector4` */
    public const val SpecularColor: Int = 0x50

    /** `hkVector4` */
    public const val EmissiveColor: Int = 0x60

    /** `hkArray<ptr>` */
    public const val SubMaterials: Int = 0x70

    /** `struct*` */
    public const val ExtraData: Int = 0x7c

    /** `hkArray<struct>` */
    public const val Properties: Int = 0x80
}

/**
 * `hkxMaterialEffect`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialEffect`
 */
public object HkxMaterialEffect {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Unnamed0x8: Int = 0x8

    /** `enum:uint8` */
    public const val Type: Int = 0xc

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x10
}

/**
 * `hkxMaterialProperty`, 0x8 bytes.
 */
public object HkxMaterialProperty {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val Key: Int = 0x0

    /** `uint32` */
    public const val Value: Int = 0x4
}

/**
 * `hkxMaterialShader`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialShader`
 */
public object HkxMaterialShader {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Unnamed0x8: Int = 0x8

    /** `enum:uint8` */
    public const val Type: Int = 0xc

    /** `hkStringPtr` */
    public const val VertexEntryName: Int = 0x10

    /** `hkStringPtr` */
    public const val GeomEntryName: Int = 0x14

    /** `hkStringPtr` */
    public const val PixelEntryName: Int = 0x18

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x1c
}

/**
 * `hkxMaterialShaderSet`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialShaderSet`
 */
public object HkxMaterialShaderSet {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val Shaders: Int = 0x8
}

/**
 * `hkxMaterialTextureStage`, 0xc bytes.
 */
public object HkxMaterialTextureStage {
    public const val ByteSize: Int = 0xc

    /** `struct*` */
    public const val Texture: Int = 0x0

    /** `enum:int32` */
    public const val UsageHint: Int = 0x4

    /** `int32` */
    public const val TcoordChannel: Int = 0x8
}

/**
 * `hkxMesh`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMesh`
 */
public object HkxMesh {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val Sections: Int = 0x8

    /** `hkArray<ptr>` */
    public const val UserChannelInfos: Int = 0x14
}

/**
 * `hkxMeshSection`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMeshSection`
 */
public object HkxMeshSection {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val VertexBuffer: Int = 0x8

    /** `hkArray<ptr>` */
    public const val IndexBuffers: Int = 0xc

    /** `struct*` */
    public const val Material: Int = 0x18

    /** `hkArray<ptr>` */
    public const val UserChannels: Int = 0x1c
}

/**
 * `hkxMeshUserChannelInfo`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxMeshUserChannelInfo`
 */
public object HkxMeshUserChannelInfo {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0x14: Int = 0x14

    /** `hkStringPtr` */
    public const val ClassName: Int = 0x18
}

/**
 * `hkxNode`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxNode`
 */
public object HkxNode {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val Object: Int = 0x18

    /** `hkArray<hkMatrix4>` */
    public const val KeyFrames: Int = 0x1c

    /** `hkArray<ptr>` */
    public const val Children: Int = 0x28

    /** `hkArray<struct>` */
    public const val Annotations: Int = 0x34

    /** `hkStringPtr` */
    public const val UserProperties: Int = 0x40

    /** `bool` */
    public const val Selected: Int = 0x44
}

/**
 * `hkxNodeAnnotationData`, 0x8 bytes.
 */
public object HkxNodeAnnotationData {
    public const val ByteSize: Int = 0x8

    /** `float` */
    public const val Time: Int = 0x0

    /** `hkStringPtr` */
    public const val Description: Int = 0x4
}

/**
 * `hkxNodeSelectionSet`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxNodeSelectionSet`
 */
public object HkxNodeSelectionSet {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x8

    /** `hkArray<ptr>` */
    public const val SelectedNodes: Int = 0x14

    /** `hkStringPtr` */
    public const val Unnamed0x20: Int = 0x20
}

/**
 * `hkxScene`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxScene`
 */
public object HkxScene {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Modeller: Int = 0x8

    /** `hkStringPtr` */
    public const val Asset: Int = 0xc

    /** `float` */
    public const val SceneLength: Int = 0x10

    /** `struct*` */
    public const val RootNode: Int = 0x14

    /** `hkArray<ptr>` */
    public const val SelectionSets: Int = 0x18

    /** `hkArray<ptr>` */
    public const val Cameras: Int = 0x24

    /** `hkArray<ptr>` */
    public const val Lights: Int = 0x30

    /** `hkArray<ptr>` */
    public const val Meshes: Int = 0x3c

    /** `hkArray<ptr>` */
    public const val Materials: Int = 0x48

    /** `hkArray<ptr>` */
    public const val InplaceTextures: Int = 0x54

    /** `hkArray<ptr>` */
    public const val ExternalTextures: Int = 0x60

    /** `hkArray<ptr>` */
    public const val SkinBindings: Int = 0x6c

    /** `hkMatrix3` */
    public const val AppliedTransform: Int = 0x80
}

/**
 * `hkxSkinBinding`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSkinBinding`
 */
public object HkxSkinBinding {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val Mesh: Int = 0x8

    /** `hkArray<hkStringPtr>` */
    public const val NodeNames: Int = 0xc

    /** `hkArray<hkMatrix4>` */
    public const val BindPose: Int = 0x18

    /** `hkMatrix4` */
    public const val InitSkinTransform: Int = 0x30
}

/**
 * `hkxSparselyAnimatedBool`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedBool`
 */
public object HkxSparselyAnimatedBool {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<bool>` */
    public const val Bools: Int = 0x8

    /** `hkArray<float>` */
    public const val Times: Int = 0x14
}

/**
 * `hkxSparselyAnimatedEnum`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedInt` -> `hkxSparselyAnimatedEnum`
 */
public object HkxSparselyAnimatedEnum {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<int32>`, from `hkxSparselyAnimatedInt` */
    public const val Ints: Int = 0x8

    /** `hkArray<float>`, from `hkxSparselyAnimatedInt` */
    public const val Times: Int = 0x14

    /** `struct*` */
    public const val Enum: Int = 0x20
}

/**
 * `hkxSparselyAnimatedInt`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedInt`
 */
public object HkxSparselyAnimatedInt {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val Ints: Int = 0x8

    /** `hkArray<float>` */
    public const val Times: Int = 0x14
}

/**
 * `hkxSparselyAnimatedString`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedString`
 */
public object HkxSparselyAnimatedString {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkStringPtr>` */
    public const val Strings: Int = 0x8

    /** `hkArray<float>` */
    public const val Times: Int = 0x14
}

/**
 * `hkxTextureFile`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTextureFile`
 */
public object HkxTextureFile {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Filename: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0xc: Int = 0xc

    /** `hkStringPtr` */
    public const val OriginalFilename: Int = 0x10
}

/**
 * `hkxTextureInplace`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTextureInplace`
 */
public object HkxTextureInplace {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `char[4]` */
    public const val FileType: Int = 0x8

    /** `hkArray<uint8>` */
    public const val Data: Int = 0xc

    /** `hkStringPtr` */
    public const val Unnamed0x18: Int = 0x18

    /** `hkStringPtr` */
    public const val OriginalFilename: Int = 0x1c
}

/**
 * `hkxTriangleSelectionChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTriangleSelectionChannel`
 */
public object HkxTriangleSelectionChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val SelectedTriangles: Int = 0x8
}

/**
 * `hkxVertexBuffer`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexBuffer`
 */
public object HkxVertexBuffer {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct` */
    public const val Data: Int = 0x8

    /** `struct` */
    public const val Desc: Int = 0x5c
}

/**
 * `hkxVertexBufferVertexData`, 0x54 bytes.
 */
public object HkxVertexBufferVertexData {
    public const val ByteSize: Int = 0x54

    /** `hkArray<hkVector4>` */
    public const val VectorData: Int = 0x0

    /** `hkArray<float>` */
    public const val FloatData: Int = 0xc

    /** `hkArray<uint32>` */
    public const val Uint32Data: Int = 0x18

    /** `hkArray<uint16>` */
    public const val Uint16Data: Int = 0x24

    /** `hkArray<uint8>` */
    public const val Uint8Data: Int = 0x30

    /** `uint32` */
    public const val NumVerts: Int = 0x3c

    /** `uint32` */
    public const val VectorStride: Int = 0x40

    /** `uint32` */
    public const val FloatStride: Int = 0x44

    /** `uint32` */
    public const val Uint32Stride: Int = 0x48

    /** `uint32` */
    public const val Uint16Stride: Int = 0x4c

    /** `uint32` */
    public const val Uint8Stride: Int = 0x50
}

/**
 * `hkxVertexDescription`, 0xc bytes.
 */
public object HkxVertexDescription {
    public const val ByteSize: Int = 0xc

    /** `hkArray<struct>` */
    public const val Decls: Int = 0x0
}

/**
 * `hkxVertexDescriptionElementDecl`, 0x10 bytes.
 */
public object HkxVertexDescriptionElementDecl {
    public const val ByteSize: Int = 0x10

    /** `uint32` */
    public const val ByteOffset: Int = 0x0

    /** `enum:uint16` */
    public const val Type: Int = 0x4

    /** `enum:uint16` */
    public const val Usage: Int = 0x6

    /** `uint32` */
    public const val ByteStride: Int = 0x8

    /** `uint8` */
    public const val NumElements: Int = 0xc
}

/**
 * `hkxVertexFloatDataChannel`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexFloatDataChannel`
 */
public object HkxVertexFloatDataChannel {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<float>` */
    public const val PerVertexFloats: Int = 0x8

    /** `enum:uint8` */
    public const val Dimensions: Int = 0x14
}

/**
 * `hkxVertexIntDataChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexIntDataChannel`
 */
public object HkxVertexIntDataChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val PerVertexInts: Int = 0x8
}

/**
 * `hkxVertexSelectionChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexSelectionChannel`
 */
public object HkxVertexSelectionChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val SelectedVertices: Int = 0x8
}

/**
 * `hkxVertexVectorDataChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexVectorDataChannel`
 */
public object HkxVertexVectorDataChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkVector4>` */
    public const val PerVertexVectors: Int = 0x8
}
