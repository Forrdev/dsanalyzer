// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Scene: 44 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.runtime.ptde.havok

/**
 * `hkxAnimatedFloat`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedFloat`
 */
public object HkxAnimatedFloat {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<float>` */
    public const val floats: Int = 0x8

    /** `enum:uint8` */
    public const val hint: Int = 0x14
}

/**
 * `hkxAnimatedMatrix`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedMatrix`
 */
public object HkxAnimatedMatrix {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkMatrix4>` */
    public const val matrices: Int = 0x8

    /** `enum:uint8` */
    public const val hint: Int = 0x14
}

/**
 * `hkxAnimatedQuaternion`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedQuaternion`
 */
public object HkxAnimatedQuaternion {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkQuaternion>` */
    public const val quaternions: Int = 0x8
}

/**
 * `hkxAnimatedVector`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAnimatedVector`
 */
public object HkxAnimatedVector {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkVector4>` */
    public const val vectors: Int = 0x8

    /** `enum:uint8` */
    public const val hint: Int = 0x14
}

/**
 * `hkxAttribute`, 0x8 bytes.
 */
public object HkxAttribute {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0x0: Int = 0x0

    /** `struct*` */
    public const val value: Int = 0x4
}

/**
 * `hkxAttributeGroup`, 0x10 bytes.
 */
public object HkxAttributeGroup {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val unnamed0x0: Int = 0x0

    /** `hkArray<struct>` */
    public const val attributes: Int = 0x4
}

/**
 * `hkxAttributeHolder`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder`
 */
public object HkxAttributeHolder {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val attributeGroups: Int = 0x8
}

/**
 * `hkxCamera`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxCamera`
 */
public object HkxCamera {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkVector4` */
    public const val from: Int = 0x10

    /** `hkVector4` */
    public const val focus: Int = 0x20

    /** `hkVector4` */
    public const val up: Int = 0x30

    /** `float` */
    public const val fov: Int = 0x40

    /** `float` */
    public const val far: Int = 0x44

    /** `float` */
    public const val near: Int = 0x48

    /** `bool` */
    public const val leftHanded: Int = 0x4c
}

/**
 * `hkxEdgeSelectionChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEdgeSelectionChannel`
 */
public object HkxEdgeSelectionChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val selectedEdges: Int = 0x8
}

/**
 * `hkxEnum`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEnum`
 */
public object HkxEnum {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val items: Int = 0x8
}

/**
 * `hkxEnumItem`, 0x8 bytes.
 */
public object HkxEnumItem {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val value: Int = 0x0

    /** `hkStringPtr` */
    public const val unnamed0x4: Int = 0x4
}

/**
 * `hkxEnvironment`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxEnvironment`
 */
public object HkxEnvironment {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val variables: Int = 0x8
}

/**
 * `hkxEnvironmentVariable`, 0x8 bytes.
 */
public object HkxEnvironmentVariable {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0x0: Int = 0x0

    /** `hkStringPtr` */
    public const val value: Int = 0x4
}

/**
 * `hkxIndexBuffer`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxIndexBuffer`
 */
public object HkxIndexBuffer {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8` */
    public const val indexType: Int = 0x8

    /** `hkArray<uint16>` */
    public const val indices16: Int = 0xc

    /** `hkArray<uint32>` */
    public const val indices32: Int = 0x18

    /** `uint32` */
    public const val vertexBaseOffset: Int = 0x24

    /** `uint32` */
    public const val length: Int = 0x28
}

/**
 * `hkxLight`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxLight`
 */
public object HkxLight {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8` */
    public const val type: Int = 0x8

    /** `hkVector4` */
    public const val position: Int = 0x10

    /** `hkVector4` */
    public const val direction: Int = 0x20

    /** `uint32` */
    public const val color: Int = 0x30

    /** `float` */
    public const val angle: Int = 0x34
}

/**
 * `hkxMaterial`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxMaterial`
 */
public object HkxMaterial {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val attributeGroups: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0x14: Int = 0x14

    /** `hkArray<struct>` */
    public const val stages: Int = 0x18

    /** `hkVector4` */
    public const val diffuseColor: Int = 0x30

    /** `hkVector4` */
    public const val ambientColor: Int = 0x40

    /** `hkVector4` */
    public const val specularColor: Int = 0x50

    /** `hkVector4` */
    public const val emissiveColor: Int = 0x60

    /** `hkArray<ptr>` */
    public const val subMaterials: Int = 0x70

    /** `struct*` */
    public const val extraData: Int = 0x7c

    /** `hkArray<struct>` */
    public const val properties: Int = 0x80
}

/**
 * `hkxMaterialEffect`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialEffect`
 */
public object HkxMaterialEffect {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val unnamed0x8: Int = 0x8

    /** `enum:uint8` */
    public const val type: Int = 0xc

