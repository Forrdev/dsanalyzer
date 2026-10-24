// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateDSRHavokLayouts
//
// Source: tools/havok/dsr-reflection.tsv, extracted from DarkSoulsRemastered.exe (hk_2015.1.0-r1, 64-bit)
//
// Scene: 50 of 866 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.dsr

/**
 * `hkxAnimatedFloat`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedFloat`
 */
public object HkxAnimatedFloat {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val Floats: Int = 0x10

    /** `enum:uint8` */
    public const val Hint: Int = 0x20
}

/**
 * `hkxAnimatedMatrix`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedMatrix`
 */
public object HkxAnimatedMatrix {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val Matrices: Int = 0x10

    /** `enum:uint8` */
    public const val Hint: Int = 0x20
}

/**
 * `hkxAnimatedQuaternion`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedQuaternion`
 */
public object HkxAnimatedQuaternion {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val Quaternions: Int = 0x10
}

/**
 * `hkxAnimatedVector`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedVector`
 */
public object HkxAnimatedVector {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val Vectors: Int = 0x10

    /** `enum:uint8` */
    public const val Hint: Int = 0x20
}

/**
 * `hkxAttribute`, 0x10 bytes.
 */
public object HkxAttribute {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `struct*` */
    public const val Value: Int = 0x8
}

/**
 * `hkxAttributeGroup`, 0x18 bytes.
 */
public object HkxAttributeGroup {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkArray<struct>` */
    public const val Attributes: Int = 0x8
}

/**
 * `hkxAttributeHolder`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder`
 */
public object HkxAttributeHolder {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val AttributeGroups: Int = 0x10
}

/**
 * `hkxBlob`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxBlob`
 */
public object HkxBlob {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x10
}

/**
 * `hkxBlobMeshShape`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkxBlobMeshShape`
 */
public object HkxBlobMeshShape {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Blob: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x30
}

/**
 * `hkxCamera`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxCamera`
 */
public object HkxCamera {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

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
 * `hkxEdgeSelectionChannel`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEdgeSelectionChannel`
 */
public object HkxEdgeSelectionChannel {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val SelectedEdges: Int = 0x10
}

/**
 * `hkxEnum`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEnum`
 */
public object HkxEnum {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Items: Int = 0x10
}

/**
 * `hkxEnum::Item`, 0x10 bytes.
 */
public object HkxEnumItem {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val Value: Int = 0x0

    /** `hkStringPtr` */
    public const val Name: Int = 0x8
}

/**
 * `hkxEnvironment`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEnvironment`
 */
public object HkxEnvironment {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Variables: Int = 0x10
}

/**
 * `hkxEnvironment::Variable`, 0x10 bytes.
 */
public object HkxEnvironmentVariable {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkStringPtr` */
    public const val Value: Int = 0x8
}

/**
 * `hkxIndexBuffer`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxIndexBuffer`
 */
public object HkxIndexBuffer {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8` */
    public const val IndexType: Int = 0x10

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x18

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x28

    /** `uint32` */
    public const val VertexBaseOffset: Int = 0x38

    /** `uint32` */
    public const val Length: Int = 0x3c
}

/**
 * `hkxLight`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxLight`
 */
public object HkxLight {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8` */
    public const val Type: Int = 0x10

    /** `hkVector4` */
    public const val Position: Int = 0x20

    /** `hkVector4` */
    public const val Direction: Int = 0x30

    /** `uint32` */
    public const val Color: Int = 0x40

    /** `float` */
    public const val Angle: Int = 0x44

    /** `float` */
    public const val Range: Int = 0x48

    /** `float` */
    public const val FadeStart: Int = 0x4c

    /** `float` */
    public const val FadeEnd: Int = 0x50

    /** `int16` */
    public const val DecayRate: Int = 0x54

    /** `float` */
    public const val Intensity: Int = 0x58

    /** `bool` */
    public const val ShadowCaster: Int = 0x5c
}

/**
 * `hkxMaterial`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxMaterial`
 */
public object HkxMaterial {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x20

    /** `hkArray<struct>` */
    public const val Stages: Int = 0x28

    /** `hkVector4` */
    public const val DiffuseColor: Int = 0x40

