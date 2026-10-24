// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateDSRHavokLayouts
//
// Source: tools/havok/dsr-reflection.tsv, extracted from DarkSoulsRemastered.exe (hk_2015.1.0-r1, 64-bit)
//
// Animation: 38 of 866 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.dsr

/**
 * `hkaAngularReferenceFrame`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame` -> `hkaParameterizedReferenceFrame` -> `hkaAngularReferenceFrame`
 */
public object HkaAngularReferenceFrame {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkaAnimatedReferenceFrame` */
    public const val FrameType: Int = 0x10

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Up: Int = 0x20

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Forward: Int = 0x30

    /** `float`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Duration: Int = 0x40

    /** `hkArray<hkVector4>`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val ReferenceFrameSamples: Int = 0x48

    /** `float` */
    public const val TopAngle: Int = 0x60

    /** `float` */
    public const val Radius: Int = 0x64
}

/**
 * `hkaAnimatedReferenceFrame`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame`
 */
public object HkaAnimatedReferenceFrame {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8` */
    public const val FrameType: Int = 0x10
}

/**
 * `hkaAnimation`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation`
 */
public object HkaAnimation {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int32` */
    public const val Type: Int = 0x10

    /** `float` */
    public const val Duration: Int = 0x14

    /** `int32` */
    public const val NumberOfTransformTracks: Int = 0x18

    /** `int32` */
    public const val NumberOfFloatTracks: Int = 0x1c

    /** `struct*` */
    public const val ExtractedMotion: Int = 0x20

    /** `hkArray<struct>` */
    public const val AnnotationTracks: Int = 0x28
}

/**
 * `hkaAnimationBinding`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationBinding`
 */
public object HkaAnimationBinding {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val OriginalSkeletonName: Int = 0x10

    /** `struct*` */
    public const val Animation: Int = 0x18

    /** `hkArray<int16>` */
    public const val TransformTrackToBoneIndices: Int = 0x20

    /** `hkArray<int16>` */
    public const val FloatTrackToFloatSlotIndices: Int = 0x30

    /** `hkArray<int16>` */
    public const val PartitionIndices: Int = 0x40

    /** `enum:int8` */
    public const val BlendHint: Int = 0x50
}

/**
 * `hkaAnimationContainer`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationContainer`
 */
public object HkaAnimationContainer {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Skeletons: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Animations: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Bindings: Int = 0x30

    /** `hkArray<ptr>` */
    public const val Attachments: Int = 0x40

    /** `hkArray<ptr>` */
    public const val Skins: Int = 0x50
}

/**
 * `hkaAnimationPreviewColorContainer`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimationPreviewColorContainer`
 */
public object HkaAnimationPreviewColorContainer {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<uint32>` */
    public const val PreviewColor: Int = 0x10
}

/**
 * `hkaAnnotationTrack`, 0x18 bytes.
 */
public object HkaAnnotationTrack {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val TrackName: Int = 0x0

    /** `hkArray<struct>` */
    public const val Annotations: Int = 0x8
}

/**
 * `hkaAnnotationTrack::Annotation`, 0x10 bytes.
 */
public object HkaAnnotationTrackAnnotation {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val Time: Int = 0x0

    /** `hkStringPtr` */
    public const val Text: Int = 0x8
}

/**
 * `hkaBone`, 0x10 bytes.
 */
public object HkaBone {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `bool` */
    public const val LockTranslation: Int = 0x8
}

/**
 * `hkaBoneAttachment`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaBoneAttachment`
 */
public object HkaBoneAttachment {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val OriginalSkeletonName: Int = 0x10

    /** `hkMatrix4` */
    public const val BoneFromAttachment: Int = 0x20

    /** `struct*` */
    public const val Attachment: Int = 0x60

    /** `hkStringPtr` */
    public const val Name: Int = 0x68