    /** `hkArray<uint8>` */
    public const val data: Int = 0x10
}

/**
 * `hkxMaterialProperty`, 0x8 bytes.
 */
public object HkxMaterialProperty {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val key: Int = 0x0

    /** `uint32` */
    public const val value: Int = 0x4
}

/**
 * `hkxMaterialShader`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialShader`
 */
public object HkxMaterialShader {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val unnamed0x8: Int = 0x8

    /** `enum:uint8` */
    public const val type: Int = 0xc

    /** `hkStringPtr` */
    public const val vertexEntryName: Int = 0x10

    /** `hkStringPtr` */
    public const val geomEntryName: Int = 0x14

    /** `hkStringPtr` */
    public const val pixelEntryName: Int = 0x18

    /** `hkArray<uint8>` */
    public const val data: Int = 0x1c
}

/**
 * `hkxMaterialShaderSet`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMaterialShaderSet`
 */
public object HkxMaterialShaderSet {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val shaders: Int = 0x8
}

/**
 * `hkxMaterialTextureStage`, 0xc bytes.
 */
public object HkxMaterialTextureStage {
    public const val ByteSize: Int = 0xc

    /** `struct*` */
    public const val texture: Int = 0x0

    /** `enum:int32` */
    public const val usageHint: Int = 0x4

    /** `int32` */
    public const val tcoordChannel: Int = 0x8
}

/**
 * `hkxMesh`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMesh`
 */
public object HkxMesh {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val sections: Int = 0x8

    /** `hkArray<ptr>` */
    public const val userChannelInfos: Int = 0x14
}

/**
 * `hkxMeshSection`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxMeshSection`
 */
public object HkxMeshSection {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val vertexBuffer: Int = 0x8

    /** `hkArray<ptr>` */
    public const val indexBuffers: Int = 0xc

    /** `struct*` */
    public const val material: Int = 0x18

    /** `hkArray<ptr>` */
    public const val userChannels: Int = 0x1c
}

/**
 * `hkxMeshUserChannelInfo`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxMeshUserChannelInfo`
 */
public object HkxMeshUserChannelInfo {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val attributeGroups: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0x14: Int = 0x14

    /** `hkStringPtr` */
    public const val className: Int = 0x18
}

/**
 * `hkxNode`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxNode`
 */
public object HkxNode {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val attributeGroups: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val `object`: Int = 0x18

    /** `hkArray<hkMatrix4>` */
    public const val keyFrames: Int = 0x1c

    /** `hkArray<ptr>` */
    public const val children: Int = 0x28

    /** `hkArray<struct>` */
    public const val annotations: Int = 0x34

    /** `hkStringPtr` */
    public const val userProperties: Int = 0x40

    /** `bool` */
    public const val selected: Int = 0x44
}

/**
 * `hkxNodeAnnotationData`, 0x8 bytes.
 */
public object HkxNodeAnnotationData {
    public const val ByteSize: Int = 0x8

    /** `float` */
    public const val time: Int = 0x0

    /** `hkStringPtr` */
    public const val description: Int = 0x4
}

/**
 * `hkxNodeSelectionSet`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxAttributeHolder` -> `hkxNodeSelectionSet`
 */
public object HkxNodeSelectionSet {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>`, from `hkxAttributeHolder` */
    public const val attributeGroups: Int = 0x8

    /** `hkArray<ptr>` */
    public const val selectedNodes: Int = 0x14

    /** `hkStringPtr` */
    public const val unnamed0x20: Int = 0x20
}

/**
 * `hkxScene`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxScene`
 */
public object HkxScene {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val modeller: Int = 0x8

    /** `hkStringPtr` */
    public const val asset: Int = 0xc

    /** `float` */
    public const val sceneLength: Int = 0x10

    /** `struct*` */
    public const val rootNode: Int = 0x14

    /** `hkArray<ptr>` */
    public const val selectionSets: Int = 0x18

    /** `hkArray<ptr>` */
    public const val cameras: Int = 0x24

    /** `hkArray<ptr>` */
    public const val lights: Int = 0x30

    /** `hkArray<ptr>` */
    public const val meshes: Int = 0x3c

    /** `hkArray<ptr>` */
    public const val materials: Int = 0x48

    /** `hkArray<ptr>` */
    public const val inplaceTextures: Int = 0x54

    /** `hkArray<ptr>` */
    public const val externalTextures: Int = 0x60

    /** `hkArray<ptr>` */
    public const val skinBindings: Int = 0x6c

    /** `hkMatrix3` */
    public const val appliedTransform: Int = 0x80
}

/**
 * `hkxSkinBinding`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSkinBinding`
 */
public object HkxSkinBinding {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val mesh: Int = 0x8

    /** `hkArray<hkStringPtr>` */
    public const val nodeNames: Int = 0xc

    /** `hkArray<hkMatrix4>` */
    public const val bindPose: Int = 0x18

