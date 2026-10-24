// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generatePTDEHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Animation: 34 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.ptde

/**
 * `hkaAnimatedReferenceFrame`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame`
 */
public object HkaAnimatedReferenceFrame {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkaAnimation`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation`
 */
public object HkaAnimation {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int32` */
    public const val Type: Int = 0x8

    /** `float` */
    public const val Duration: Int = 0xc

    /** `int32` */
    public const val NumberOfTransformTracks: Int = 0x10

    /** `int32` */
    public const val NumberOfFloatTracks: Int = 0x14

    /** `struct*` */
    public const val ExtractedMotion: Int = 0x18

    /** `hkArray<struct>` */
    public const val AnnotationTracks: Int = 0x1c
}

/**
 * `hkaAnimationBinding`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationBinding`
 */
public object HkaAnimationBinding {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val OriginalSkeletonName: Int = 0x8

    /** `struct*` */
    public const val Animation: Int = 0xc

    /** `hkArray<int16>` */
    public const val TransformTrackToBoneIndices: Int = 0x10

    /** `hkArray<int16>` */
    public const val FloatTrackToFloatSlotIndices: Int = 0x1c

    /** `enum:int8` */
    public const val BlendHint: Int = 0x28
}

/**
 * `hkaAnimationContainer`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationContainer`
 */
public object HkaAnimationContainer {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val Skeletons: Int = 0x8

    /** `hkArray<ptr>` */
    public const val Animations: Int = 0x14

    /** `hkArray<ptr>` */
    public const val Bindings: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Attachments: Int = 0x2c

    /** `hkArray<ptr>` */
    public const val Skins: Int = 0x38
}

/**
 * `hkaAnimationPreviewColorContainer`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationPreviewColorContainer`
 */
public object HkaAnimationPreviewColorContainer {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<uint32>` */
    public const val PreviewColor: Int = 0x8
}

/**
 * `hkaAnnotationTrack`, 0x10 bytes.
 */
public object HkaAnnotationTrack {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val TrackName: Int = 0x0

    /** `hkArray<struct>` */
    public const val Annotations: Int = 0x4
}

/**
 * `hkaAnnotationTrackAnnotation`, 0x8 bytes.
 */
public object HkaAnnotationTrackAnnotation {
    public const val ByteSize: Int = 0x8

    /** `float` */
    public const val Time: Int = 0x0

    /** `hkStringPtr` */
    public const val Text: Int = 0x4
}

/**
 * `hkaBone`, 0x8 bytes.
 */
public object HkaBone {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0x0: Int = 0x0

    /** `bool` */
    public const val LockTranslation: Int = 0x4
}

/**
 * `hkaBoneAttachment`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaBoneAttachment`
 */
public object HkaBoneAttachment {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val OriginalSkeletonName: Int = 0x8

    /** `hkMatrix4` */
    public const val BoneFromAttachment: Int = 0x10

    /** `struct*` */
    public const val Attachment: Int = 0x50

    /** `hkStringPtr` */
    public const val Unnamed0x54: Int = 0x54

    /** `int16` */
    public const val BoneIndex: Int = 0x58
}

/**
 * `hkaDefaultAnimatedReferenceFrame`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame`
 */
public object HkaDefaultAnimatedReferenceFrame {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkVector4` */
    public const val Up: Int = 0x10

    /** `hkVector4` */
    public const val Forward: Int = 0x20

    /** `float` */
    public const val Duration: Int = 0x30

    /** `hkArray<hkVector4>` */
    public const val ReferenceFrameSamples: Int = 0x34
}

/**
 * `hkaDeltaCompressedAnimation`, 0x78 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaDeltaCompressedAnimation`
 */
public object HkaDeltaCompressedAnimation {
    public const val ByteSize: Int = 0x78

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x1c

    /** `int32` */
    public const val NumberOfPoses: Int = 0x28

    /** `int32` */
    public const val BlockSize: Int = 0x2c

    /** `struct` */
    public const val QFormat: Int = 0x30

    /** `uint32` */
    public const val QuantizedDataIdx: Int = 0x44

    /** `uint32` */
    public const val QuantizedDataSize: Int = 0x48

    /** `uint32` */
    public const val StaticMaskIdx: Int = 0x4c

    /** `uint32` */
    public const val StaticMaskSize: Int = 0x50

    /** `uint32` */
    public const val StaticDOFsIdx: Int = 0x54

    /** `uint32` */
    public const val StaticDOFsSize: Int = 0x58

    /** `uint32` */
    public const val NumStaticTransformDOFs: Int = 0x5c

    /** `uint32` */
    public const val NumDynamicTransformDOFs: Int = 0x60

    /** `uint32` */
    public const val TotalBlockSize: Int = 0x64