    /** `int16` */
    public const val BoneIndex: Int = 0x70
}

/**
 * `hkaDefaultAnimatedReferenceFrame`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame`
 */
public object HkaDefaultAnimatedReferenceFrame {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkaAnimatedReferenceFrame` */
    public const val FrameType: Int = 0x10

    /** `hkVector4` */
    public const val Up: Int = 0x20

    /** `hkVector4` */
    public const val Forward: Int = 0x30

    /** `float` */
    public const val Duration: Int = 0x40

    /** `hkArray<hkVector4>` */
    public const val ReferenceFrameSamples: Int = 0x48
}

/**
 * `hkaDirectionalReferenceFrame`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame` -> `hkaParameterizedReferenceFrame` -> `hkaDirectionalReferenceFrame`
 */
public object HkaDirectionalReferenceFrame {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkaAnimatedReferenceFrame` */
    public const val FrameType: Int = 0x10

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Up: Int = 0x20

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Forward: Int = 0x30

    /** `float`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Duration: Int = 0x40

    /** `hkArray<hkVector4>`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val ReferenceFrameSamples: Int = 0x48

    /** `hkVector4` */
    public const val MovementDir: Int = 0x60
}

/**
 * `hkaFootstepAnalysisInfo`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaFootstepAnalysisInfo`
 */
public object HkaFootstepAnalysisInfo {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<char>` */
    public const val Name: Int = 0x10

    /** `hkArray<char>` */
    public const val NameStrike: Int = 0x20

    /** `hkArray<char>` */
    public const val NameLift: Int = 0x30

    /** `hkArray<char>` */
    public const val NameLock: Int = 0x40

    /** `hkArray<char>` */
    public const val NameUnlock: Int = 0x50

    /** `hkArray<float>` */
    public const val MinPos: Int = 0x60

    /** `hkArray<float>` */
    public const val MaxPos: Int = 0x70

    /** `hkArray<float>` */
    public const val MinVel: Int = 0x80

    /** `hkArray<float>` */
    public const val MaxVel: Int = 0x90

    /** `hkArray<float>` */
    public const val AllBonesDown: Int = 0xa0

    /** `hkArray<float>` */
    public const val AnyBonesDown: Int = 0xb0

    /** `float` */
    public const val PosTol: Int = 0xc0

    /** `float` */
    public const val VelTol: Int = 0xc4

    /** `float` */
    public const val Duration: Int = 0xc8
}

/**
 * `hkaFootstepAnalysisInfoContainer`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaFootstepAnalysisInfoContainer`
 */
public object HkaFootstepAnalysisInfoContainer {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val PreviewInfo: Int = 0x10
}

/**
 * `hkaInterleavedUncompressedAnimation`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaInterleavedUncompressedAnimation`
 */
public object HkaInterleavedUncompressedAnimation {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x10

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0x14

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x18

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x1c

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x20

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x28

    /** `hkArray<hkQsTransform>` */
    public const val Transforms: Int = 0x38

    /** `hkArray<float>` */
    public const val Floats: Int = 0x48
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
 * `hkaKeyFrameHierarchyUtility::ControlData`, 0x30 bytes.
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
 * `hkaMeshBinding`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaMeshBinding`
 */
public object HkaMeshBinding {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Mesh: Int = 0x10

    /** `hkStringPtr` */
    public const val OriginalSkeletonName: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x20

    /** `struct*` */
    public const val Skeleton: Int = 0x28

    /** `hkArray<struct>` */
    public const val Mappings: Int = 0x30

    /** `hkArray<hkTransform>` */
    public const val BoneFromSkinMeshTransforms: Int = 0x40
}

/**
 * `hkaMeshBinding::Mapping`, 0x10 bytes.
 */
public object HkaMeshBindingMapping {
    public const val ByteSize: Int = 0x10

    /** `hkArray<int16>` */
    public const val Mapping: Int = 0x0
}

/**
 * `hkaParameterizedAnimationReferenceFrame`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame` -> `hkaParameterizedAnimationReferenceFrame`
 */
public object HkaParameterizedAnimationReferenceFrame {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkaAnimatedReferenceFrame` */
    public const val FrameType: Int = 0x10

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Up: Int = 0x20

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Forward: Int = 0x30

    /** `float`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Duration: Int = 0x40

    /** `hkArray<hkVector4>`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val ReferenceFrameSamples: Int = 0x48

    /** `hkArray<float>` */
    public const val ParameterValues: Int = 0x60

    /** `hkArray<int32>` */
    public const val ParameterTypes: Int = 0x70
}

/**
 * `hkaParameterizedReferenceFrame`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimatedReferenceFrame` -> `hkaDefaultAnimatedReferenceFrame` -> `hkaParameterizedReferenceFrame`
 */
public object HkaParameterizedReferenceFrame {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkaAnimatedReferenceFrame` */
    public const val FrameType: Int = 0x10

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Up: Int = 0x20

    /** `hkVector4`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Forward: Int = 0x30

    /** `float`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val Duration: Int = 0x40

    /** `hkArray<hkVector4>`, from `hkaDefaultAnimatedReferenceFrame` */
    public const val ReferenceFrameSamples: Int = 0x48
}

/**
 * `hkaPredictiveCompressedAnimation`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaPredictiveCompressedAnimation`
 */
public object HkaPredictiveCompressedAnimation {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x10

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0x14

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x18

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x1c

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x20

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x28