    /** `hkMatrix4` */
    public const val initSkinTransform: Int = 0x30
}

/**
 * `hkxSparselyAnimatedBool`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedBool`
 */
public object HkxSparselyAnimatedBool {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<bool>` */
    public const val bools: Int = 0x8

    /** `hkArray<float>` */
    public const val times: Int = 0x14
}

/**
 * `hkxSparselyAnimatedEnum`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedInt` -> `hkxSparselyAnimatedEnum`
 */
public object HkxSparselyAnimatedEnum {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<int32>`, from `hkxSparselyAnimatedInt` */
    public const val ints: Int = 0x8

    /** `hkArray<float>`, from `hkxSparselyAnimatedInt` */
    public const val times: Int = 0x14

    /** `struct*` */
    public const val enum: Int = 0x20
}

/**
 * `hkxSparselyAnimatedInt`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedInt`
 */
public object HkxSparselyAnimatedInt {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val ints: Int = 0x8

    /** `hkArray<float>` */
    public const val times: Int = 0x14
}

/**
 * `hkxSparselyAnimatedString`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxSparselyAnimatedString`
 */
public object HkxSparselyAnimatedString {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkStringPtr>` */
    public const val strings: Int = 0x8

    /** `hkArray<float>` */
    public const val times: Int = 0x14
}

/**
 * `hkxTextureFile`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTextureFile`
 */
public object HkxTextureFile {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val filename: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0xc: Int = 0xc

    /** `hkStringPtr` */
    public const val originalFilename: Int = 0x10
}

/**
 * `hkxTextureInplace`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTextureInplace`
 */
public object HkxTextureInplace {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `char[4]` */
    public const val fileType: Int = 0x8

    /** `hkArray<uint8>` */
    public const val data: Int = 0xc

    /** `hkStringPtr` */
    public const val unnamed0x18: Int = 0x18

    /** `hkStringPtr` */
    public const val originalFilename: Int = 0x1c
}

/**
 * `hkxTriangleSelectionChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxTriangleSelectionChannel`
 */
public object HkxTriangleSelectionChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val selectedTriangles: Int = 0x8
}

/**
 * `hkxVertexBuffer`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexBuffer`
 */
public object HkxVertexBuffer {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct` */
    public const val data: Int = 0x8

    /** `struct` */
    public const val desc: Int = 0x5c
}

/**
 * `hkxVertexBufferVertexData`, 0x54 bytes.
 */
public object HkxVertexBufferVertexData {
    public const val ByteSize: Int = 0x54

    /** `hkArray<hkVector4>` */
    public const val vectorData: Int = 0x0

    /** `hkArray<float>` */
    public const val floatData: Int = 0xc

    /** `hkArray<uint32>` */
    public const val uint32Data: Int = 0x18

    /** `hkArray<uint16>` */
    public const val uint16Data: Int = 0x24

    /** `hkArray<uint8>` */
    public const val uint8Data: Int = 0x30

    /** `uint32` */
    public const val numVerts: Int = 0x3c

    /** `uint32` */
    public const val vectorStride: Int = 0x40

    /** `uint32` */
    public const val floatStride: Int = 0x44

    /** `uint32` */
    public const val uint32Stride: Int = 0x48

    /** `uint32` */
    public const val uint16Stride: Int = 0x4c

    /** `uint32` */
    public const val uint8Stride: Int = 0x50
}

/**
 * `hkxVertexDescription`, 0xc bytes.
 */
public object HkxVertexDescription {
    public const val ByteSize: Int = 0xc

    /** `hkArray<struct>` */
    public const val decls: Int = 0x0
}

/**
 * `hkxVertexDescriptionElementDecl`, 0x10 bytes.
 */
public object HkxVertexDescriptionElementDecl {
    public const val ByteSize: Int = 0x10

    /** `uint32` */
    public const val byteOffset: Int = 0x0

    /** `enum:uint16` */
    public const val type: Int = 0x4

    /** `enum:uint16` */
    public const val usage: Int = 0x6

    /** `uint32` */
    public const val byteStride: Int = 0x8

    /** `uint8` */
    public const val numElements: Int = 0xc
}

/**
 * `hkxVertexFloatDataChannel`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexFloatDataChannel`
 */
public object HkxVertexFloatDataChannel {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<float>` */
    public const val perVertexFloats: Int = 0x8

    /** `enum:uint8` */
    public const val dimensions: Int = 0x14
}

/**
 * `hkxVertexIntDataChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexIntDataChannel`
 */
public object HkxVertexIntDataChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val perVertexInts: Int = 0x8
}

/**
 * `hkxVertexSelectionChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexSelectionChannel`
 */
public object HkxVertexSelectionChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<int32>` */
    public const val selectedVertices: Int = 0x8
}

/**
 * `hkxVertexVectorDataChannel`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkxVertexVectorDataChannel`
 */
public object HkxVertexVectorDataChannel {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkVector4>` */
    public const val perVertexVectors: Int = 0x8
}