    /** `hkVector4` */
    public const val AmbientColor: Int = 0x50

    /** `hkVector4` */
    public const val SpecularColor: Int = 0x60

    /** `hkVector4` */
    public const val EmissiveColor: Int = 0x70

    /** `hkArray<ptr>` */
    public const val SubMaterials: Int = 0x80

    /** `struct*` */
    public const val ExtraData: Int = 0x90

    /** `float[2]` */
    public const val UvMapScale: Int = 0x98

    /** `float[2]` */
    public const val UvMapOffset: Int = 0xa0

    /** `float` */
    public const val UvMapRotation: Int = 0xa8

    /** `enum:uint32` */
    public const val UvMapAlgorithm: Int = 0xac

    /** `float` */
    public const val SpecularMultiplier: Int = 0xb0

    /** `float` */
    public const val SpecularExponent: Int = 0xb4

    /** `enum:uint8` */
    public const val Transparency: Int = 0xb8

    /** `hkUlong` */
    public const val UserData: Int = 0xc0

    /** `hkArray<struct>` */
    public const val Properties: Int = 0xc8
}

/**
 * `hkxMaterial::Property`, 0x8 bytes.
 */
public object HkxMaterialProperty {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val Key: Int = 0x0

    /** `uint32` */
    public const val Value: Int = 0x4
}

/**
 * `hkxMaterial::TextureStage`, 0x10 bytes.
 */
public object HkxMaterialTextureStage {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Texture: Int = 0x0

    /** `enum:int32` */
    public const val UsageHint: Int = 0x8

    /** `int32` */
    public const val TcoordChannel: Int = 0xc
}

/**
 * `hkxMaterialEffect`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialEffect`
 */
public object HkxMaterialEffect {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10

    /** `enum:uint8` */
    public const val Type: Int = 0x18

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x20
}

/**
 * `hkxMaterialShader`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialShader`
 */
public object HkxMaterialShader {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10

    /** `enum:uint8` */
    public const val Type: Int = 0x18

    /** `hkStringPtr` */
    public const val VertexEntryName: Int = 0x20

    /** `hkStringPtr` */
    public const val GeomEntryName: Int = 0x28

    /** `hkStringPtr` */
    public const val PixelEntryName: Int = 0x30

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x38
}

/**
 * `hkxMaterialShaderSet`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialShaderSet`
 */
public object HkxMaterialShaderSet {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Shaders: Int = 0x10
}

/**
 * `hkxMesh`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMesh`
 */
public object HkxMesh {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Sections: Int = 0x10

    /** `hkArray<ptr>` */
    public const val UserChannelInfos: Int = 0x20
}

/**
 * `hkxMesh::UserChannelInfo`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxMesh::UserChannelInfo`
 */
public object HkxMeshUserChannelInfo {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x20

    /** `hkStringPtr` */
    public const val ClassName: Int = 0x28
}

/**
 * `hkxMeshSection`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMeshSection`
 */
public object HkxMeshSection {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val VertexBuffer: Int = 0x10

    /** `hkArray<ptr>` */
    public const val IndexBuffers: Int = 0x18

    /** `struct*` */
    public const val Material: Int = 0x28

    /** `hkArray<ptr>` */
    public const val UserChannels: Int = 0x30

    /** `hkArray<ptr>` */
    public const val VertexAnimations: Int = 0x40

    /** `hkArray<float>` */
    public const val LinearKeyFrameHints: Int = 0x50

    /** `hkArray<struct>` */
    public const val BoneMatrixMap: Int = 0x60
}

/**
 * `hkxNode`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxNode`
 */
public object HkxNode {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x20

    /** `struct` */
    public const val Uuid: Int = 0x28

    /** `struct*` */
    public const val Object: Int = 0x38

    /** `hkArray<hkMatrix4>` */
    public const val KeyFrames: Int = 0x40

    /** `hkArray<ptr>` */
    public const val Children: Int = 0x50

    /** `hkArray<struct>` */
    public const val Annotations: Int = 0x60

    /** `hkArray<float>` */
    public const val LinearKeyFrameHints: Int = 0x70

    /** `hkStringPtr` */
    public const val UserProperties: Int = 0x80