    /** `uint32` */
    public const val LastBlockSize: Int = 0x68

    /** `hkArray<uint8>` */
    public const val DataBuffer: Int = 0x6c
}

/**
 * `hkaDeltaCompressedAnimationQuantizationFormat`, 0x14 bytes.
 */
public object HkaDeltaCompressedAnimationQuantizationFormat {
    public const val ByteSize: Int = 0x14

    /** `uint8` */
    public const val MaxBitWidth: Int = 0x0

    /** `uint8` */
    public const val Preserved: Int = 0x1

    /** `uint32` */
    public const val NumD: Int = 0x4

    /** `uint32` */
    public const val OffsetIdx: Int = 0x8

    /** `uint32` */
    public const val ScaleIdx: Int = 0xc

    /** `uint32` */
    public const val BitWidthIdx: Int = 0x10
}

/**
 * `hkaFootstepAnalysisInfo`, 0x98 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaFootstepAnalysisInfo`
 */
public object HkaFootstepAnalysisInfo {
    public const val ByteSize: Int = 0x98

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<char>` */
    public const val Unnamed0x8: Int = 0x8

    /** `hkArray<char>` */
    public const val NameStrike: Int = 0x14

    /** `hkArray<char>` */
    public const val NameLift: Int = 0x20

    /** `hkArray<char>` */
    public const val NameLock: Int = 0x2c

    /** `hkArray<char>` */
    public const val NameUnlock: Int = 0x38

    /** `hkArray<float>` */
    public const val MinPos: Int = 0x44

    /** `hkArray<float>` */
    public const val MaxPos: Int = 0x50

    /** `hkArray<float>` */
    public const val MinVel: Int = 0x5c

    /** `hkArray<float>` */
    public const val MaxVel: Int = 0x68

    /** `hkArray<float>` */
    public const val AllBonesDown: Int = 0x74

    /** `hkArray<float>` */
    public const val AnyBonesDown: Int = 0x80

    /** `float` */
    public const val PosTol: Int = 0x8c

    /** `float` */
    public const val VelTol: Int = 0x90

    /** `float` */
    public const val Duration: Int = 0x94
}

/**
 * `hkaFootstepAnalysisInfoContainer`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaFootstepAnalysisInfoContainer`
 */
public object HkaFootstepAnalysisInfoContainer {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val PreviewInfo: Int = 0x8
}

/**
 * `hkaInterleavedUncompressedAnimation`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaInterleavedUncompressedAnimation`
 */
public object HkaInterleavedUncompressedAnimation {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x1c

    /** `hkArray<hkQsTransform>` */
    public const val Transforms: Int = 0x28

    /** `hkArray<float>` */
    public const val Floats: Int = 0x34
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
    public const val HierarchyGain: Int = 0x0

    /** `float` */
    public const val VelocityDamping: Int = 0x4

    /** `float` */
    public const val AccelerationGain: Int = 0x8

    /** `float` */
    public const val VelocityGain: Int = 0xc

    /** `float` */
    public const val PositionGain: Int = 0x10

    /** `float` */
    public const val PositionMaxLinearVelocity: Int = 0x14

    /** `float` */
    public const val PositionMaxAngularVelocity: Int = 0x18

    /** `float` */
    public const val SnapGain: Int = 0x1c

    /** `float` */
    public const val SnapMaxLinearVelocity: Int = 0x20

    /** `float` */
    public const val SnapMaxAngularVelocity: Int = 0x24

    /** `float` */
    public const val SnapMaxLinearDistance: Int = 0x28

    /** `float` */
    public const val SnapMaxAngularDistance: Int = 0x2c
}

/**
 * `hkaMeshBinding`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaMeshBinding`
 */
public object HkaMeshBinding {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val Mesh: Int = 0x8

    /** `hkStringPtr` */
    public const val OriginalSkeletonName: Int = 0xc

    /** `struct*` */
    public const val Skeleton: Int = 0x10

    /** `hkArray<struct>` */
    public const val Mappings: Int = 0x14

    /** `hkArray<hkTransform>` */
    public const val BoneFromSkinMeshTransforms: Int = 0x20
}

/**
 * `hkaMeshBindingMapping`, 0xc bytes.
 */
public object HkaMeshBindingMapping {
    public const val ByteSize: Int = 0xc

    /** `hkArray<int16>` */
    public const val Mapping: Int = 0x0
}

/**
 * `hkaQuantizedAnimation`, 0x3c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaQuantizedAnimation`
 */
public object HkaQuantizedAnimation {
    public const val ByteSize: Int = 0x3c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x1c

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x28

    /** `uint32` */
    public const val Endian: Int = 0x34

    /** `void*` */
    public const val Skeleton: Int = 0x38
}

/**
 * `hkaQuantizedAnimationTrackCompressionParams`, 0x10 bytes.
 */
public object HkaQuantizedAnimationTrackCompressionParams {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val RotationTolerance: Int = 0x0

