// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Animation: 34 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.runtime.ptde.havok

/**
 * `hkaAnimatedReferenceFrame`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame`
 */
public object HkaAnimatedReferenceFrame {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkaAnimation`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation`
 */
public object HkaAnimation {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int32` */
    public const val type: Int = 0x8

    /** `float` */
    public const val duration: Int = 0xc

    /** `int32` */
    public const val numberOfTransformTracks: Int = 0x10

    /** `int32` */
    public const val numberOfFloatTracks: Int = 0x14

    /** `struct*` */
    public const val extractedMotion: Int = 0x18

    /** `hkArray<struct>` */
    public const val annotationTracks: Int = 0x1c
}

/**
 * `hkaAnimationBinding`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationBinding`
 */
public object HkaAnimationBinding {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val originalSkeletonName: Int = 0x8

    /** `struct*` */
    public const val animation: Int = 0xc

    /** `hkArray<int16>` */
    public const val transformTrackToBoneIndices: Int = 0x10

    /** `hkArray<int16>` */
    public const val floatTrackToFloatSlotIndices: Int = 0x1c

    /** `enum:int8` */
    public const val blendHint: Int = 0x28
}

/**
 * `hkaAnimationContainer`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationContainer`
 */
public object HkaAnimationContainer {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val skeletons: Int = 0x8

    /** `hkArray<ptr>` */
    public const val animations: Int = 0x14

    /** `hkArray<ptr>` */
    public const val bindings: Int = 0x20

    /** `hkArray<ptr>` */
    public const val attachments: Int = 0x2c

    /** `hkArray<ptr>` */
    public const val skins: Int = 0x38
}

/**
 * `hkaAnimationPreviewColorContainer`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationPreviewColorContainer`
 */
public object HkaAnimationPreviewColorContainer {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<uint32>` */
    public const val previewColor: Int = 0x8
}

/**
 * `hkaAnnotationTrack`, 0x10 bytes.
 */
public object HkaAnnotationTrack {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val trackName: Int = 0x0

    /** `hkArray<struct>` */
    public const val annotations: Int = 0x4
}

/**
 * `hkaAnnotationTrackAnnotation`, 0x8 bytes.
 */
public object HkaAnnotationTrackAnnotation {
    public const val ByteSize: Int = 0x8

    /** `float` */
    public const val time: Int = 0x0

    /** `hkStringPtr` */
    public const val text: Int = 0x4
}

/**
 * `hkaBone`, 0x8 bytes.
 */
public object HkaBone {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0x0: Int = 0x0

    /** `bool` */
    public const val lockTranslation: Int = 0x4
}

/**
 * `hkaBoneAttachment`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaBoneAttachment`
 */
public object HkaBoneAttachment {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val originalSkeletonName: Int = 0x8

    /** `hkMatrix4` */
    public const val boneFromAttachment: Int = 0x10

    /** `struct*` */
    public const val attachment: Int = 0x50

    /** `hkStringPtr` */
    public const val unnamed0x54: Int = 0x54

    /** `int16` */
    public const val boneIndex: Int = 0x58
}

/**
 * `hkaDefaultAnimatedReferenceFrame`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame`
 */
public object HkaDefaultAnimatedReferenceFrame {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkVector4` */
    public const val up: Int = 0x10

    /** `hkVector4` */
    public const val forward: Int = 0x20

    /** `float` */
    public const val duration: Int = 0x30

    /** `hkArray<hkVector4>` */
    public const val referenceFrameSamples: Int = 0x34
}

/**
 * `hkaDeltaCompressedAnimation`, 0x78 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaDeltaCompressedAnimation`
 */
public object HkaDeltaCompressedAnimation {
    public const val ByteSize: Int = 0x78

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val numberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val numberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val extractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val annotationTracks: Int = 0x1c

    /** `int32` */
    public const val numberOfPoses: Int = 0x28