    /** `bool` */
    public const val Selected: Int = 0x88

    /** `bool` */
    public const val Bone: Int = 0x89
}

/**
 * `hkxNode::AnnotationData`, 0x10 bytes.
 */
public object HkxNodeAnnotationData {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val Time: Int = 0x0

    /** `hkStringPtr` */
    public const val Description: Int = 0x8
}

/**
 * `hkxNodeSelectionSet`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxNodeSelectionSet`
 */
public object HkxNodeSelectionSet {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val AttributeGroups: Int = 0x10

    /** `hkArray<ptr>` */
    public const val SelectedNodes: Int = 0x20

    /** `hkStringPtr` */
    public const val Name: Int = 0x30
}

/**
 * `hkxScene`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxScene`
 */
public object HkxScene {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Modeller: Int = 0x10

    /** `hkStringPtr` */
    public const val Asset: Int = 0x18

    /** `float` */
    public const val SceneLength: Int = 0x20

    /** `uint32` */
    public const val NumFrames: Int = 0x24

    /** `struct*` */
    public const val RootNode: Int = 0x28

    /** `hkArray<ptr>` */
    public const val SelectionSets: Int = 0x30

    /** `hkArray<ptr>` */
    public const val Cameras: Int = 0x40

    /** `hkArray<ptr>` */
    public const val Lights: Int = 0x50

    /** `hkArray<ptr>` */
    public const val Meshes: Int = 0x60

    /** `hkArray<ptr>` */
    public const val Materials: Int = 0x70

    /** `hkArray<ptr>` */
    public const val InplaceTextures: Int = 0x80

    /** `hkArray<ptr>` */
    public const val ExternalTextures: Int = 0x90

    /** `hkArray<ptr>` */
    public const val SkinBindings: Int = 0xa0

    /** `hkArray<ptr>` */
    public const val Splines: Int = 0xb0

    /** `hkMatrix3` */
    public const val AppliedTransform: Int = 0xc0
}

/**
 * `hkxSkinBinding`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSkinBinding`
 */
public object HkxSkinBinding {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Mesh: Int = 0x10

    /** `hkArray<hkStringPtr>` */
    public const val NodeNames: Int = 0x18

    /** `hkArray<hkMatrix4>` */
    public const val BindPose: Int = 0x28

    /** `hkMatrix4` */
    public const val InitSkinTransform: Int = 0x40
}

/**
 * `hkxSparselyAnimatedBool`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedBool`
 */
public object HkxSparselyAnimatedBool {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<bool>` */
    public const val Bools: Int = 0x10

    /** `hkArray<float>` */
    public const val Times: Int = 0x20
}

/**
 * `hkxSparselyAnimatedEnum`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedInt` -> `hkxSparselyAnimatedEnum`
 */
public object HkxSparselyAnimatedEnum {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>`, from `hkxSparselyAnimatedInt` */
    public const val Ints: Int = 0x10

    /** `hkArray<float>`, from `hkxSparselyAnimatedInt` */
    public const val Times: Int = 0x20

    /** `struct*` */
    public const val Enum: Int = 0x30
}

/**
 * `hkxSparselyAnimatedInt`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedInt`
 */
public object HkxSparselyAnimatedInt {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val Ints: Int = 0x10

    /** `hkArray<float>` */
    public const val Times: Int = 0x20
}

/**
 * `hkxSparselyAnimatedString`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedString`
 */
public object HkxSparselyAnimatedString {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<hkStringPtr>` */
    public const val Strings: Int = 0x10

    /** `hkArray<float>` */
    public const val Times: Int = 0x20
}

/**
 * `hkxSpline`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSpline`
 */
public object HkxSpline {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val ControlPoints: Int = 0x10

    /** `bool` */
    public const val IsClosed: Int = 0x20
}

/**
 * `hkxSpline::ControlPoint`, 0x40 bytes.
 */
public object HkxSplineControlPoint {
    public const val ByteSize: Int = 0x40

    /** `hkVector4` */
    public const val Position: Int = 0x0

    /** `hkVector4` */
    public const val TangentIn: Int = 0x10

    /** `hkVector4` */
    public const val TangentOut: Int = 0x20