    /** `hkArray<uint8>` */
    public const val CompressedData: Int = 0x38

    /** `hkArray<uint16>` */
    public const val IntData: Int = 0x48

    /** `int32[9]` */
    public const val IntArrayOffsets: Int = 0x58

    /** `hkArray<float>` */
    public const val FloatData: Int = 0x80

    /** `int32[3]` */
    public const val FloatArrayOffsets: Int = 0x90

    /** `int32` */
    public const val NumBones: Int = 0x9c

    /** `int32` */
    public const val NumFloatSlots: Int = 0xa0

    /** `int32` */
    public const val NumFrames: Int = 0xa4

    /** `int32` */
    public const val FirstFloatBlockScaleAndOffsetIndex: Int = 0xa8

    /** `void*` */
    public const val Skeleton: Int = 0xb0

    /** `int32` */
    public const val MaxCompressedBytesPerFrame: Int = 0xb8
}

/**
 * `hkaPredictiveCompressedAnimation::TrackCompressionParams`, 0x20 bytes.
 */
public object HkaPredictiveCompressedAnimationTrackCompressionParams {
    public const val ByteSize: Int = 0x20

    /** `float` */
    public const val StaticTranslationTolerance: Int = 0x0

    /** `float` */
    public const val StaticRotationTolerance: Int = 0x4

    /** `float` */
    public const val StaticScaleTolerance: Int = 0x8

    /** `float` */
    public const val StaticFloatTolerance: Int = 0xc

    /** `float` */
    public const val DynamicTranslationTolerance: Int = 0x10

    /** `float` */
    public const val DynamicRotationTolerance: Int = 0x14

    /** `float` */
    public const val DynamicScaleTolerance: Int = 0x18

    /** `float` */
    public const val DynamicFloatTolerance: Int = 0x1c
}

/**
 * `hkaQuantizedAnimation`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaQuantizedAnimation`
 */
public object HkaQuantizedAnimation {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x10

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0x14

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x18

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x1c

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x20

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x28

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x38

    /** `uint32` */
    public const val Endian: Int = 0x48

    /** `void*` */
    public const val Skeleton: Int = 0x50
}

/**
 * `hkaQuantizedAnimation::TrackCompressionParams`, 0x10 bytes.
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
 * `hkaRagdollInstance`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaRagdollInstance`
 */
public object HkaRagdollInstance {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val RigidBodies: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Constraints: Int = 0x20

    /** `hkArray<int32>` */
    public const val BoneToRigidBodyMap: Int = 0x30

    /** `struct*` */
    public const val Skeleton: Int = 0x40
}

/**
 * `hkaReferencePoseAnimation`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaReferencePoseAnimation`
 */
public object HkaReferencePoseAnimation {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x10

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0x14

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x18

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x1c

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x20

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x28

    /** `struct*` */
    public const val Skeleton: Int = 0x38
}

/**
 * `hkaSkeleton`, 0x88 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaSkeleton`
 */
public object HkaSkeleton {
    public const val ByteSize: Int = 0x88

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10

    /** `hkArray<int16>` */
    public const val ParentIndices: Int = 0x18

    /** `hkArray<struct>` */
    public const val Bones: Int = 0x28

    /** `hkArray<hkQsTransform>` */
    public const val ReferencePose: Int = 0x38

    /** `hkArray<float>` */
    public const val ReferenceFloats: Int = 0x48

    /** `hkArray<hkStringPtr>` */
    public const val FloatSlots: Int = 0x58

    /** `hkArray<struct>` */
    public const val LocalFrames: Int = 0x68

    /** `hkArray<struct>` */
    public const val Partitions: Int = 0x78
}

/**
 * `hkaSkeleton::LocalFrameOnBone`, 0x10 bytes.
 */
public object HkaSkeletonLocalFrameOnBone {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val LocalFrame: Int = 0x0