    /** `int32` */
    public const val blockSize: Int = 0x2c

    /** `struct` */
    public const val qFormat: Int = 0x30

    /** `uint32` */
    public const val quantizedDataIdx: Int = 0x44

    /** `uint32` */
    public const val quantizedDataSize: Int = 0x48

    /** `uint32` */
    public const val staticMaskIdx: Int = 0x4c

    /** `uint32` */
    public const val staticMaskSize: Int = 0x50

    /** `uint32` */
    public const val staticDOFsIdx: Int = 0x54

    /** `uint32` */
    public const val staticDOFsSize: Int = 0x58

    /** `uint32` */
    public const val numStaticTransformDOFs: Int = 0x5c

    /** `uint32` */
    public const val numDynamicTransformDOFs: Int = 0x60

    /** `uint32` */
    public const val totalBlockSize: Int = 0x64

    /** `uint32` */
    public const val lastBlockSize: Int = 0x68

    /** `hkArray<uint8>` */
    public const val dataBuffer: Int = 0x6c
}

/**
 * `hkaDeltaCompressedAnimationQuantizationFormat`, 0x14 bytes.
 */
public object HkaDeltaCompressedAnimationQuantizationFormat {
    public const val ByteSize: Int = 0x14

    /** `uint8` */
    public const val maxBitWidth: Int = 0x0

    /** `uint8` */
    public const val preserved: Int = 0x1

    /** `uint32` */
    public const val numD: Int = 0x4

    /** `uint32` */
    public const val offsetIdx: Int = 0x8

    /** `uint32` */
    public const val scaleIdx: Int = 0xc

    /** `uint32` */
    public const val bitWidthIdx: Int = 0x10
}

/**
 * `hkaFootstepAnalysisInfo`, 0x98 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaFootstepAnalysisInfo`
 */
public object HkaFootstepAnalysisInfo {
    public const val ByteSize: Int = 0x98

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<char>` */
    public const val unnamed0x8: Int = 0x8

    /** `hkArray<char>` */
    public const val nameStrike: Int = 0x14

    /** `hkArray<char>` */
    public const val nameLift: Int = 0x20

    /** `hkArray<char>` */
    public const val nameLock: Int = 0x2c

    /** `hkArray<char>` */
    public const val nameUnlock: Int = 0x38

    /** `hkArray<float>` */
    public const val minPos: Int = 0x44

    /** `hkArray<float>` */
    public const val maxPos: Int = 0x50

    /** `hkArray<float>` */
    public const val minVel: Int = 0x5c

    /** `hkArray<float>` */
    public const val maxVel: Int = 0x68

    /** `hkArray<float>` */
    public const val allBonesDown: Int = 0x74

    /** `hkArray<float>` */
    public const val anyBonesDown: Int = 0x80

    /** `float` */
    public const val posTol: Int = 0x8c

    /** `float` */
    public const val velTol: Int = 0x90

    /** `float` */
    public const val duration: Int = 0x94
}

/**
 * `hkaFootstepAnalysisInfoContainer`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaFootstepAnalysisInfoContainer`
 */
public object HkaFootstepAnalysisInfoContainer {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val previewInfo: Int = 0x8
}

/**
 * `hkaInterleavedUncompressedAnimation`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaInterleavedUncompressedAnimation`
 */
public object HkaInterleavedUncompressedAnimation {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val numberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val numberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val extractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val annotationTracks: Int = 0x1c

    /** `hkArray<hkQsTransform>` */
    public const val transforms: Int = 0x28

    /** `hkArray<float>` */
    public const val floats: Int = 0x34
}

/**
 * `hkaKeyFrameHierarchyUtility`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkaKeyFrameHierarchyUtility {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkaKeyFrameHierarchyUtilityControlData`, 0x30 bytes.
 */
public object HkaKeyFrameHierarchyUtilityControlData {
    public const val ByteSize: Int = 0x30