    /** `enum:uint8` */
    public const val InType: Int = 0x30

    /** `enum:uint8` */
    public const val OutType: Int = 0x31
}

/**
 * `hkxTextureFile`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTextureFile`
 */
public object HkxTextureFile {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Filename: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x18

    /** `hkStringPtr` */
    public const val OriginalFilename: Int = 0x20
}

/**
 * `hkxTextureInplace`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTextureInplace`
 */
public object HkxTextureInplace {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint8[4]` */
    public const val FileType: Int = 0x10

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x28

    /** `hkStringPtr` */
    public const val OriginalFilename: Int = 0x30
}

/**
 * `hkxTriangleSelectionChannel`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTriangleSelectionChannel`
 */
public object HkxTriangleSelectionChannel {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val SelectedTriangles: Int = 0x10
}

/**
 * `hkxVertexAnimation`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexAnimation`
 */
public object HkxVertexAnimation {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val Time: Int = 0x10

    /** `struct` */
    public const val VertData: Int = 0x18

    /** `hkArray<int32>` */
    public const val VertexIndexMap: Int = 0xa0

    /** `hkArray<struct>` */
    public const val ComponentMap: Int = 0xb0
}

/**
 * `hkxVertexAnimation::UsageMap`, 0x4 bytes.
 */
public object HkxVertexAnimationUsageMap {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16` */
    public const val Use: Int = 0x0

    /** `uint8` */
    public const val UseIndexOrig: Int = 0x2

    /** `uint8` */
    public const val UseIndexLocal: Int = 0x3
}

/**
 * `hkxVertexBuffer`, 0x88 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexBuffer`
 */
public object HkxVertexBuffer {
    public const val ByteSize: Int = 0x88

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Data: Int = 0x10

    /** `struct` */
    public const val Desc: Int = 0x78
}

/**
 * `hkxVertexBuffer::VertexData`, 0x68 bytes.
 */
public object HkxVertexBufferVertexData {
    public const val ByteSize: Int = 0x68

    /** `hkArray<uint32>` */
    public const val VectorData: Int = 0x0

    /** `hkArray<uint32>` */
    public const val FloatData: Int = 0x10

    /** `hkArray<uint32>` */
    public const val Uint32Data: Int = 0x20

    /** `hkArray<uint16>` */
    public const val Uint16Data: Int = 0x30

    /** `hkArray<uint8>` */
    public const val Uint8Data: Int = 0x40

    /** `uint32` */
    public const val NumVerts: Int = 0x50

    /** `uint32` */
    public const val VectorStride: Int = 0x54

    /** `uint32` */
    public const val FloatStride: Int = 0x58

    /** `uint32` */
    public const val Uint32Stride: Int = 0x5c

    /** `uint32` */
    public const val Uint16Stride: Int = 0x60

    /** `uint32` */
    public const val Uint8Stride: Int = 0x64
}

/**
 * `hkxVertexDescription`, 0x10 bytes.
 */
public object HkxVertexDescription {
    public const val ByteSize: Int = 0x10

    /** `hkArray<struct>` */
    public const val Decls: Int = 0x0
}

/**
 * `hkxVertexDescription::ElementDecl`, 0x18 bytes.
 */
public object HkxVertexDescriptionElementDecl {
    public const val ByteSize: Int = 0x18

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

    /** `hkStringPtr` */
    public const val ChannelID: Int = 0x10
}

/**
 * `hkxVertexFloatDataChannel`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexFloatDataChannel`
 */
public object HkxVertexFloatDataChannel {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val PerVertexFloats: Int = 0x10

    /** `enum:uint8` */
    public const val Dimensions: Int = 0x20
}

/**
 * `hkxVertexIntDataChannel`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexIntDataChannel`
 */
public object HkxVertexIntDataChannel {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val PerVertexInts: Int = 0x10
}

/**
 * `hkxVertexSelectionChannel`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexSelectionChannel`
 */
public object HkxVertexSelectionChannel {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val SelectedVertices: Int = 0x10
}

/**
 * `hkxVertexVectorDataChannel`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexVectorDataChannel`
 */
public object HkxVertexVectorDataChannel {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val PerVertexVectors: Int = 0x10
}