    /** `float` */
    public const val TranslationTolerance: Int = 0x4

    /** `float` */
    public const val ScaleTolerance: Int = 0x8

    /** `float` */
    public const val FloatingTolerance: Int = 0xc
}

/**
 * `hkaRagdollInstance`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaRagdollInstance`
 */
public object HkaRagdollInstance {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val RigidBodies: Int = 0x8

    /** `hkArray<ptr>` */
    public const val Constraints: Int = 0x14

    /** `hkArray<int32>` */
    public const val BoneToRigidBodyMap: Int = 0x20

    /** `struct*` */
    public const val Skeleton: Int = 0x2c
}

/**
 * `hkaSkeleton`, 0x54 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaSkeleton`
 */
public object HkaSkeleton {
    public const val ByteSize: Int = 0x54

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkStringPtr` */
    public const val Unnamed0x8: Int = 0x8

    /** `hkArray<int16>` */
    public const val ParentIndices: Int = 0xc

    /** `hkArray<struct>` */
    public const val Bones: Int = 0x18

    /** `hkArray<hkQsTransform>` */
    public const val ReferencePose: Int = 0x24

    /** `hkArray<float>` */
    public const val ReferenceFloats: Int = 0x30

    /** `hkArray<hkStringPtr>` */
    public const val FloatSlots: Int = 0x3c

    /** `hkArray<struct>` */
    public const val LocalFrames: Int = 0x48
}

/**
 * `hkaSkeletonLocalFrameOnBone`, 0x8 bytes.
 */
public object HkaSkeletonLocalFrameOnBone {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val LocalFrame: Int = 0x0

    /** `int32` */
    public const val BoneIndex: Int = 0x4
}

/**
 * `hkaSkeletonMapper`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaSkeletonMapper`
 */
public object HkaSkeletonMapper {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct` */
    public const val Mapping: Int = 0x10
}

/**
 * `hkaSkeletonMapperData`, 0x70 bytes.
 */
public object HkaSkeletonMapperData {
    public const val ByteSize: Int = 0x70

    /** `struct*` */
    public const val SkeletonA: Int = 0x0

    /** `struct*` */
    public const val SkeletonB: Int = 0x4

    /** `hkArray<struct>` */
    public const val SimpleMappings: Int = 0x8

    /** `hkArray<struct>` */
    public const val ChainMappings: Int = 0x14

    /** `hkArray<int16>` */
    public const val UnmappedBones: Int = 0x20

    /** `hkQsTransform` */
    public const val ExtractedMotionMapping: Int = 0x30

    /** `bool` */
    public const val KeepUnmappedLocal: Int = 0x60

    /** `enum:int32` */
    public const val MappingType: Int = 0x64
}

/**
 * `hkaSkeletonMapperDataChainMapping`, 0x70 bytes.
 */
public object HkaSkeletonMapperDataChainMapping {
    public const val ByteSize: Int = 0x70

    /** `int16` */
    public const val StartBoneA: Int = 0x0

    /** `int16` */
    public const val EndBoneA: Int = 0x2

    /** `int16` */
    public const val StartBoneB: Int = 0x4

    /** `int16` */
    public const val EndBoneB: Int = 0x6

    /** `hkQsTransform` */
    public const val StartAFromBTransform: Int = 0x10

    /** `hkQsTransform` */
    public const val EndAFromBTransform: Int = 0x40
}

/**
 * `hkaSkeletonMapperDataSimpleMapping`, 0x40 bytes.
 */
public object HkaSkeletonMapperDataSimpleMapping {
    public const val ByteSize: Int = 0x40

    /** `int16` */
    public const val BoneA: Int = 0x0

    /** `int16` */
    public const val BoneB: Int = 0x2

    /** `hkQsTransform` */
    public const val AFromBTransform: Int = 0x10
}

/**
 * `hkaSplineCompressedAnimation`, 0x84 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaSplineCompressedAnimation`
 */
public object HkaSplineCompressedAnimation {
    public const val ByteSize: Int = 0x84

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x1c

    /** `int32` */
    public const val NumFrames: Int = 0x28

    /** `int32` */
    public const val NumBlocks: Int = 0x2c

    /** `int32` */
    public const val MaxFramesPerBlock: Int = 0x30

    /** `int32` */
    public const val MaskAndQuantizationSize: Int = 0x34

    /** `float` */
    public const val BlockDuration: Int = 0x38

    /** `float` */
    public const val BlockInverseDuration: Int = 0x3c

    /** `float` */
    public const val FrameDuration: Int = 0x40

    /** `hkArray<uint32>` */
    public const val BlockOffsets: Int = 0x44