    /** `float` */
    public const val hierarchyGain: Int = 0x0

    /** `float` */
    public const val velocityDamping: Int = 0x4

    /** `float` */
    public const val accelerationGain: Int = 0x8

    /** `float` */
    public const val velocityGain: Int = 0xc

    /** `float` */
    public const val positionGain: Int = 0x10

    /** `float` */
    public const val positionMaxLinearVelocity: Int = 0x14

    /** `float` */
    public const val positionMaxAngularVelocity: Int = 0x18

    /** `float` */
    public const val snapGain: Int = 0x1c

    /** `float` */
    public const val snapMaxLinearVelocity: Int = 0x20

    /** `float` */
    public const val snapMaxAngularVelocity: Int = 0x24

    /** `float` */
    public const val snapMaxLinearDistance: Int = 0x28

    /** `float` */
    public const val snapMaxAngularDistance: Int = 0x2c
}

/**
 * `hkaMeshBinding`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaMeshBinding`
 */
public object HkaMeshBinding {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val mesh: Int = 0x8

    /** `hkStringPtr` */
    public const val originalSkeletonName: Int = 0xc

    /** `struct*` */
    public const val skeleton: Int = 0x10

    /** `hkArray<struct>` */
    public const val mappings: Int = 0x14

    /** `hkArray<hkTransform>` */
    public const val boneFromSkinMeshTransforms: Int = 0x20
}

/**
 * `hkaMeshBindingMapping`, 0xc bytes.
 */
public object HkaMeshBindingMapping {
    public const val ByteSize: Int = 0xc

    /** `hkArray<int16>` */
    public const val mapping: Int = 0x0
}

/**
 * `hkaQuantizedAnimation`, 0x3c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaQuantizedAnimation`
 */
public object HkaQuantizedAnimation {
    public const val ByteSize: Int = 0x3c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val numberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val numberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val extractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val annotationTracks: Int = 0x1c

    /** `hkArray<uint8>` */
    public const val data: Int = 0x28

    /** `uint32` */
    public const val endian: Int = 0x34

    /** `void*` */
    public const val skeleton: Int = 0x38
}

/**
 * `hkaQuantizedAnimationTrackCompressionParams`, 0x10 bytes.
 */
public object HkaQuantizedAnimationTrackCompressionParams {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val rotationTolerance: Int = 0x0

    /** `float` */
    public const val translationTolerance: Int = 0x4

    /** `float` */
    public const val scaleTolerance: Int = 0x8

    /** `float` */
    public const val floatingTolerance: Int = 0xc
}

/**
 * `hkaRagdollInstance`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaRagdollInstance`
 */
public object HkaRagdollInstance {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val rigidBodies: Int = 0x8

    /** `hkArray<ptr>` */
    public const val constraints: Int = 0x14

    /** `hkArray<int32>` */
    public const val boneToRigidBodyMap: Int = 0x20

    /** `struct*` */
    public const val skeleton: Int = 0x2c
}

/**
 * `hkaSkeleton`, 0x54 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaSkeleton`
 */
public object HkaSkeleton {
    public const val ByteSize: Int = 0x54

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val unnamed0x8: Int = 0x8

    /** `hkArray<int16>` */
    public const val parentIndices: Int = 0xc

    /** `hkArray<struct>` */
    public const val bones: Int = 0x18

    /** `hkArray<hkQsTransform>` */
    public const val referencePose: Int = 0x24

    /** `hkArray<float>` */
    public const val referenceFloats: Int = 0x30

    /** `hkArray<hkStringPtr>` */
    public const val floatSlots: Int = 0x3c

    /** `hkArray<struct>` */
    public const val localFrames: Int = 0x48
}

/**
 * `hkaSkeletonLocalFrameOnBone`, 0x8 bytes.
 */
public object HkaSkeletonLocalFrameOnBone {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val localFrame: Int = 0x0