    /** `int16` */
    public const val BoneIndex: Int = 0x8
}

/**
 * `hkaSkeleton::Partition`, 0x10 bytes.
 */
public object HkaSkeletonPartition {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `int16` */
    public const val StartBoneIndex: Int = 0x8

    /** `int16` */
    public const val NumBones: Int = 0xa
}

/**
 * `hkaSkeletonMapper`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaSkeletonMapper`
 */
public object HkaSkeletonMapper {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Mapping: Int = 0x10
}

/**
 * `hkaSkeletonMapperData`, 0xb0 bytes.
 */
public object HkaSkeletonMapperData {
    public const val ByteSize: Int = 0xb0

    /** `struct*` */
    public const val SkeletonA: Int = 0x0

    /** `struct*` */
    public const val SkeletonB: Int = 0x8

    /** `hkArray<int16>` */
    public const val PartitionMap: Int = 0x10

    /** `hkArray<struct>` */
    public const val SimpleMappingPartitionRanges: Int = 0x20

    /** `hkArray<struct>` */
    public const val ChainMappingPartitionRanges: Int = 0x30

    /** `hkArray<struct>` */
    public const val SimpleMappings: Int = 0x40

    /** `hkArray<struct>` */
    public const val ChainMappings: Int = 0x50

    /** `hkArray<int16>` */
    public const val UnmappedBones: Int = 0x60

    /** `hkQsTransform` */
    public const val ExtractedMotionMapping: Int = 0x70

    /** `bool` */
    public const val KeepUnmappedLocal: Int = 0xa0

    /** `enum:int32` */
    public const val MappingType: Int = 0xa4
}

/**
 * `hkaSkeletonMapperData::ChainMapping`, 0x70 bytes.
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
 * `hkaSkeletonMapperData::PartitionMappingRange`, 0x8 bytes.
 */
public object HkaSkeletonMapperDataPartitionMappingRange {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val StartMappingIndex: Int = 0x0

    /** `int32` */
    public const val NumMappings: Int = 0x4
}

/**
 * `hkaSkeletonMapperData::SimpleMapping`, 0x40 bytes.
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
 * `hkaSplineCompressedAnimation`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkaAnimation` -> `hkaSplineCompressedAnimation`
 */
public object HkaSplineCompressedAnimation {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int32`, from `hkaAnimation` */
    public const val Type: Int = 0x10

    /** `float`, from `hkaAnimation` */
    public const val Duration: Int = 0x14

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfTransformTracks: Int = 0x18

    /** `int32`, from `hkaAnimation` */
    public const val NumberOfFloatTracks: Int = 0x1c

    /** `struct*`, from `hkaAnimation` */
    public const val ExtractedMotion: Int = 0x20

    /** `hkArray<struct>`, from `hkaAnimation` */
    public const val AnnotationTracks: Int = 0x28

    /** `int32` */
    public const val NumFrames: Int = 0x38

    /** `int32` */
    public const val NumBlocks: Int = 0x3c

    /** `int32` */
    public const val MaxFramesPerBlock: Int = 0x40

    /** `int32` */
    public const val MaskAndQuantizationSize: Int = 0x44

    /** `float` */
    public const val BlockDuration: Int = 0x48

    /** `float` */
    public const val BlockInverseDuration: Int = 0x4c

    /** `float` */
    public const val FrameDuration: Int = 0x50

    /** `hkArray<uint32>` */
    public const val BlockOffsets: Int = 0x58

    /** `hkArray<uint32>` */
    public const val FloatBlockOffsets: Int = 0x68

    /** `hkArray<uint32>` */
    public const val TransformOffsets: Int = 0x78

    /** `hkArray<uint32>` */
    public const val FloatOffsets: Int = 0x88

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x98

    /** `int32` */
    public const val Endian: Int = 0xa8
}

/**
 * `hkaSplineCompressedAnimation::AnimationCompressionParams`, 0x4 bytes.
 */
public object HkaSplineCompressedAnimationAnimationCompressionParams {
    public const val ByteSize: Int = 0x4

    /** `uint16` */
    public const val MaxFramesPerBlock: Int = 0x0

    /** `bool` */
    public const val EnableSampleSingleTracks: Int = 0x2
}

/**
 * `hkaSplineCompressedAnimation::TrackCompressionParams`, 0x1c bytes.
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