    /** `hkArray<uint32>` */
    public const val FloatBlockOffsets: Int = 0x50

    /** `hkArray<uint32>` */
    public const val TransformOffsets: Int = 0x5c

    /** `hkArray<uint32>` */
    public const val FloatOffsets: Int = 0x68

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x74

    /** `int32` */
    public const val Endian: Int = 0x80
}

/**
 * `hkaSplineCompressedAnimationAnimationCompressionParams`, 0x4 bytes.
 */
public object HkaSplineCompressedAnimationAnimationCompressionParams {
    public const val ByteSize: Int = 0x4

    /** `uint16` */
    public const val MaxFramesPerBlock: Int = 0x0

    /** `bool` */
    public const val EnableSampleSingleTracks: Int = 0x2
}

/**
 * `hkaSplineCompressedAnimationTrackCompressionParams`, 0x1c bytes.
 */
public object HkaSplineCompressedAnimationTrackCompressionParams {
    public const val ByteSize: Int = 0x1c

    /** `float` */
    public const val RotationTolerance: Int = 0x0

    /** `float` */
    public const val TranslationTolerance: Int = 0x4

    /** `float` */
    public const val ScaleTolerance: Int = 0x8

    /** `float` */
    public const val FloatingTolerance: Int = 0xc

    /** `uint16` */
    public const val RotationDegree: Int = 0x10

    /** `uint16` */
    public const val TranslationDegree: Int = 0x12

    /** `uint16` */
    public const val ScaleDegree: Int = 0x14

    /** `uint16` */
    public const val FloatingDegree: Int = 0x16

    /** `enum:uint8` */
    public const val RotationQuantizationType: Int = 0x18

    /** `enum:uint8` */
    public const val TranslationQuantizationType: Int = 0x19

    /** `enum:uint8` */
    public const val ScaleQuantizationType: Int = 0x1a

    /** `enum:uint8` */
    public const val FloatQuantizationType: Int = 0x1b
}

/**
 * `hkaWaveletCompressedAnimation`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaWaveletCompressedAnimation`
 */
public object HkaWaveletCompressedAnimation {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x8

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0xc

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x10

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x14

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x18

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x1c

    /** `int32` */
    public const val NumberOfPoses: Int = 0x28

    /** `int32` */
    public const val BlockSize: Int = 0x2c

    /** `struct` */
    public const val QFormat: Int = 0x30

    /** `uint32` */
    public const val StaticMaskIdx: Int = 0x44

    /** `uint32` */
    public const val StaticDOFsIdx: Int = 0x48

    /** `uint32` */
    public const val NumStaticTransformDOFs: Int = 0x4c

    /** `uint32` */
    public const val NumDynamicTransformDOFs: Int = 0x50

    /** `uint32` */
    public const val BlockIndexIdx: Int = 0x54

    /** `uint32` */
    public const val BlockIndexSize: Int = 0x58

    /** `uint32` */
    public const val QuantizedDataIdx: Int = 0x5c

    /** `uint32` */
    public const val QuantizedDataSize: Int = 0x60

    /** `hkArray<uint8>` */
    public const val DataBuffer: Int = 0x64
}

/**
 * `hkaWaveletCompressedAnimationCompressionParams`, 0x24 bytes.
 */
public object HkaWaveletCompressedAnimationCompressionParams {
    public const val ByteSize: Int = 0x24

    /** `uint16` */
    public const val QuantizationBits: Int = 0x0

    /** `uint16` */
    public const val BlockSize: Int = 0x2

    /** `uint16` */
    public const val Preserve: Int = 0x4

    /** `float` */
    public const val TruncProp: Int = 0x8

    /** `bool` */
    public const val UseOldStyleTruncation: Int = 0xc

    /** `float` */
    public const val AbsolutePositionTolerance: Int = 0x10

    /** `float` */
    public const val RelativePositionTolerance: Int = 0x14

    /** `float` */
    public const val RotationTolerance: Int = 0x18

    /** `float` */
    public const val ScaleTolerance: Int = 0x1c

    /** `float` */
    public const val AbsoluteFloatTolerance: Int = 0x20
}

/**
 * `hkaWaveletCompressedAnimationQuantizationFormat`, 0x14 bytes.
 */
public object HkaWaveletCompressedAnimationQuantizationFormat {
    public const val ByteSize: Int = 0x14

    /** `uint8` */
    public const val MaxBitWidth: Int = 0x0

    /** `uint8` */
    public const val Preserved: Int = 0x1

    /** `uint32` */
    public const val NumD: Int = 0x4

    /** `uint32` */
    public const val OffsetIdx: Int = 0x8

    /** `uint32` */
    public const val ScaleIdx: Int = 0xc

    /** `uint32` */
    public const val BitWidthIdx: Int = 0x10
}