    /** `int32` */
    public const val boneIndex: Int = 0x4
}

/**
 * `hkaSkeletonMapper`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaSkeletonMapper`
 */
public object HkaSkeletonMapper {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct` */
    public const val mapping: Int = 0x10
}

/**
 * `hkaSkeletonMapperData`, 0x70 bytes.
 */
public object HkaSkeletonMapperData {
    public const val ByteSize: Int = 0x70

    /** `struct*` */
    public const val skeletonA: Int = 0x0

    /** `struct*` */
    public const val skeletonB: Int = 0x4

    /** `hkArray<struct>` */
    public const val simpleMappings: Int = 0x8

    /** `hkArray<struct>` */
    public const val chainMappings: Int = 0x14

    /** `hkArray<int16>` */
    public const val unmappedBones: Int = 0x20

    /** `hkQsTransform` */
    public const val extractedMotionMapping: Int = 0x30

    /** `bool` */
    public const val keepUnmappedLocal: Int = 0x60

    /** `enum:int32` */
    public const val mappingType: Int = 0x64
}

/**
 * `hkaSkeletonMapperDataChainMapping`, 0x70 bytes.
 */
public object HkaSkeletonMapperDataChainMapping {
    public const val ByteSize: Int = 0x70

    /** `int16` */
    public const val startBoneA: Int = 0x0

    /** `int16` */
    public const val endBoneA: Int = 0x2

    /** `int16` */
    public const val startBoneB: Int = 0x4

    /** `int16` */
    public const val endBoneB: Int = 0x6

    /** `hkQsTransform` */
    public const val startAFromBTransform: Int = 0x10

    /** `hkQsTransform` */
    public const val endAFromBTransform: Int = 0x40
}

/**
 * `hkaSkeletonMapperDataSimpleMapping`, 0x40 bytes.
 */
public object HkaSkeletonMapperDataSimpleMapping {
    public const val ByteSize: Int = 0x40

    /** `int16` */
    public const val boneA: Int = 0x0

    /** `int16` */
    public const val boneB: Int = 0x2

    /** `hkQsTransform` */
    public const val aFromBTransform: Int = 0x10
}

/**
 * `hkaSplineCompressedAnimation`, 0x84 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaSplineCompressedAnimation`
 */
public object HkaSplineCompressedAnimation {
    public const val ByteSize: Int = 0x84

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val numberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val numberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val extractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val annotationTracks: Int = 0x1c

    /** `int32` */
    public const val numFrames: Int = 0x28

    /** `int32` */
    public const val numBlocks: Int = 0x2c

    /** `int32` */
    public const val maxFramesPerBlock: Int = 0x30

    /** `int32` */
    public const val maskAndQuantizationSize: Int = 0x34

    /** `float` */
    public const val blockDuration: Int = 0x38

    /** `float` */
    public const val blockInverseDuration: Int = 0x3c

    /** `float` */
    public const val frameDuration: Int = 0x40

    /** `hkArray<uint32>` */
    public const val blockOffsets: Int = 0x44

    /** `hkArray<uint32>` */
    public const val floatBlockOffsets: Int = 0x50

    /** `hkArray<uint32>` */
    public const val transformOffsets: Int = 0x5c

    /** `hkArray<uint32>` */
    public const val floatOffsets: Int = 0x68

    /** `hkArray<uint8>` */
    public const val data: Int = 0x74

    /** `int32` */
    public const val endian: Int = 0x80
}

/**
 * `hkaSplineCompressedAnimationAnimationCompressionParams`, 0x4 bytes.
 */
public object HkaSplineCompressedAnimationAnimationCompressionParams {
    public const val ByteSize: Int = 0x4

    /** `uint16` */
    public const val maxFramesPerBlock: Int = 0x0

    /** `bool` */
    public const val enableSampleSingleTracks: Int = 0x2
}

/**
 * `hkaSplineCompressedAnimationTrackCompressionParams`, 0x1c bytes.
 */
public object HkaSplineCompressedAnimationTrackCompressionParams {
    public const val ByteSize: Int = 0x1c

    /** `float` */
    public const val rotationTolerance: Int = 0x0

    /** `float` */
    public const val translationTolerance: Int = 0x4

    /** `float` */
    public const val scaleTolerance: Int = 0x8

    /** `float` */
    public const val floatingTolerance: Int = 0xc

    /** `uint16` */
    public const val rotationDegree: Int = 0x10

    /** `uint16` */
    public const val translationDegree: Int = 0x12

    /** `uint16` */
    public const val scaleDegree: Int = 0x14

    /** `uint16` */
    public const val floatingDegree: Int = 0x16

    /** `enum:uint8` */
    public const val rotationQuantizationType: Int = 0x18

    /** `enum:uint8` */
    public const val translationQuantizationType: Int = 0x19

    /** `enum:uint8` */
    public const val scaleQuantizationType: Int = 0x1a

    /** `enum:uint8` */
    public const val floatQuantizationType: Int = 0x1b
}

/**
 * `hkaWaveletCompressedAnimation`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaWaveletCompressedAnimation`
 */
public object HkaWaveletCompressedAnimation {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val numberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val numberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val extractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val annotationTracks: Int = 0x1c

    /** `int32` */
    public const val numberOfPoses: Int = 0x28

    /** `int32` */
    public const val blockSize: Int = 0x2c

    /** `struct` */
    public const val qFormat: Int = 0x30

    /** `uint32` */
    public const val staticMaskIdx: Int = 0x44

    /** `uint32` */
    public const val staticDOFsIdx: Int = 0x48

    /** `uint32` */
    public const val numStaticTransformDOFs: Int = 0x4c

    /** `uint32` */
    public const val numDynamicTransformDOFs: Int = 0x50

    /** `uint32` */
    public const val blockIndexIdx: Int = 0x54

    /** `uint32` */
    public const val blockIndexSize: Int = 0x58

    /** `uint32` */
    public const val quantizedDataIdx: Int = 0x5c

    /** `uint32` */
    public const val quantizedDataSize: Int = 0x60

    /** `hkArray<uint8>` */
    public const val dataBuffer: Int = 0x64
}

/**
 * `hkaWaveletCompressedAnimationCompressionParams`, 0x24 bytes.
 */
public object HkaWaveletCompressedAnimationCompressionParams {
    public const val ByteSize: Int = 0x24

    /** `uint16` */
    public const val quantizationBits: Int = 0x0

    /** `uint16` */
    public const val blockSize: Int = 0x2

    /** `uint16` */
    public const val preserve: Int = 0x4

    /** `float` */
    public const val truncProp: Int = 0x8

    /** `bool` */
    public const val useOldStyleTruncation: Int = 0xc

    /** `float` */
    public const val absolutePositionTolerance: Int = 0x10

    /** `float` */
    public const val relativePositionTolerance: Int = 0x14

    /** `float` */
    public const val rotationTolerance: Int = 0x18

    /** `float` */
    public const val scaleTolerance: Int = 0x1c

    /** `float` */
    public const val absoluteFloatTolerance: Int = 0x20
}

/**
 * `hkaWaveletCompressedAnimationQuantizationFormat`, 0x14 bytes.
 */
public object HkaWaveletCompressedAnimationQuantizationFormat {
    public const val ByteSize: Int = 0x14

    /** `uint8` */
    public const val maxBitWidth: Int = 0x0

    /** `uint8` */
    public const val preserved: Int = 0x1

    /** `uint32` */
    public const val numD: Int = 0x4

    /** `uint32` */
    public const val offsetIdx: Int = 0x8

    /** `uint32` */
    public const val scaleIdx: Int = 0xc

    /** `uint32` */
    public const val bitWidthIdx: Int = 0x10
}
