// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateDSRHavokLayouts
//
// Source: tools/havok/dsr-reflection.tsv, extracted from DarkSoulsRemastered.exe (hk_2015.1.0-r1, 64-bit)
//
// Physics: 286 of 866 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.dsr

/**
 * `hkp2dAngConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkp2dAngConstraintAtom`
 */
public object Hkp2dAngConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val FreeRotationAxis: Int = 0x2
}

/**
 * `hkp3dAngConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkp3dAngConstraintAtom`
 */
public object Hkp3dAngConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0
}

/**
 * `hkp6DofConstraintData`, 0x2b0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkp6DofConstraintData`
 */
public object Hkp6DofConstraintData {
    public const val ByteSize: Int = 0x2b0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Blueprints: Int = 0x20

    /** `bool` */
    public const val IsDirty: Int = 0x220

    /** `int32` */
    public const val NumRuntimeElements: Int = 0x224

    /** `int32[11]` */
    public const val AtomToCompiledAtomOffset: Int = 0x228

    /** `int32[19]` */
    public const val ResultToRuntime: Int = 0x254

    /** `hkArray<void>` */
    public const val CompiledAtoms: Int = 0x2a0
}

/**
 * `hkp6DofConstraintData::Blueprints`, 0x200 bytes.
 */
public object Hkp6DofConstraintDataBlueprints {
    public const val ByteSize: Int = 0x200

    /** `bool[3]` */
    public const val LinearIsFixed: Int = 0x0

    /** `struct` */
    public const val Transforms: Int = 0x10

    /** `struct` */
    public const val SetupStabilization: Int = 0xa0

    /** `struct` */
    public const val RagdollMotors: Int = 0xb0

    /** `struct` */
    public const val AngFriction: Int = 0x110

    /** `struct` */
    public const val TwistLimit: Int = 0x120

    /** `struct` */
    public const val EllipticalLimit: Int = 0x140

    /** `struct` */
    public const val StiffSpring: Int = 0x170

    /** `struct` */
    public const val LinearMotor0: Int = 0x190

    /** `struct` */
    public const val LinearMotor1: Int = 0x1b0

    /** `struct` */
    public const val LinearMotor2: Int = 0x1d0

    /** `struct` */
    public const val BallSocket: Int = 0x1f0
}

/**
 * `hkpAabbPhantom`, 0x130 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpAabbPhantom`
 */
public object HkpAabbPhantom {
    public const val ByteSize: Int = 0x130

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0xc8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0xd8

    /** `struct` */
    public const val Aabb: Int = 0xf0

    /** `hkArray<void>` */
    public const val OverlappingCollidables: Int = 0x110

    /** `bool` */
    public const val OrderDirty: Int = 0x120
}

/**
 * `hkpAction`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction`
 */
public object HkpAction {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*` */
    public const val World: Int = 0x10

    /** `void*` */
    public const val Island: Int = 0x18

    /** `hkUlong` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr` */
    public const val Name: Int = 0x28
}

/**
 * `hkpAngConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngConstraintAtom`
 */
public object HkpAngConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8[3]` */
    public const val ConstrainedAxes: Int = 0x2

    /** `uint8` */
    public const val NumConstrainedAxes: Int = 0x5
}

/**
 * `hkpAngFrictionConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngFrictionConstraintAtom`
 */
public object HkpAngFrictionConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val FirstFrictionAxis: Int = 0x3

    /** `uint8` */
    public const val NumFrictionAxes: Int = 0x4

    /** `float` */
    public const val MaxFrictionTorque: Int = 0x8
}

/**
 * `hkpAngLimitConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngLimitConstraintAtom`
 */
public object HkpAngLimitConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val LimitAxis: Int = 0x3

    /** `uint8` */
    public const val CosineAxis: Int = 0x4

    /** `float` */
    public const val MinAngle: Int = 0x8

    /** `float` */
    public const val MaxAngle: Int = 0xc

    /** `float` */
    public const val AngularLimitsTauFactor: Int = 0x10

    /** `float` */
    public const val AngularLimitsDampFactor: Int = 0x14
}

/**
 * `hkpAngMotorConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngMotorConstraintAtom`
 */
public object HkpAngMotorConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `bool` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val MotorAxis: Int = 0x3

    /** `int16` */
    public const val InitializedOffset: Int = 0x4

    /** `int16` */
    public const val PreviousTargetAngleOffset: Int = 0x6

    /** `struct*` */
    public const val Motor: Int = 0x8

    /** `float` */
    public const val TargetAngle: Int = 0x10

    /** `int16` */
    public const val CorrespondingAngLimitSolverResultOffset: Int = 0x14
}

/**
 * `hkpAngularDashpotAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpAngularDashpotAction`
 */
public object HkpAngularDashpotAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityA: Int = 0x30

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityB: Int = 0x38

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x40

    /** `float` */
    public const val Strength: Int = 0x50

    /** `float` */
    public const val Damping: Int = 0x54
}

/**
 * `hkpArrayAction`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpArrayAction`
 */
public object HkpArrayAction {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `hkArray<ptr>` */
    public const val Entities: Int = 0x30
}

/**
 * `hkpBallAndSocketConstraintData`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpBallAndSocketConstraintData`
 */
public object HkpBallAndSocketConstraintData {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpBallAndSocketConstraintData::Atoms`, 0x50 bytes.
 */
public object HkpBallAndSocketConstraintDataAtoms {
    public const val ByteSize: Int = 0x50

    /** `struct` */
    public const val Pivots: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x30

    /** `struct` */
    public const val BallSocket: Int = 0x40
}

/**
 * `hkpBallGun`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpBallGun`
 */
public object HkpBallGun {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x10

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Name: Int = 0x18

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x20

    /** `hkArray<void>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x28

    /** `float` */
    public const val BulletRadius: Int = 0x38

    /** `float` */
    public const val BulletVelocity: Int = 0x3c

    /** `float` */
    public const val BulletMass: Int = 0x40

    /** `float` */
    public const val DamageMultiplier: Int = 0x44

    /** `int32` */
    public const val MaxBulletsInWorld: Int = 0x48

    /** `hkVector4` */
    public const val BulletOffsetFromCenter: Int = 0x50

    /** `void*` */
    public const val AddedBodies: Int = 0x60
}

/**
 * `hkpBallSocketChainData`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpBallSocketChainData`
 */
public object HkpBallSocketChainData {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `hkArray<struct>` */
    public const val Infos: Int = 0x40

    /** `hkVector4` */
    public const val Link0PivotBVelocity: Int = 0x50

    /** `float` */
    public const val Tau: Int = 0x60

    /** `float` */
    public const val Damping: Int = 0x64

    /** `float` */
    public const val Cfm: Int = 0x68

    /** `float` */
    public const val MaxErrorDistance: Int = 0x6c

    /** `float` */
    public const val InertiaPerMeter: Int = 0x70

    /** `bool` */
    public const val UseStabilizedCode: Int = 0x74
}

/**
 * `hkpBallSocketChainData::ConstraintInfo`, 0x30 bytes.
 */
public object HkpBallSocketChainDataConstraintInfo {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val PivotInA: Int = 0x0

    /** `hkVector4` */
    public const val PivotInB: Int = 0x10

    /** `uint32` */
    public const val Flags: Int = 0x20
}

/**
 * `hkpBallSocketConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpBallSocketConstraintAtom`
 */
public object HkpBallSocketConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `enum:uint8` */
    public const val SolvingMethod: Int = 0x2

    /** `uint8` */
    public const val BodiesToNotify: Int = 0x3

    /** `struct` */
    public const val VelocityStabilizationFactor: Int = 0x4

    /** `bool` */
    public const val EnableLinearImpulseLimit: Int = 0x5

    /** `float` */
    public const val BreachImpulse: Int = 0x8

    /** `float` */
    public const val InertiaStabilizationFactor: Int = 0xc
}

/**
 * `hkpBinaryAction`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction`
 */
public object HkpBinaryAction {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*` */
    public const val EntityA: Int = 0x30

    /** `struct*` */
    public const val EntityB: Int = 0x38
}

/**
 * `hkpBoxMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpBoxMotion`
 */
public object HkpBoxMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpBoxShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpBoxShape`
 */
public object HkpBoxShape {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `hkVector4` */
    public const val HalfExtents: Int = 0x30
}

/**
 * `hkpBreakableBody`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableBody`
 */
public object HkpBreakableBody {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Controller: Int = 0x10

    /** `struct*` */
    public const val BreakableShape: Int = 0x18

    /** `uint8` */
    public const val BodyTypeAndFlags: Int = 0x20

    /** `hkHalf` */
    public const val ConstraintStrength: Int = 0x22
}

/**
 * `hkpBreakableBody::Controller`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableBody::Controller`
 */
public object HkpBreakableBodyController {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val BreakingImpulse: Int = 0x10
}

/**
 * `hkpBreakableConstraintData`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpWrappedConstraintData` -> `hkpBreakableConstraintData`
 */
public object HkpBreakableConstraintData {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct*`, from `hkpWrappedConstraintData` */
    public const val ConstraintData: Int = 0x18

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `uint16` */
    public const val ChildRuntimeSize: Int = 0x40

    /** `uint16` */
    public const val ChildNumSolverResults: Int = 0x42

    /** `float` */
    public const val SolverResultLimit: Int = 0x44

    /** `bool` */
    public const val RemoveWhenBroken: Int = 0x48

    /** `bool` */
    public const val RevertBackVelocityOnBreak: Int = 0x49
}

/**
 * `hkpBreakableMaterial`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMaterial`
 */
public object HkpBreakableMaterial {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val Strength: Int = 0x10

    /** `int32` */
    public const val TypeAndFlags: Int = 0x14

    /** `struct*` */
    public const val Properties: Int = 0x18
}

/**
 * `hkpBreakableMultiMaterial`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMaterial` -> `hkpBreakableMultiMaterial`
 */
public object HkpBreakableMultiMaterial {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float`, from `hkpBreakableMaterial` */
    public const val Strength: Int = 0x10

    /** `int32`, from `hkpBreakableMaterial` */
    public const val TypeAndFlags: Int = 0x14

    /** `struct*`, from `hkpBreakableMaterial` */
    public const val Properties: Int = 0x18

    /** `hkArray<ptr>` */
    public const val SubMaterials: Int = 0x20

    /** `struct*` */
    public const val InverseMapping: Int = 0x30
}

/**
 * `hkpBreakableMultiMaterial::InverseMapping`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMultiMaterial::InverseMapping`
 */
public object HkpBreakableMultiMaterialInverseMapping {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Descriptors: Int = 0x10

    /** `hkArray<uint32>` */
    public const val SubShapeIds: Int = 0x20
}

/**
 * `hkpBreakableMultiMaterial::InverseMappingDescriptor`, 0x4 bytes.
 */
public object HkpBreakableMultiMaterialInverseMappingDescriptor {
    public const val ByteSize: Int = 0x4

    /** `uint16` */
    public const val Offset: Int = 0x0

    /** `uint16` */
    public const val NumKeys: Int = 0x2
}

/**
 * `hkpBreakableShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableShape`
 */
public object HkpBreakableShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val PhysicsShape: Int = 0x10

    /** `struct*` */
    public const val Material: Int = 0x18
}

/**
 * `hkpBridgeAtoms`, 0x20 bytes.
 */
public object HkpBridgeAtoms {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val BridgeAtom: Int = 0x0
}

/**
 * `hkpBridgeConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpBridgeConstraintAtom`
 */
public object HkpBridgeConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `void*` */
    public const val BuildJacobianFunc: Int = 0x8

    /** `struct*` */
    public const val ConstraintData: Int = 0x10
}

/**
 * `hkpBroadPhaseHandle`, 0x4 bytes.
 */
public object HkpBroadPhaseHandle {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val Id: Int = 0x0
}

/**
 * `hkpBvCompressedMeshShape`, 0x110 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvTreeShape` -> `hkpBvCompressedMeshShape`
 */
public object HkpBvCompressedMeshShape {
    public const val ByteSize: Int = 0x110

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val BvTreeType: Int = 0x20

    /** `float` */
    public const val ConvexRadius: Int = 0x30

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x34

    /** `bool` */
    public const val HasPerPrimitiveCollisionFilterInfo: Int = 0x35

    /** `bool` */
    public const val HasPerPrimitiveUserData: Int = 0x36

    /** `hkArray<uint32>` */
    public const val CollisionFilterInfoPalette: Int = 0x38

    /** `hkArray<uint32>` */
    public const val UserDataPalette: Int = 0x48

    /** `hkArray<hkStringPtr>` */
    public const val UserStringPalette: Int = 0x58

    /** `struct` */
    public const val Tree: Int = 0x70
}

/**
 * `hkpBvCompressedMeshShapeTree`, 0xa0 bytes.
 *
 * `hkcdStaticTree::DynamicStorage` -> `hkcdStaticTree::Tree` -> `hkcdStaticMeshTreeBase` -> `hkcdStaticMeshTree` -> `hkpBvCompressedMeshShapeTree`
 */
public object HkpBvCompressedMeshShapeTree {
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

    /** `hkArray<uint32>`, from `hkcdStaticMeshTree` */
    public const val PackedVertices: Int = 0x70

    /** `hkArray<int64>`, from `hkcdStaticMeshTree` */
    public const val SharedVertices: Int = 0x80

    /** `hkArray<struct>`, from `hkcdStaticMeshTree` */
    public const val PrimitiveDataRuns: Int = 0x90
}

/**
 * `hkpBvCompressedMeshShapeTreeDataRun`, 0x8 bytes.
 *
 * `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` -> `hkpBvCompressedMeshShapeTreeDataRun`
 */
public object HkpBvCompressedMeshShapeTreeDataRun {
    public const val ByteSize: Int = 0x8

    /** `uint32`, from `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` */
    public const val Value: Int = 0x0

    /** `uint8`, from `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` */
    public const val Index: Int = 0x4

    /** `uint8`, from `hkcdStaticMeshTreeBase::PrimitiveDataRunBase` */
    public const val Count: Int = 0x5
}

/**
 * `hkpBvShape`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvShape`
 */
public object HkpBvShape {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `struct*` */
    public const val BoundingVolumeShape: Int = 0x20

    /** `struct` */
    public const val ChildShape: Int = 0x28
}

/**
 * `hkpBvTreeShape`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvTreeShape`
 */
public object HkpBvTreeShape {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `enum:uint8` */
    public const val BvTreeType: Int = 0x20
}

/**
 * `hkpCachingShapePhantom`, 0x1c0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom` -> `hkpCachingShapePhantom`
 */
public object HkpCachingShapePhantom {
    public const val ByteSize: Int = 0x1c0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0xc8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0xd8

    /** `struct`, from `hkpShapePhantom` */
    public const val MotionState: Int = 0xf0

    /** `hkArray<void>` */
    public const val CollisionDetails: Int = 0x1a0

    /** `bool` */
    public const val OrderDirty: Int = 0x1b0
}

/**
 * `hkpCallbackConstraintMotor`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpCallbackConstraintMotor`
 */
public object HkpCallbackConstraintMotor {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x10

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0x18

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x1c

    /** `void*` */
    public const val CallbackFunc: Int = 0x20

    /** `enum:uint32` */
    public const val CallbackType: Int = 0x28

    /** `hkUlong` */
    public const val UserData0: Int = 0x30

    /** `hkUlong` */
    public const val UserData1: Int = 0x38

    /** `hkUlong` */
    public const val UserData2: Int = 0x40
}

/**
 * `hkpCapsuleShape`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpCapsuleShape`
 */
public object HkpCapsuleShape {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `hkVector4` */
    public const val VertexA: Int = 0x30

    /** `hkVector4` */
    public const val VertexB: Int = 0x40
}

/**
 * `hkpCdBody`, 0x20 bytes.
 */
public object HkpCdBody {
    public const val ByteSize: Int = 0x20

    /** `struct*` */
    public const val Shape: Int = 0x0

    /** `uint32` */
    public const val ShapeKey: Int = 0x8

    /** `void*` */
    public const val Motion: Int = 0x10

    /** `struct*` */
    public const val Parent: Int = 0x18
}

/**
 * `hkpCenterOfMassChangerModifierConstraintAtom`, 0x50 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpCenterOfMassChangerModifierConstraintAtom`
 */
public object HkpCenterOfMassChangerModifierConstraintAtom {
    public const val ByteSize: Int = 0x50

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x18

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x20

    /** `hkVector4` */
    public const val DisplacementA: Int = 0x30

    /** `hkVector4` */
    public const val DisplacementB: Int = 0x40
}

/**
 * `hkpCharacterControllerCinfo`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo`
 */
public object HkpCharacterControllerCinfo {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkpCharacterMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpCharacterMotion`
 */
public object HkpCharacterMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpCharacterProxyCinfo`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo` -> `hkpCharacterProxyCinfo`
 */
public object HkpCharacterProxyCinfo {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkVector4` */
    public const val Position: Int = 0x10

    /** `hkVector4` */
    public const val Velocity: Int = 0x20

    /** `float` */
    public const val DynamicFriction: Int = 0x30

    /** `float` */
    public const val StaticFriction: Int = 0x34

    /** `float` */
    public const val KeepContactTolerance: Int = 0x38

    /** `hkVector4` */
    public const val Up: Int = 0x40

    /** `float` */
    public const val ExtraUpStaticFriction: Int = 0x50

    /** `float` */
    public const val ExtraDownStaticFriction: Int = 0x54

    /** `struct*` */
    public const val ShapePhantom: Int = 0x58

    /** `float` */
    public const val KeepDistance: Int = 0x60

    /** `float` */
    public const val ContactAngleSensitivity: Int = 0x64

    /** `uint32` */
    public const val UserPlanes: Int = 0x68

    /** `float` */
    public const val MaxCharacterSpeedForSolver: Int = 0x6c

    /** `float` */
    public const val CharacterStrength: Int = 0x70

    /** `float` */
    public const val CharacterMass: Int = 0x74

    /** `float` */
    public const val MaxSlope: Int = 0x78

    /** `float` */
    public const val PenetrationRecoverySpeed: Int = 0x7c

    /** `int32` */
    public const val MaxCastIterations: Int = 0x80

    /** `bool` */
    public const val RefreshManifoldInCheckSupport: Int = 0x84
}

/**
 * `hkpCharacterRigidBodyCinfo`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo` -> `hkpCharacterRigidBodyCinfo`
 */
public object HkpCharacterRigidBodyCinfo {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32` */
    public const val CollisionFilterInfo: Int = 0x10

    /** `struct*` */
    public const val Shape: Int = 0x18

    /** `hkVector4` */
    public const val Position: Int = 0x20

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x30

    /** `float` */
    public const val Mass: Int = 0x40

    /** `float` */
    public const val Friction: Int = 0x44

    /** `float` */
    public const val MaxLinearVelocity: Int = 0x48

    /** `float` */
    public const val AllowedPenetrationDepth: Int = 0x4c

    /** `hkVector4` */
    public const val Up: Int = 0x50

    /** `float` */
    public const val MaxSlope: Int = 0x60

    /** `float` */
    public const val MaxSupportSlope: Int = 0x64

    /** `float` */
    public const val MaxForce: Int = 0x68

    /** `float` */
    public const val UnweldingHeightOffsetFactor: Int = 0x6c

    /** `float` */
    public const val MaxSpeedForSimplexSolver: Int = 0x70

    /** `float` */
    public const val SupportDistance: Int = 0x74

    /** `float` */
    public const val HardSupportDistance: Int = 0x78

    /** `int32` */
    public const val VdbColor: Int = 0x7c
}

/**
 * `hkpCogWheelConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpCogWheelConstraintAtom`
 */
public object HkpCogWheelConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `float` */
    public const val CogWheelRadiusA: Int = 0x4

    /** `float` */
    public const val CogWheelRadiusB: Int = 0x8

    /** `bool` */
    public const val IsScrew: Int = 0xc

    /** `int8` */
    public const val MemOffsetToInitialAngleOffset: Int = 0xd

    /** `int8` */
    public const val MemOffsetToPrevAngle: Int = 0xe

    /** `int8` */
    public const val MemOffsetToRevolutionCounter: Int = 0xf
}

/**
 * `hkpCogWheelConstraintData`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpCogWheelConstraintData`
 */
public object HkpCogWheelConstraintData {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpCogWheelConstraintData::Atoms`, 0xa0 bytes.
 */
public object HkpCogWheelConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val CogWheels: Int = 0x90
}

/**
 * `hkpCollidable`, 0x70 bytes.
 *
 * `hkpCdBody` -> `hkpCollidable`
 */
public object HkpCollidable {
    public const val ByteSize: Int = 0x70

    /** `struct*`, from `hkpCdBody` */
    public const val Shape: Int = 0x0

    /** `uint32`, from `hkpCdBody` */
    public const val ShapeKey: Int = 0x8

    /** `void*`, from `hkpCdBody` */
    public const val Motion: Int = 0x10

    /** `struct*`, from `hkpCdBody` */
    public const val Parent: Int = 0x18

    /** `int8` */
    public const val OwnerOffset: Int = 0x20

    /** `uint8` */
    public const val ForceCollideOntoPpu: Int = 0x21

    /** `uint16` */
    public const val ShapeSizeOnSpu: Int = 0x22

    /** `struct` */
    public const val BroadPhaseHandle: Int = 0x24

    /** `struct` */
    public const val BoundingVolumeData: Int = 0x30

    /** `float` */
    public const val AllowedPenetrationDepth: Int = 0x68
}

/**
 * `hkpCollidable::BoundingVolumeData`, 0x38 bytes.
 */
public object HkpCollidableBoundingVolumeData {
    public const val ByteSize: Int = 0x38

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
    public const val Padding: Int = 0x1f

    /** `uint16` */
    public const val NumChildShapeAabbs: Int = 0x20

    /** `uint16` */
    public const val CapacityChildShapeAabbs: Int = 0x22

    /** `void*` */
    public const val ChildShapeAabbs: Int = 0x28

    /** `void*` */
    public const val ChildShapeKeys: Int = 0x30
}

/**
 * `hkpCollidableCollidableFilter`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpCollidableCollidableFilter {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpCollisionFilter`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter`
 */
public object HkpCollisionFilter {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32` */
    public const val Type: Int = 0x38

    /** `uint32[3]` */
    public const val Postpad: Int = 0x3c
}

/**
 * `hkpCollisionFilterList`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpCollisionFilterList`
 */
public object HkpCollisionFilterList {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `hkArray<ptr>` */
    public const val CollisionFilters: Int = 0x48
}

/**
 * `hkpCompressedMeshShape`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpCompressedMeshShape`
 */
public object HkpCompressedMeshShape {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `int32` */
    public const val BitsPerIndex: Int = 0x30

    /** `int32` */
    public const val BitsPerWIndex: Int = 0x34

    /** `int32` */
    public const val WIndexMask: Int = 0x38

    /** `int32` */
    public const val IndexMask: Int = 0x3c

    /** `float` */
    public const val Radius: Int = 0x40

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x44

    /** `enum:uint8` */
    public const val MaterialType: Int = 0x45

    /** `hkArray<uint32>` */
    public const val Materials: Int = 0x48

    /** `hkArray<uint16>` */
    public const val Materials16: Int = 0x58

    /** `hkArray<uint8>` */
    public const val Materials8: Int = 0x68

    /** `hkArray<hkQsTransform>` */
    public const val Transforms: Int = 0x78

    /** `hkArray<hkVector4>` */
    public const val BigVertices: Int = 0x88

    /** `hkArray<struct>` */
    public const val BigTriangles: Int = 0x98

    /** `hkArray<struct>` */
    public const val Chunks: Int = 0xa8

    /** `hkArray<struct>` */
    public const val ConvexPieces: Int = 0xb8

    /** `float` */
    public const val Error: Int = 0xc8

    /** `struct` */
    public const val Bounds: Int = 0xd0

    /** `uint32` */
    public const val DefaultCollisionFilterInfo: Int = 0xf0

    /** `void*` */
    public const val MeshMaterials: Int = 0xf8

    /** `uint16` */
    public const val MaterialStriding: Int = 0x100

    /** `uint16` */
    public const val NumMaterials: Int = 0x102

    /** `hkArray<struct>` */
    public const val NamedMaterials: Int = 0x108
}

/**
 * `hkpCompressedMeshShape::BigTriangle`, 0x10 bytes.
 */
public object HkpCompressedMeshShapeBigTriangle {
    public const val ByteSize: Int = 0x10

    /** `uint16` */
    public const val A: Int = 0x0

    /** `uint16` */
    public const val B: Int = 0x2

    /** `uint16` */
    public const val C: Int = 0x4

    /** `uint32` */
    public const val Material: Int = 0x8

    /** `uint16` */
    public const val WeldingInfo: Int = 0xc

    /** `uint16` */
    public const val TransformIndex: Int = 0xe
}

/**
 * `hkpCompressedMeshShape::Chunk`, 0x60 bytes.
 */
public object HkpCompressedMeshShapeChunk {
    public const val ByteSize: Int = 0x60

    /** `hkVector4` */
    public const val Offset: Int = 0x0

    /** `hkArray<uint16>` */
    public const val Vertices: Int = 0x10

    /** `hkArray<uint16>` */
    public const val Indices: Int = 0x20

    /** `hkArray<uint16>` */
    public const val StripLengths: Int = 0x30

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0x40

    /** `uint32` */
    public const val MaterialInfo: Int = 0x50

    /** `uint16` */
    public const val Reference: Int = 0x54

    /** `uint16` */
    public const val TransformIndex: Int = 0x56
}

/**
 * `hkpCompressedMeshShape::ConvexPiece`, 0x30 bytes.
 */
public object HkpCompressedMeshShapeConvexPiece {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val Offset: Int = 0x0

    /** `hkArray<uint16>` */
    public const val Vertices: Int = 0x10

    /** `uint16` */
    public const val Reference: Int = 0x20

    /** `uint16` */
    public const val TransformIndex: Int = 0x22
}

/**
 * `hkpCompressedSampledHeightFieldShape`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpHeightFieldShape` -> `hkpSampledHeightFieldShape` -> `hkpCompressedSampledHeightFieldShape`
 */
public object HkpCompressedSampledHeightFieldShape {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `hkArray<struct>`, from `hkpSampledHeightFieldShape` */
    public const val CoarseTreeData: Int = 0x20

    /** `int32`, from `hkpSampledHeightFieldShape` */
    public const val Coarseness: Int = 0x30

    /** `float`, from `hkpSampledHeightFieldShape` */
    public const val RaycastMinY: Int = 0x34

    /** `float`, from `hkpSampledHeightFieldShape` */
    public const val RaycastMaxY: Int = 0x38

    /** `int32`, from `hkpSampledHeightFieldShape` */
    public const val XRes: Int = 0x3c

    /** `int32`, from `hkpSampledHeightFieldShape` */
    public const val ZRes: Int = 0x40

    /** `float`, from `hkpSampledHeightFieldShape` */
    public const val HeightCenter: Int = 0x44

    /** `bool`, from `hkpSampledHeightFieldShape` */
    public const val UseProjectionBasedHeight: Int = 0x48

    /** `enum:uint8`, from `hkpSampledHeightFieldShape` */
    public const val HeightfieldType: Int = 0x49

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val IntToFloatScale: Int = 0x50

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val FloatToIntScale: Int = 0x60

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val FloatToIntOffsetFloorCorrected: Int = 0x70

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val Extents: Int = 0x80

    /** `hkArray<uint16>` */
    public const val Storage: Int = 0x90

    /** `bool` */
    public const val TriangleFlip: Int = 0xa0

    /** `float` */
    public const val Offset: Int = 0xa4

    /** `float` */
    public const val Scale: Int = 0xa8
}

/**
 * `hkpConeLimitConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpConeLimitConstraintAtom`
 */
public object HkpConeLimitConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val TwistAxisInA: Int = 0x3

    /** `uint8` */
    public const val RefAxisInB: Int = 0x4

    /** `enum:uint8` */
    public const val AngleMeasurementMode: Int = 0x5

    /** `uint16` */
    public const val MemOffsetToAngleOffset: Int = 0x6

    /** `float` */
    public const val MinAngle: Int = 0x8

    /** `float` */
    public const val MaxAngle: Int = 0xc

    /** `float` */
    public const val AngularLimitsTauFactor: Int = 0x10

    /** `float` */
    public const val AngularLimitsDampFactor: Int = 0x14
}

/**
 * `hkpConstrainedSystemFilter`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpConstrainedSystemFilter`
 */
public object HkpConstrainedSystemFilter {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `struct*` */
    public const val OtherFilter: Int = 0x50
}

/**
 * `hkpConstraintAtom`, 0x10 bytes.
 */
public object HkpConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16` */
    public const val Type: Int = 0x0
}

/**
 * `hkpConstraintChainData`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData`
 */
public object HkpConstraintChainData {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10
}

/**
 * `hkpConstraintChainInstance`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintInstance` -> `hkpConstraintChainInstance`
 */
public object HkpConstraintChainInstance {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpConstraintInstance` */
    public const val Owner: Int = 0x10

    /** `struct*`, from `hkpConstraintInstance` */
    public const val Data: Int = 0x18

    /** `struct*`, from `hkpConstraintInstance` */
    public const val ConstraintModifiers: Int = 0x20

    /** `struct*[2]`, from `hkpConstraintInstance` */
    public const val Entities: Int = 0x28

    /** `enum:uint8`, from `hkpConstraintInstance` */
    public const val Priority: Int = 0x38

    /** `bool`, from `hkpConstraintInstance` */
    public const val WantRuntime: Int = 0x39

    /** `enum:uint8`, from `hkpConstraintInstance` */
    public const val DestructionRemapInfo: Int = 0x3a

    /** `struct`, from `hkpConstraintInstance` */
    public const val Listeners: Int = 0x40

    /** `hkStringPtr`, from `hkpConstraintInstance` */
    public const val Name: Int = 0x50

    /** `hkUlong`, from `hkpConstraintInstance` */
    public const val UserData: Int = 0x58

    /** `void*`, from `hkpConstraintInstance` */
    public const val Internal: Int = 0x60

    /** `uint32`, from `hkpConstraintInstance` */
    public const val Uid: Int = 0x68

    /** `hkArray<ptr>` */
    public const val ChainedEntities: Int = 0x70

    /** `struct*` */
    public const val Action: Int = 0x80

    /** `hkUlong` */
    public const val ChainConnectedness: Int = 0x88
}

/**
 * `hkpConstraintChainInstanceAction`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpConstraintChainInstanceAction`
 */
public object HkpConstraintChainInstanceAction {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*` */
    public const val ConstraintInstance: Int = 0x30
}

/**
 * `hkpConstraintCollisionFilter`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpPairCollisionFilter` -> `hkpConstraintCollisionFilter`
 */
public object HkpConstraintCollisionFilter {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `struct`, from `hkpPairCollisionFilter` */
    public const val DisabledPairs: Int = 0x48

    /** `struct*`, from `hkpPairCollisionFilter` */
    public const val ChildFilter: Int = 0x58
}

/**
 * `hkpConstraintData`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData`
 */
public object HkpConstraintData {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong` */
    public const val UserData: Int = 0x10
}

/**
 * `hkpConstraintInstance`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintInstance`
 */
public object HkpConstraintInstance {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*` */
    public const val Owner: Int = 0x10

    /** `struct*` */
    public const val Data: Int = 0x18

    /** `struct*` */
    public const val ConstraintModifiers: Int = 0x20

    /** `struct*[2]` */
    public const val Entities: Int = 0x28

    /** `enum:uint8` */
    public const val Priority: Int = 0x38

    /** `bool` */
    public const val WantRuntime: Int = 0x39

    /** `enum:uint8` */
    public const val DestructionRemapInfo: Int = 0x3a

    /** `struct` */
    public const val Listeners: Int = 0x40

    /** `hkStringPtr` */
    public const val Name: Int = 0x50

    /** `hkUlong` */
    public const val UserData: Int = 0x58

    /** `void*` */
    public const val Internal: Int = 0x60

    /** `uint32` */
    public const val Uid: Int = 0x68
}

/**
 * `hkpConstraintInstance::SmallArraySerializeOverrideType`, 0x10 bytes.
 */
public object HkpConstraintInstanceSmallArraySerializeOverrideType {
    public const val ByteSize: Int = 0x10

    /** `void*` */
    public const val Data: Int = 0x0

    /** `uint16` */
    public const val Size: Int = 0x8

    /** `uint16` */
    public const val CapacityAndFlags: Int = 0xa
}

/**
 * `hkpConstraintListener`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpConstraintListener {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpConstraintMotor`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor`
 */
public object HkpConstraintMotor {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8` */
    public const val Type: Int = 0x10
}

/**
 * `hkpContactListener`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpContactListener {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpConvexListFilter`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexListFilter`
 */
public object HkpConvexListFilter {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkpConvexListShape`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexListShape`
 */
public object HkpConvexListShape {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `float` */
    public const val MinDistanceToUseConvexHullForGetClosestPoints: Int = 0x30

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x40

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x50

    /** `bool` */
    public const val UseCachedAabb: Int = 0x60

    /** `hkArray<ptr>` */
    public const val ChildShapes: Int = 0x68
}

/**
 * `hkpConvexPieceMeshShape`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpConvexPieceMeshShape`
 */
public object HkpConvexPieceMeshShape {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `struct*` */
    public const val ConvexPieceStream: Int = 0x30

    /** `struct*` */
    public const val DisplayMesh: Int = 0x38

    /** `float` */
    public const val Radius: Int = 0x40
}

/**
 * `hkpConvexPieceStreamData`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexPieceStreamData`
 */
public object HkpConvexPieceStreamData {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<uint32>` */
    public const val ConvexPieceStream: Int = 0x10

    /** `hkArray<uint32>` */
    public const val ConvexPieceOffsets: Int = 0x20

    /** `hkArray<uint32>` */
    public const val ConvexPieceSingleTriangles: Int = 0x30
}

/**
 * `hkpConvexShape`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape`
 */
public object HkpConvexShape {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float` */
    public const val Radius: Int = 0x20
}

/**
 * `hkpConvexTransformShape`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase` -> `hkpConvexTransformShape`
 */
public object HkpConvexTransformShape {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `struct`, from `hkpConvexTransformShapeBase` */
    public const val ChildShape: Int = 0x28

    /** `int32`, from `hkpConvexTransformShapeBase` */
    public const val ChildShapeSizeForSpu: Int = 0x38

    /** `hkQsTransform` */
    public const val Transform: Int = 0x40

    /** `hkVector4` */
    public const val ExtraScale: Int = 0x70
}

/**
 * `hkpConvexTransformShapeBase`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase`
 */
public object HkpConvexTransformShapeBase {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `struct` */
    public const val ChildShape: Int = 0x28

    /** `int32` */
    public const val ChildShapeSizeForSpu: Int = 0x38
}

/**
 * `hkpConvexTranslateShape`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase` -> `hkpConvexTranslateShape`
 */
public object HkpConvexTranslateShape {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `struct`, from `hkpConvexTransformShapeBase` */
    public const val ChildShape: Int = 0x28

    /** `int32`, from `hkpConvexTransformShapeBase` */
    public const val ChildShapeSizeForSpu: Int = 0x38

    /** `hkVector4` */
    public const val Translation: Int = 0x40
}

/**
 * `hkpConvexVerticesConnectivity`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexVerticesConnectivity`
 */
public object HkpConvexVerticesConnectivity {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<uint16>` */
    public const val VertexIndices: Int = 0x10

    /** `hkArray<uint8>` */
    public const val NumVerticesPerFace: Int = 0x20
}

/**
 * `hkpConvexVerticesShape`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexVerticesShape`
 */
public object HkpConvexVerticesShape {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x30

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x40

    /** `hkArray<hkMatrix3>` */
    public const val RotatedVertices: Int = 0x50

    /** `int32` */
    public const val NumVertices: Int = 0x60

    /** `bool` */
    public const val UseSpuBuffer: Int = 0x64

    /** `hkArray<hkVector4>` */
    public const val PlaneEquations: Int = 0x68

    /** `struct*` */
    public const val Connectivity: Int = 0x78
}

/**
 * `hkpCylinderShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpCylinderShape`
 */
public object HkpCylinderShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `float` */
    public const val CylRadius: Int = 0x28

    /** `float` */
    public const val CylBaseRadiusFactorForHeightFieldCollisions: Int = 0x2c

    /** `hkVector4` */
    public const val VertexA: Int = 0x30

    /** `hkVector4` */
    public const val VertexB: Int = 0x40

    /** `hkVector4` */
    public const val Perpendicular1: Int = 0x50

    /** `hkVector4` */
    public const val Perpendicular2: Int = 0x60
}

/**
 * `hkpDashpotAction`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpDashpotAction`
 */
public object HkpDashpotAction {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityA: Int = 0x30

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityB: Int = 0x38

    /** `hkVector4[2]` */
    public const val Point: Int = 0x40

    /** `float` */
    public const val Strength: Int = 0x60

    /** `float` */
    public const val Damping: Int = 0x64

    /** `hkVector4` */
    public const val Impulse: Int = 0x70
}

/**
 * `hkpDefaultConvexListFilter`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexListFilter` -> `hkpDefaultConvexListFilter`
 */
public object HkpDefaultConvexListFilter {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkpDefaultWorldMemoryWatchDog`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkWorldMemoryAvailableWatchDog` -> `hkpDefaultWorldMemoryWatchDog`
 */
public object HkpDefaultWorldMemoryWatchDog {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val FreeHeapMemoryRequested: Int = 0x10
}

/**
 * `hkpDeformableAngConstraintAtom`, 0x60 bytes.
 *
 * `hkpConstraintAtom` -> `hkpDeformableAngConstraintAtom`
 */
public object HkpDeformableAngConstraintAtom {
    public const val ByteSize: Int = 0x60

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `hkQuaternion` */
    public const val Offset: Int = 0x10

    /** `hkVector4` */
    public const val YieldStrengthDiag: Int = 0x20

    /** `hkVector4` */
    public const val YieldStrengthOffDiag: Int = 0x30

    /** `hkVector4` */
    public const val UltimateStrengthDiag: Int = 0x40

    /** `hkVector4` */
    public const val UltimateStrengthOffDiag: Int = 0x50
}

/**
 * `hkpDeformableFixedConstraintData`, 0x170 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpDeformableFixedConstraintData`
 */
public object HkpDeformableFixedConstraintData {
    public const val ByteSize: Int = 0x170

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpDeformableFixedConstraintData::Atoms`, 0x150 bytes.
 */
public object HkpDeformableFixedConstraintDataAtoms {
    public const val ByteSize: Int = 0x150

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Lin: Int = 0x90

    /** `struct` */
    public const val Ang: Int = 0xf0
}

/**
 * `hkpDeformableLinConstraintAtom`, 0x60 bytes.
 *
 * `hkpConstraintAtom` -> `hkpDeformableLinConstraintAtom`
 */
public object HkpDeformableLinConstraintAtom {
    public const val ByteSize: Int = 0x60

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `hkVector4` */
    public const val Offset: Int = 0x10

    /** `hkVector4` */
    public const val YieldStrengthDiag: Int = 0x20

    /** `hkVector4` */
    public const val YieldStrengthOffDiag: Int = 0x30

    /** `hkVector4` */
    public const val UltimateStrengthDiag: Int = 0x40

    /** `hkVector4` */
    public const val UltimateStrengthOffDiag: Int = 0x50
}

/**
 * `hkpDisableEntityCollisionFilter`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpDisableEntityCollisionFilter`
 */
public object HkpDisableEntityCollisionFilter {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `hkArray<ptr>` */
    public const val DisabledEntities: Int = 0x50
}

/**
 * `hkpDisplayBindingData`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingData`
 */
public object HkpDisplayBindingData {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val RigidBodyBindings: Int = 0x10

    /** `hkArray<ptr>` */
    public const val PhysicsSystemBindings: Int = 0x20
}

/**
 * `hkpDisplayBindingData::PhysicsSystem`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingData::PhysicsSystem`
 */
public object HkpDisplayBindingDataPhysicsSystem {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Bindings: Int = 0x10

    /** `struct*` */
    public const val System: Int = 0x20
}

/**
 * `hkpDisplayBindingData::RigidBody`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingData::RigidBody`
 */
public object HkpDisplayBindingDataRigidBody {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val RigidBody: Int = 0x10

    /** `struct*` */
    public const val DisplayObjectPtr: Int = 0x18

    /** `hkMatrix4` */
    public const val RigidBodyFromDisplayObjectTransform: Int = 0x20
}

/**
 * `hkpEllipticalLimitConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpEllipticalLimitConstraintAtom`
 */
public object HkpEllipticalLimitConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `bool` */
    public const val ElipticalLimitEnabled: Int = 0x3

    /** `bool` */
    public const val ConeLimitEnabled: Int = 0x4

    /** `float` */
    public const val Angle0: Int = 0x8

    /** `float` */
    public const val Angle1: Int = 0xc

    /** `float` */
    public const val ConeAngle: Int = 0x10

    /** `float` */
    public const val AngleCorrected0: Int = 0x14

    /** `float` */
    public const val AngleCorrected1: Int = 0x18

    /** `float` */
    public const val ConeAngleCorrected: Int = 0x1c

    /** `float` */
    public const val AngleCorrected0Inv: Int = 0x20

    /** `float` */
    public const val AngleCorrected1Inv: Int = 0x24

    /** `float` */
    public const val AngularLimitsTauFactor: Int = 0x28

    /** `float` */
    public const val AngularLimitsDampFactor: Int = 0x2c
}

/**
 * `hkpEntity`, 0x2c0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpEntity`
 */
public object HkpEntity {
    public const val ByteSize: Int = 0x2c0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `struct` */
    public const val Material: Int = 0xc8

    /** `void*` */
    public const val LimitContactImpulseUtilAndFlag: Int = 0xd8

    /** `float` */
    public const val DamageMultiplier: Int = 0xe0

    /** `void*` */
    public const val BreakableBody: Int = 0xe8

    /** `uint32` */
    public const val SolverData: Int = 0xf0

    /** `uint16` */
    public const val StorageIndex: Int = 0xf4

    /** `uint16` */
    public const val ContactPointCallbackDelay: Int = 0xf6

    /** `struct` */
    public const val ConstraintsMaster: Int = 0xf8

    /** `hkArray<ptr>` */
    public const val ConstraintsSlave: Int = 0x108

    /** `hkArray<uint8>` */
    public const val ConstraintRuntime: Int = 0x118

    /** `void*` */
    public const val SimulationIsland: Int = 0x128

    /** `int8` */
    public const val AutoRemoveLevel: Int = 0x130

    /** `uint8` */
    public const val NumShapeKeysInContactPointProperties: Int = 0x131

    /** `uint8` */
    public const val ResponseModifierFlags: Int = 0x132

    /** `uint32` */
    public const val Uid: Int = 0x134

    /** `struct` */
    public const val SpuCollisionCallback: Int = 0x138

    /** `struct` */
    public const val Motion: Int = 0x150

    /** `struct` */
    public const val ContactListeners: Int = 0x290

    /** `struct` */
    public const val Actions: Int = 0x2a0

    /** `struct*` */
    public const val LocalFrame: Int = 0x2b0

    /** `struct*` */
    public const val ExtendedListeners: Int = 0x2b8
}

/**
 * `hkpEntity::ExtendedListeners`, 0x20 bytes.
 */
public object HkpEntityExtendedListeners {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val ActivationListeners: Int = 0x0

    /** `struct` */
    public const val EntityListeners: Int = 0x10
}

/**
 * `hkpEntity::SmallArraySerializeOverrideType`, 0x10 bytes.
 */
public object HkpEntitySmallArraySerializeOverrideType {
    public const val ByteSize: Int = 0x10

    /** `void*` */
    public const val Data: Int = 0x0

    /** `uint16` */
    public const val Size: Int = 0x8

    /** `uint16` */
    public const val CapacityAndFlags: Int = 0xa
}

/**
 * `hkpEntity::SpuCollisionCallback`, 0x10 bytes.
 */
public object HkpEntitySpuCollisionCallback {
    public const val ByteSize: Int = 0x10

    /** `void*` */
    public const val Util: Int = 0x0

    /** `uint16` */
    public const val Capacity: Int = 0x8

    /** `uint8` */
    public const val EventFilter: Int = 0xa

    /** `uint8` */
    public const val UserFilter: Int = 0xb
}

/**
 * `hkpEntityListener`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpEntityListener {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpExtendedMeshShape`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape`
 */
public object HkpExtendedMeshShape {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `struct` */
    public const val EmbeddedTrianglesSubpart: Int = 0x30

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0xc0

    /** `hkVector4` */
    public const val AabbCenter: Int = 0xd0

    /** `void*` */
    public const val MaterialClass: Int = 0xe0

    /** `int32` */
    public const val NumBitsForSubpartIndex: Int = 0xe8

    /** `hkArray<struct>` */
    public const val TrianglesSubparts: Int = 0xf0

    /** `hkArray<struct>` */
    public const val ShapesSubparts: Int = 0x100

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0x110

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x120

    /** `uint32` */
    public const val DefaultCollisionFilterInfo: Int = 0x124

    /** `int32` */
    public const val CachedNumChildShapes: Int = 0x128

    /** `float` */
    public const val TriangleRadius: Int = 0x12c

    /** `int32` */
    public const val Padding: Int = 0x130
}

/**
 * `hkpExtendedMeshShape::ShapesSubpart`, 0x50 bytes.
 *
 * `hkpExtendedMeshShape::Subpart` -> `hkpExtendedMeshShape::ShapesSubpart`
 */
public object HkpExtendedMeshShapeShapesSubpart {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkpExtendedMeshShape::Subpart` */
    public const val TypeAndFlags: Int = 0x0

    /** `uint16`, from `hkpExtendedMeshShape::Subpart` */
    public const val ShapeInfo: Int = 0x2

    /** `int16`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialStriding: Int = 0x4

    /** `uint16`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialIndexStriding: Int = 0x6

    /** `void*`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialIndexBase: Int = 0x8

    /** `void*`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialBase: Int = 0x10

    /** `hkUlong`, from `hkpExtendedMeshShape::Subpart` */
    public const val UserData: Int = 0x18

    /** `hkArray<ptr>` */
    public const val ChildShapes: Int = 0x20

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x30

    /** `hkVector4` */
    public const val Translation: Int = 0x40
}

/**
 * `hkpExtendedMeshShape::Subpart`, 0x20 bytes.
 */
public object HkpExtendedMeshShapeSubpart {
    public const val ByteSize: Int = 0x20

    /** `uint16` */
    public const val TypeAndFlags: Int = 0x0

    /** `uint16` */
    public const val ShapeInfo: Int = 0x2

    /** `int16` */
    public const val MaterialStriding: Int = 0x4

    /** `uint16` */
    public const val MaterialIndexStriding: Int = 0x6

    /** `void*` */
    public const val MaterialIndexBase: Int = 0x8

    /** `void*` */
    public const val MaterialBase: Int = 0x10

    /** `hkUlong` */
    public const val UserData: Int = 0x18
}

/**
 * `hkpExtendedMeshShape::TrianglesSubpart`, 0x90 bytes.
 *
 * `hkpExtendedMeshShape::Subpart` -> `hkpExtendedMeshShape::TrianglesSubpart`
 */
public object HkpExtendedMeshShapeTrianglesSubpart {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkpExtendedMeshShape::Subpart` */
    public const val TypeAndFlags: Int = 0x0

    /** `uint16`, from `hkpExtendedMeshShape::Subpart` */
    public const val ShapeInfo: Int = 0x2

    /** `int16`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialStriding: Int = 0x4

    /** `uint16`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialIndexStriding: Int = 0x6

    /** `void*`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialIndexBase: Int = 0x8

    /** `void*`, from `hkpExtendedMeshShape::Subpart` */
    public const val MaterialBase: Int = 0x10

    /** `hkUlong`, from `hkpExtendedMeshShape::Subpart` */
    public const val UserData: Int = 0x18

    /** `int32` */
    public const val NumTriangleShapes: Int = 0x20

    /** `void*` */
    public const val VertexBase: Int = 0x28

    /** `int32` */
    public const val NumVertices: Int = 0x30

    /** `void*` */
    public const val IndexBase: Int = 0x38

    /** `uint16` */
    public const val VertexStriding: Int = 0x40

    /** `int32` */
    public const val TriangleOffset: Int = 0x44

    /** `uint16` */
    public const val IndexStriding: Int = 0x48

    /** `enum:int8` */
    public const val StridingType: Int = 0x4a

    /** `int8` */
    public const val FlipAlternateTriangles: Int = 0x4b

    /** `hkVector4` */
    public const val Extrusion: Int = 0x50

    /** `hkQsTransform` */
    public const val Transform: Int = 0x60
}

/**
 * `hkpExtendedMeshShapeBreakableMaterial`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMaterial` -> `hkpBreakableMultiMaterial` -> `hkpExtendedMeshShapeBreakableMaterial`
 */
public object HkpExtendedMeshShapeBreakableMaterial {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float`, from `hkpBreakableMaterial` */
    public const val Strength: Int = 0x10

    /** `int32`, from `hkpBreakableMaterial` */
    public const val TypeAndFlags: Int = 0x14

    /** `struct*`, from `hkpBreakableMaterial` */
    public const val Properties: Int = 0x18

    /** `hkArray<ptr>`, from `hkpBreakableMultiMaterial` */
    public const val SubMaterials: Int = 0x20

    /** `struct*`, from `hkpBreakableMultiMaterial` */
    public const val InverseMapping: Int = 0x30
}

/**
 * `hkpFastMeshShape`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpFastMeshShape`
 */
public object HkpFastMeshShape {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `hkVector4`, from `hkpMeshShape` */
    public const val Scaling: Int = 0x30

    /** `int32`, from `hkpMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0x40

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val Subparts: Int = 0x48

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val WeldingInfo: Int = 0x58

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val WeldingType: Int = 0x68

    /** `float`, from `hkpMeshShape` */
    public const val Radius: Int = 0x6c

    /** `int32[3]`, from `hkpMeshShape` */
    public const val Pad: Int = 0x70
}

/**
 * `hkpFirstPersonGun`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun`
 */
public object HkpFirstPersonGun {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8` */
    public const val Type: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x18

    /** `enum:uint8` */
    public const val KeyboardKey: Int = 0x20

    /** `hkArray<void>` */
    public const val Listeners: Int = 0x28
}

/**
 * `hkpFixedConstraintData`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpFixedConstraintData`
 */
public object HkpFixedConstraintData {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpFixedConstraintData::Atoms`, 0xc0 bytes.
 */
public object HkpFixedConstraintDataAtoms {
    public const val ByteSize: Int = 0xc0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x90

    /** `struct` */
    public const val BallSocket: Int = 0xa0

    /** `struct` */
    public const val Ang: Int = 0xb0
}

/**
 * `hkpFixedRigidMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion` -> `hkpFixedRigidMotion`
 */
public object HkpFixedRigidMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpGenericConstraintData`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpGenericConstraintData`
 */
public object HkpGenericConstraintData {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `struct` */
    public const val Scheme: Int = 0x40
}

/**
 * `hkpGenericConstraintDataScheme`, 0x50 bytes.
 */
public object HkpGenericConstraintDataScheme {
    public const val ByteSize: Int = 0x50

    /** `struct` */
    public const val Info: Int = 0x0

    /** `hkArray<hkVector4>` */
    public const val Data: Int = 0x10

    /** `hkArray<int32>` */
    public const val Commands: Int = 0x20

    /** `hkArray<void>` */
    public const val Modifiers: Int = 0x30

    /** `hkArray<ptr>` */
    public const val Motors: Int = 0x40
}

/**
 * `hkpGenericConstraintDataScheme::ConstraintInfo`, 0x10 bytes.
 */
public object HkpGenericConstraintDataSchemeConstraintInfo {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val MaxSizeOfSchema: Int = 0x0

    /** `int32` */
    public const val SizeOfSchemas: Int = 0x4

    /** `int32` */
    public const val NumSolverResults: Int = 0x8

    /** `int32` */
    public const val NumSolverElemTemps: Int = 0xc
}

/**
 * `hkpGravityGun`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpGravityGun`
 */
public object HkpGravityGun {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x10

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Name: Int = 0x18

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x20

    /** `hkArray<void>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x28

    /** `hkArray<void>` */
    public const val GrabbedBodies: Int = 0x38

    /** `int32` */
    public const val MaxNumObjectsPicked: Int = 0x48

    /** `float` */
    public const val MaxMassOfObjectPicked: Int = 0x4c

    /** `float` */
    public const val MaxDistOfObjectPicked: Int = 0x50

    /** `float` */
    public const val ImpulseAppliedWhenObjectNotPicked: Int = 0x54

    /** `float` */
    public const val ThrowVelocity: Int = 0x58

    /** `hkVector4` */
    public const val CapturedObjectPosition: Int = 0x60

    /** `hkVector4` */
    public const val CapturedObjectsOffset: Int = 0x70
}

/**
 * `hkpGroupCollisionFilter`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpGroupCollisionFilter`
 */
public object HkpGroupCollisionFilter {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `bool` */
    public const val NoGroupCollisionEnabled: Int = 0x48

    /** `uint32[32]` */
    public const val CollisionGroups: Int = 0x4c
}

/**
 * `hkpGroupFilter`, 0x110 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpGroupFilter`
 */
public object HkpGroupFilter {
    public const val ByteSize: Int = 0x110

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `int32` */
    public const val NextFreeSystemGroup: Int = 0x48

    /** `uint32[32]` */
    public const val CollisionLookupTable: Int = 0x4c

    /** `hkVector4[4]` */
    public const val Pad256: Int = 0xd0
}

/**
 * `hkpHeightFieldShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpHeightFieldShape`
 */
public object HkpHeightFieldShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18
}

/**
 * `hkpHingeConstraintData`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpHingeConstraintData`
 */
public object HkpHingeConstraintData {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpHingeConstraintData::Atoms`, 0xc0 bytes.
 */
public object HkpHingeConstraintDataAtoms {
    public const val ByteSize: Int = 0xc0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x90

    /** `struct` */
    public const val `2dAng`: Int = 0xa0

    /** `struct` */
    public const val BallSocket: Int = 0xb0
}

/**
 * `hkpHingeLimitsData`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpHingeLimitsData`
 */
public object HkpHingeLimitsData {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpHingeLimitsData::Atoms`, 0xa0 bytes.
 */
public object HkpHingeLimitsDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Rotations: Int = 0x0

    /** `struct` */
    public const val AngLimit: Int = 0x70

    /** `struct` */
    public const val `2dAng`: Int = 0x90
}

/**
 * `hkpIgnoreModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpIgnoreModifierConstraintAtom`
 */
public object HkpIgnoreModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x18

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x20
}

/**
 * `hkpKeyframedRigidMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion`
 */
public object HkpKeyframedRigidMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpLimitedForceConstraintMotor`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor`
 */
public object HkpLimitedForceConstraintMotor {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x10

    /** `float` */
    public const val MinForce: Int = 0x18

    /** `float` */
    public const val MaxForce: Int = 0x1c
}

/**
 * `hkpLimitedHingeConstraintData`, 0x130 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpLimitedHingeConstraintData`
 */
public object HkpLimitedHingeConstraintData {
    public const val ByteSize: Int = 0x130

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpLimitedHingeConstraintData::Atoms`, 0x110 bytes.
 */
public object HkpLimitedHingeConstraintDataAtoms {
    public const val ByteSize: Int = 0x110

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x90

    /** `struct` */
    public const val AngMotor: Int = 0xa0

    /** `struct` */
    public const val AngFriction: Int = 0xc0

    /** `struct` */
    public const val AngLimit: Int = 0xd0

    /** `struct` */
    public const val `2dAng`: Int = 0xf0

    /** `struct` */
    public const val BallSocket: Int = 0x100
}

/**
 * `hkpLinConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinConstraintAtom`
 */
public object HkpLinConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val AxisIndex: Int = 0x2
}

/**
 * `hkpLinFrictionConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinFrictionConstraintAtom`
 */
public object HkpLinFrictionConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val FrictionAxis: Int = 0x3

    /** `float` */
    public const val MaxFrictionForce: Int = 0x4
}

/**
 * `hkpLinLimitConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinLimitConstraintAtom`
 */
public object HkpLinLimitConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val AxisIndex: Int = 0x2

    /** `float` */
    public const val Min: Int = 0x4

    /** `float` */
    public const val Max: Int = 0x8
}

/**
 * `hkpLinMotorConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinMotorConstraintAtom`
 */
public object HkpLinMotorConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `bool` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val MotorAxis: Int = 0x3

    /** `int16` */
    public const val InitializedOffset: Int = 0x4

    /** `int16` */
    public const val PreviousTargetPositionOffset: Int = 0x6

    /** `struct*` */
    public const val Motor: Int = 0x8

    /** `float` */
    public const val TargetPosition: Int = 0x10
}

/**
 * `hkpLinSoftConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinSoftConstraintAtom`
 */
public object HkpLinSoftConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val AxisIndex: Int = 0x2

    /** `float` */
    public const val Tau: Int = 0x4

    /** `float` */
    public const val Damping: Int = 0x8
}

/**
 * `hkpLinearClearanceConstraintData`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpLinearClearanceConstraintData`
 */
public object HkpLinearClearanceConstraintData {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpLinearClearanceConstraintData::Atoms`, 0x120 bytes.
 */
public object HkpLinearClearanceConstraintDataAtoms {
    public const val ByteSize: Int = 0x120

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Motor: Int = 0x90

    /** `struct` */
    public const val Friction0: Int = 0xb0

    /** `struct` */
    public const val Friction1: Int = 0xc0

    /** `struct` */
    public const val Friction2: Int = 0xd0

    /** `struct` */
    public const val Ang: Int = 0xe0

    /** `struct` */
    public const val LinLimit0: Int = 0xf0

    /** `struct` */
    public const val LinLimit1: Int = 0x100

    /** `struct` */
    public const val LinLimit2: Int = 0x110
}

/**
 * `hkpLinearParametricCurve`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpParametricCurve` -> `hkpLinearParametricCurve`
 */
public object HkpLinearParametricCurve {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val SmoothingFactor: Int = 0x10

    /** `bool` */
    public const val ClosedLoop: Int = 0x14

    /** `hkVector4` */
    public const val DirNotParallelToTangentAlongWholePath: Int = 0x20

    /** `hkArray<hkVector4>` */
    public const val Points: Int = 0x30

    /** `hkArray<float>` */
    public const val Distance: Int = 0x40
}

/**
 * `hkpLinkedCollidable`, 0x80 bytes.
 *
 * `hkpCdBody` -> `hkpCollidable` -> `hkpLinkedCollidable`
 */
public object HkpLinkedCollidable {
    public const val ByteSize: Int = 0x80

    /** `struct*`, from `hkpCdBody` */
    public const val Shape: Int = 0x0

    /** `uint32`, from `hkpCdBody` */
    public const val ShapeKey: Int = 0x8

    /** `void*`, from `hkpCdBody` */
    public const val Motion: Int = 0x10

    /** `struct*`, from `hkpCdBody` */
    public const val Parent: Int = 0x18

    /** `int8`, from `hkpCollidable` */
    public const val OwnerOffset: Int = 0x20

    /** `uint8`, from `hkpCollidable` */
    public const val ForceCollideOntoPpu: Int = 0x21

    /** `uint16`, from `hkpCollidable` */
    public const val ShapeSizeOnSpu: Int = 0x22

    /** `struct`, from `hkpCollidable` */
    public const val BroadPhaseHandle: Int = 0x24

    /** `struct`, from `hkpCollidable` */
    public const val BoundingVolumeData: Int = 0x30

    /** `float`, from `hkpCollidable` */
    public const val AllowedPenetrationDepth: Int = 0x68

    /** `hkArray<void>` */
    public const val CollisionEntries: Int = 0x70
}

/**
 * `hkpListShape`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpListShape`
 */
public object HkpListShape {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `hkArray<struct>` */
    public const val ChildInfo: Int = 0x30

    /** `uint16` */
    public const val Flags: Int = 0x40

    /** `uint16` */
    public const val NumDisabledChildren: Int = 0x42

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x50

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x60

    /** `uint32[8]` */
    public const val EnabledChildren: Int = 0x70
}

/**
 * `hkpListShape::ChildInfo`, 0x20 bytes.
 */
public object HkpListShapeChildInfo {
    public const val ByteSize: Int = 0x20

    /** `struct*` */
    public const val Shape: Int = 0x0

    /** `uint32` */
    public const val CollisionFilterInfo: Int = 0x8

    /** `uint16` */
    public const val ShapeInfo: Int = 0xc

    /** `int16` */
    public const val ShapeSize: Int = 0xe

    /** `int32` */
    public const val NumChildShapes: Int = 0x10
}

/**
 * `hkpListShapeBreakableMaterial`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMaterial` -> `hkpBreakableMultiMaterial` -> `hkpListShapeBreakableMaterial`
 */
public object HkpListShapeBreakableMaterial {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float`, from `hkpBreakableMaterial` */
    public const val Strength: Int = 0x10

    /** `int32`, from `hkpBreakableMaterial` */
    public const val TypeAndFlags: Int = 0x14

    /** `struct*`, from `hkpBreakableMaterial` */
    public const val Properties: Int = 0x18

    /** `hkArray<ptr>`, from `hkpBreakableMultiMaterial` */
    public const val SubMaterials: Int = 0x20

    /** `struct*`, from `hkpBreakableMultiMaterial` */
    public const val InverseMapping: Int = 0x30
}

/**
 * `hkpMalleableConstraintData`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpWrappedConstraintData` -> `hkpMalleableConstraintData`
 */
public object HkpMalleableConstraintData {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct*`, from `hkpWrappedConstraintData` */
    public const val ConstraintData: Int = 0x18

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `float` */
    public const val Strength: Int = 0x40
}

/**
 * `hkpMassChangerModifierConstraintAtom`, 0x50 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpMassChangerModifierConstraintAtom`
 */
public object HkpMassChangerModifierConstraintAtom {
    public const val ByteSize: Int = 0x50

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x18

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x20

    /** `hkVector4` */
    public const val FactorA: Int = 0x30

    /** `hkVector4` */
    public const val FactorB: Int = 0x40
}

/**
 * `hkpMaterial`, 0xc bytes.
 */
public object HkpMaterial {
    public const val ByteSize: Int = 0xc

    /** `enum:int8` */
    public const val ResponseType: Int = 0x0

    /** `hkHalf` */
    public const val RollingFrictionMultiplier: Int = 0x2

    /** `float` */
    public const val Friction: Int = 0x4

    /** `float` */
    public const val Restitution: Int = 0x8
}

/**
 * `hkpMaxSizeMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion` -> `hkpMaxSizeMotion`
 */
public object HkpMaxSizeMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpMeshMaterial`, 0x4 bytes.
 */
public object HkpMeshMaterial {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val FilterInfo: Int = 0x0
}

/**
 * `hkpMeshShape`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape`
 */
public object HkpMeshShape {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `hkVector4` */
    public const val Scaling: Int = 0x30

    /** `int32` */
    public const val NumBitsForSubpartIndex: Int = 0x40

    /** `hkArray<struct>` */
    public const val Subparts: Int = 0x48

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0x58

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x68

    /** `float` */
    public const val Radius: Int = 0x6c

    /** `int32[3]` */
    public const val Pad: Int = 0x70
}

/**
 * `hkpMeshShape::Subpart`, 0x50 bytes.
 */
public object HkpMeshShapeSubpart {
    public const val ByteSize: Int = 0x50

    /** `void*` */
    public const val VertexBase: Int = 0x0

    /** `int32` */
    public const val VertexStriding: Int = 0x8

    /** `int32` */
    public const val NumVertices: Int = 0xc

    /** `void*` */
    public const val IndexBase: Int = 0x10

    /** `enum:int8` */
    public const val StridingType: Int = 0x18

    /** `enum:int8` */
    public const val MaterialIndexStridingType: Int = 0x19

    /** `int32` */
    public const val IndexStriding: Int = 0x1c

    /** `int32` */
    public const val FlipAlternateTriangles: Int = 0x20

    /** `int32` */
    public const val NumTriangles: Int = 0x24

    /** `void*` */
    public const val MaterialIndexBase: Int = 0x28

    /** `int32` */
    public const val MaterialIndexStriding: Int = 0x30

    /** `void*` */
    public const val MaterialBase: Int = 0x38

    /** `int32` */
    public const val MaterialStriding: Int = 0x40

    /** `int32` */
    public const val NumMaterials: Int = 0x44

    /** `int32` */
    public const val TriangleOffset: Int = 0x48
}

/**
 * `hkpModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom`
 */
public object HkpModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16` */
    public const val ChildSize: Int = 0x12

    /** `struct*` */
    public const val Child: Int = 0x18

    /** `uint32[2]` */
    public const val Pad: Int = 0x20
}

/**
 * `hkpMoppBvTreeShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvTreeShape` -> `hkMoppBvTreeShapeBase` -> `hkpMoppBvTreeShape`
 */
public object HkpMoppBvTreeShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val BvTreeType: Int = 0x20

    /** `struct*`, from `hkMoppBvTreeShapeBase` */
    public const val Code: Int = 0x28

    /** `void*`, from `hkMoppBvTreeShapeBase` */
    public const val MoppData: Int = 0x30

    /** `uint32`, from `hkMoppBvTreeShapeBase` */
    public const val MoppDataSize: Int = 0x38

    /** `hkVector4`, from `hkMoppBvTreeShapeBase` */
    public const val CodeInfoCopy: Int = 0x40

    /** `struct` */
    public const val Child: Int = 0x50

    /** `int32` */
    public const val ChildSize: Int = 0x60
}

/**
 * `hkpMoppCode`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMoppCode`
 */
public object HkpMoppCode {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Info: Int = 0x10

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x20

    /** `enum:int8` */
    public const val BuildType: Int = 0x30
}

/**
 * `hkpMoppCode::CodeInfo`, 0x10 bytes.
 */
public object HkpMoppCodeCodeInfo {
    public const val ByteSize: Int = 0x10

    /** `hkVector4` */
    public const val Offset: Int = 0x0
}

/**
 * `hkpMoppCodeReindexedTerminal`, 0x8 bytes.
 */
public object HkpMoppCodeReindexedTerminal {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val OrigShapeKey: Int = 0x0

    /** `uint32` */
    public const val ReindexedShapeKey: Int = 0x4
}

/**
 * `hkpMoppModifier`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpMoppModifier {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion`
 */
public object HkpMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8` */
    public const val Type: Int = 0x10

    /** `uint8` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct` */
    public const val MotionState: Int = 0x20

    /** `hkVector4` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*` */
    public const val SavedMotion: Int = 0x128

    /** `uint16` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpMotorAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpMotorAction`
 */
public object HkpMotorAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*`, from `hkpUnaryAction` */
    public const val Entity: Int = 0x30

    /** `hkVector4` */
    public const val Axis: Int = 0x40

    /** `float` */
    public const val SpinRate: Int = 0x50

    /** `float` */
    public const val Gain: Int = 0x54

    /** `bool` */
    public const val Active: Int = 0x58
}

/**
 * `hkpMountedBallGun`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpBallGun` -> `hkpMountedBallGun`
 */
public object HkpMountedBallGun {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x10

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Name: Int = 0x18

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x20

    /** `hkArray<void>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x28

    /** `float`, from `hkpBallGun` */
    public const val BulletRadius: Int = 0x38

    /** `float`, from `hkpBallGun` */
    public const val BulletVelocity: Int = 0x3c

    /** `float`, from `hkpBallGun` */
    public const val BulletMass: Int = 0x40

    /** `float`, from `hkpBallGun` */
    public const val DamageMultiplier: Int = 0x44

    /** `int32`, from `hkpBallGun` */
    public const val MaxBulletsInWorld: Int = 0x48

    /** `hkVector4`, from `hkpBallGun` */
    public const val BulletOffsetFromCenter: Int = 0x50

    /** `void*`, from `hkpBallGun` */
    public const val AddedBodies: Int = 0x60

    /** `hkVector4` */
    public const val Position: Int = 0x70
}

/**
 * `hkpMouseSpringAction`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpMouseSpringAction`
 */
public object HkpMouseSpringAction {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*`, from `hkpUnaryAction` */
    public const val Entity: Int = 0x30

    /** `hkVector4` */
    public const val PositionInRbLocal: Int = 0x40

    /** `hkVector4` */
    public const val MousePositionInWorld: Int = 0x50

    /** `float` */
    public const val SpringDamping: Int = 0x60

    /** `float` */
    public const val SpringElasticity: Int = 0x64

    /** `float` */
    public const val MaxRelativeForce: Int = 0x68

    /** `float` */
    public const val ObjectDamping: Int = 0x6c

    /** `uint32` */
    public const val ShapeKey: Int = 0x70

    /** `hkArray<ptr>` */
    public const val ApplyCallbacks: Int = 0x78
}

/**
 * `hkpMovingSurfaceModifierConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpMovingSurfaceModifierConstraintAtom`
 */
public object HkpMovingSurfaceModifierConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x18

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x20

    /** `hkVector4` */
    public const val Velocity: Int = 0x30
}

/**
 * `hkpMultiRayShape`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpMultiRayShape`
 */
public object HkpMultiRayShape {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `hkArray<struct>` */
    public const val Rays: Int = 0x20

    /** `float` */
    public const val RayPenetrationDistance: Int = 0x30
}

/**
 * `hkpMultiRayShape::Ray`, 0x20 bytes.
 */
public object HkpMultiRayShapeRay {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val Start: Int = 0x0

    /** `hkVector4` */
    public const val End: Int = 0x10
}

/**
 * `hkpMultiSphereShape`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpMultiSphereShape`
 */
public object HkpMultiSphereShape {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `int32` */
    public const val NumSpheres: Int = 0x20

    /** `hkVector4[8]` */
    public const val Spheres: Int = 0x30
}

/**
 * `hkpNamedMeshMaterial`, 0x10 bytes.
 *
 * `hkpMeshMaterial` -> `hkpNamedMeshMaterial`
 */
public object HkpNamedMeshMaterial {
    public const val ByteSize: Int = 0x10

    /** `uint32`, from `hkpMeshMaterial` */
    public const val FilterInfo: Int = 0x0

    /** `hkStringPtr` */
    public const val Name: Int = 0x8
}

/**
 * `hkpNullCollisionFilter`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpNullCollisionFilter`
 */
public object HkpNullCollisionFilter {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c
}

/**
 * `hkpOverwritePivotConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpOverwritePivotConstraintAtom`
 */
public object HkpOverwritePivotConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val CopyToPivotBFromPivotA: Int = 0x2
}

/**
 * `hkpPairCollisionFilter`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpPairCollisionFilter`
 */
public object HkpPairCollisionFilter {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x30

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x38

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x3c

    /** `struct` */
    public const val DisabledPairs: Int = 0x48

    /** `struct*` */
    public const val ChildFilter: Int = 0x58
}

/**
 * `hkpPairCollisionFilter::MapPairFilterKeyOverrideType`, 0x10 bytes.
 */
public object HkpPairCollisionFilterMapPairFilterKeyOverrideType {
    public const val ByteSize: Int = 0x10

    /** `void*` */
    public const val Elem: Int = 0x0

    /** `int32` */
    public const val NumElems: Int = 0x8

    /** `int32` */
    public const val HashMod: Int = 0xc
}

/**
 * `hkpParametricCurve`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpParametricCurve`
 */
public object HkpParametricCurve {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkpPhantom`, 0xe8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom`
 */
public object HkpPhantom {
    public const val ByteSize: Int = 0xe8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `hkArray<void>` */
    public const val OverlapListeners: Int = 0xc8

    /** `hkArray<void>` */
    public const val PhantomListeners: Int = 0xd8
}

/**
 * `hkpPhantomCallbackShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpPhantomCallbackShape`
 */
public object HkpPhantomCallbackShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18
}

/**
 * `hkpPhantomOverlapListener`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpPhantomOverlapListener {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpPhysicsData`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsData`
 */
public object HkpPhysicsData {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val WorldCinfo: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Systems: Int = 0x18
}

/**
 * `hkpPhysicsSystem`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsSystem`
 */
public object HkpPhysicsSystem {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val RigidBodies: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Constraints: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Actions: Int = 0x30

    /** `hkArray<ptr>` */
    public const val Phantoms: Int = 0x40

    /** `hkStringPtr` */
    public const val Name: Int = 0x50

    /** `hkUlong` */
    public const val UserData: Int = 0x58

    /** `bool` */
    public const val Active: Int = 0x60
}

/**
 * `hkpPhysicsSystemWithContacts`, 0x78 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsSystem` -> `hkpPhysicsSystemWithContacts`
 */
public object HkpPhysicsSystemWithContacts {
    public const val ByteSize: Int = 0x78

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val RigidBodies: Int = 0x10

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val Constraints: Int = 0x20

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val Actions: Int = 0x30

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val Phantoms: Int = 0x40

    /** `hkStringPtr`, from `hkpPhysicsSystem` */
    public const val Name: Int = 0x50

    /** `hkUlong`, from `hkpPhysicsSystem` */
    public const val UserData: Int = 0x58

    /** `bool`, from `hkpPhysicsSystem` */
    public const val Active: Int = 0x60

    /** `hkArray<ptr>` */
    public const val Contacts: Int = 0x68
}

/**
 * `hkpPlaneShape`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpHeightFieldShape` -> `hkpPlaneShape`
 */
public object HkpPlaneShape {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `hkVector4` */
    public const val Plane: Int = 0x20

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x30

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x40
}

/**
 * `hkpPointToPathConstraintData`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPointToPathConstraintData`
 */
public object HkpPointToPathConstraintData {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `struct*` */
    public const val Path: Int = 0x40

    /** `float` */
    public const val MaxFrictionForce: Int = 0x48

    /** `enum:int8` */
    public const val AngularConstrainedDOF: Int = 0x4c

    /** `hkTransform[2]` */
    public const val Transform_OS_KS: Int = 0x50
}

/**
 * `hkpPointToPlaneConstraintData`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPointToPlaneConstraintData`
 */
public object HkpPointToPlaneConstraintData {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpPointToPlaneConstraintData::Atoms`, 0xa0 bytes.
 */
public object HkpPointToPlaneConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Lin: Int = 0x90
}

/**
 * `hkpPositionConstraintMotor`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpPositionConstraintMotor`
 */
public object HkpPositionConstraintMotor {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x10

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0x18

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x1c

    /** `float` */
    public const val Tau: Int = 0x20

    /** `float` */
    public const val Damping: Int = 0x24

    /** `float` */
    public const val ProportionalRecoveryVelocity: Int = 0x28

    /** `float` */
    public const val ConstantRecoveryVelocity: Int = 0x2c
}

/**
 * `hkpPoweredChainData`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpPoweredChainData`
 */
public object HkpPoweredChainData {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `hkArray<struct>` */
    public const val Infos: Int = 0x40

    /** `float` */
    public const val Tau: Int = 0x50

    /** `float` */
    public const val Damping: Int = 0x54

    /** `float` */
    public const val CfmLinAdd: Int = 0x58

    /** `float` */
    public const val CfmLinMul: Int = 0x5c

    /** `float` */
    public const val CfmAngAdd: Int = 0x60

    /** `float` */
    public const val CfmAngMul: Int = 0x64

    /** `float` */
    public const val MaxErrorDistance: Int = 0x68
}

/**
 * `hkpPoweredChainData::ConstraintInfo`, 0x60 bytes.
 */
public object HkpPoweredChainDataConstraintInfo {
    public const val ByteSize: Int = 0x60

    /** `hkVector4` */
    public const val PivotInA: Int = 0x0

    /** `hkVector4` */
    public const val PivotInB: Int = 0x10

    /** `hkQuaternion` */
    public const val ATc: Int = 0x20

    /** `hkQuaternion` */
    public const val BTc: Int = 0x30

    /** `struct*[3]` */
    public const val Motors: Int = 0x40

    /** `bool` */
    public const val SwitchBodies: Int = 0x58
}

/**
 * `hkpPoweredChainMapper`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPoweredChainMapper`
 */
public object HkpPoweredChainMapper {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Links: Int = 0x10

    /** `hkArray<struct>` */
    public const val Targets: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Chains: Int = 0x30
}

/**
 * `hkpPoweredChainMapper::LinkInfo`, 0x10 bytes.
 */
public object HkpPoweredChainMapperLinkInfo {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val FirstTargetIdx: Int = 0x0

    /** `int32` */
    public const val NumTargets: Int = 0x4

    /** `struct*` */
    public const val LimitConstraint: Int = 0x8
}

/**
 * `hkpPoweredChainMapper::Target`, 0x10 bytes.
 */
public object HkpPoweredChainMapperTarget {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Chain: Int = 0x0

    /** `int32` */
    public const val InfoIndex: Int = 0x8
}

/**
 * `hkpPrismaticConstraintData`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPrismaticConstraintData`
 */
public object HkpPrismaticConstraintData {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpPrismaticConstraintData::Atoms`, 0x100 bytes.
 */
public object HkpPrismaticConstraintDataAtoms {
    public const val ByteSize: Int = 0x100

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Motor: Int = 0x90

    /** `struct` */
    public const val Friction: Int = 0xb0

    /** `struct` */
    public const val Ang: Int = 0xc0

    /** `struct` */
    public const val Lin0: Int = 0xd0

    /** `struct` */
    public const val Lin1: Int = 0xe0

    /** `struct` */
    public const val LinLimit: Int = 0xf0
}

/**
 * `hkpProjectileGun`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpProjectileGun`
 */
public object HkpProjectileGun {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x10

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Name: Int = 0x18

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x20

    /** `hkArray<void>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x28

    /** `int32` */
    public const val MaxProjectiles: Int = 0x38

    /** `float` */
    public const val ReloadTime: Int = 0x3c

    /** `float` */
    public const val Reload: Int = 0x40

    /** `hkArray<void>` */
    public const val Projectiles: Int = 0x48

    /** `void*` */
    public const val World: Int = 0x58

    /** `void*` */
    public const val DestructionWorld: Int = 0x60
}

/**
 * `hkpPulleyConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpPulleyConstraintAtom`
 */
public object HkpPulleyConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `hkVector4` */
    public const val FixedPivotAinWorld: Int = 0x10

    /** `hkVector4` */
    public const val FixedPivotBinWorld: Int = 0x20

    /** `float` */
    public const val RopeLength: Int = 0x30

    /** `float` */
    public const val LeverageOnBodyB: Int = 0x34
}

/**
 * `hkpPulleyConstraintData`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPulleyConstraintData`
 */
public object HkpPulleyConstraintData {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpPulleyConstraintData::Atoms`, 0x70 bytes.
 */
public object HkpPulleyConstraintDataAtoms {
    public const val ByteSize: Int = 0x70

    /** `struct` */
    public const val Translations: Int = 0x0

    /** `struct` */
    public const val Pulley: Int = 0x30
}

/**
 * `hkpRackAndPinionConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpRackAndPinionConstraintAtom`
 */
public object HkpRackAndPinionConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `float` */
    public const val PinionRadiusOrScrewPitch: Int = 0x4

    /** `bool` */
    public const val IsScrew: Int = 0x8

    /** `int8` */
    public const val MemOffsetToInitialAngleOffset: Int = 0x9

    /** `int8` */
    public const val MemOffsetToPrevAngle: Int = 0xa

    /** `int8` */
    public const val MemOffsetToRevolutionCounter: Int = 0xb
}

/**
 * `hkpRackAndPinionConstraintData`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRackAndPinionConstraintData`
 */
public object HkpRackAndPinionConstraintData {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpRackAndPinionConstraintData::Atoms`, 0xa0 bytes.
 */
public object HkpRackAndPinionConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val RackAndPinion: Int = 0x90
}

/**
 * `hkpRagdollConstraintData`, 0x1a0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRagdollConstraintData`
 */
public object HkpRagdollConstraintData {
    public const val ByteSize: Int = 0x1a0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpRagdollConstraintData::Atoms`, 0x180 bytes.
 */
public object HkpRagdollConstraintDataAtoms {
    public const val ByteSize: Int = 0x180

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x90

    /** `struct` */
    public const val RagdollMotors: Int = 0xa0

    /** `struct` */
    public const val AngFriction: Int = 0x100

    /** `struct` */
    public const val TwistLimit: Int = 0x110

    /** `struct` */
    public const val ConeLimit: Int = 0x130

    /** `struct` */
    public const val PlanesLimit: Int = 0x150

    /** `struct` */
    public const val BallSocket: Int = 0x170
}

/**
 * `hkpRagdollLimitsData`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRagdollLimitsData`
 */
public object HkpRagdollLimitsData {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpRagdollLimitsData::Atoms`, 0xd0 bytes.
 */
public object HkpRagdollLimitsDataAtoms {
    public const val ByteSize: Int = 0xd0

    /** `struct` */
    public const val Rotations: Int = 0x0

    /** `struct` */
    public const val TwistLimit: Int = 0x70

    /** `struct` */
    public const val ConeLimit: Int = 0x90

    /** `struct` */
    public const val PlanesLimit: Int = 0xb0
}

/**
 * `hkpRagdollMotorConstraintAtom`, 0x60 bytes.
 *
 * `hkpConstraintAtom` -> `hkpRagdollMotorConstraintAtom`
 */
public object HkpRagdollMotorConstraintAtom {
    public const val ByteSize: Int = 0x60

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `bool` */
    public const val IsEnabled: Int = 0x2

    /** `int16` */
    public const val InitializedOffset: Int = 0x4

    /** `int16` */
    public const val PreviousTargetAnglesOffset: Int = 0x6

    /** `hkMatrix3` */
    public const val Target_bRca: Int = 0x10

    /** `struct*[3]` */
    public const val Motors: Int = 0x40
}

/**
 * `hkpRayCollidableFilter`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpRayCollidableFilter {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpRayShapeCollectionFilter`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpRayShapeCollectionFilter {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpRemoveTerminalsMoppModifier`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpRemoveTerminalsMoppModifier`
 */
public object HkpRemoveTerminalsMoppModifier {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<uint32>` */
    public const val RemoveInfo: Int = 0x18

    /** `void*` */
    public const val TempShapesToRemove: Int = 0x28
}

/**
 * `hkpReorientAction`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpReorientAction`
 */
public object HkpReorientAction {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*`, from `hkpUnaryAction` */
    public const val Entity: Int = 0x30

    /** `hkVector4` */
    public const val RotationAxis: Int = 0x40

    /** `hkVector4` */
    public const val UpAxis: Int = 0x50

    /** `float` */
    public const val Strength: Int = 0x60

    /** `float` */
    public const val Damping: Int = 0x64
}

/**
 * `hkpRigidBody`, 0x2c0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpEntity` -> `hkpRigidBody`
 */
public object HkpRigidBody {
    public const val ByteSize: Int = 0x2c0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `struct`, from `hkpEntity` */
    public const val Material: Int = 0xc8

    /** `void*`, from `hkpEntity` */
    public const val LimitContactImpulseUtilAndFlag: Int = 0xd8

    /** `float`, from `hkpEntity` */
    public const val DamageMultiplier: Int = 0xe0

    /** `void*`, from `hkpEntity` */
    public const val BreakableBody: Int = 0xe8

    /** `uint32`, from `hkpEntity` */
    public const val SolverData: Int = 0xf0

    /** `uint16`, from `hkpEntity` */
    public const val StorageIndex: Int = 0xf4

    /** `uint16`, from `hkpEntity` */
    public const val ContactPointCallbackDelay: Int = 0xf6

    /** `struct`, from `hkpEntity` */
    public const val ConstraintsMaster: Int = 0xf8

    /** `hkArray<ptr>`, from `hkpEntity` */
    public const val ConstraintsSlave: Int = 0x108

    /** `hkArray<uint8>`, from `hkpEntity` */
    public const val ConstraintRuntime: Int = 0x118

    /** `void*`, from `hkpEntity` */
    public const val SimulationIsland: Int = 0x128

    /** `int8`, from `hkpEntity` */
    public const val AutoRemoveLevel: Int = 0x130

    /** `uint8`, from `hkpEntity` */
    public const val NumShapeKeysInContactPointProperties: Int = 0x131

    /** `uint8`, from `hkpEntity` */
    public const val ResponseModifierFlags: Int = 0x132

    /** `uint32`, from `hkpEntity` */
    public const val Uid: Int = 0x134

    /** `struct`, from `hkpEntity` */
    public const val SpuCollisionCallback: Int = 0x138

    /** `struct`, from `hkpEntity` */
    public const val Motion: Int = 0x150

    /** `struct`, from `hkpEntity` */
    public const val ContactListeners: Int = 0x290

    /** `struct`, from `hkpEntity` */
    public const val Actions: Int = 0x2a0

    /** `struct*`, from `hkpEntity` */
    public const val LocalFrame: Int = 0x2b0

    /** `struct*`, from `hkpEntity` */
    public const val ExtendedListeners: Int = 0x2b8
}

/**
 * `hkpRotationalConstraintData`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRotationalConstraintData`
 */
public object HkpRotationalConstraintData {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpRotationalConstraintData::Atoms`, 0x80 bytes.
 */
public object HkpRotationalConstraintDataAtoms {
    public const val ByteSize: Int = 0x80

    /** `struct` */
    public const val Rotations: Int = 0x0

    /** `struct` */
    public const val Ang: Int = 0x70
}

/**
 * `hkpSampledHeightFieldShape`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpHeightFieldShape` -> `hkpSampledHeightFieldShape`
 */
public object HkpSampledHeightFieldShape {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `hkArray<struct>` */
    public const val CoarseTreeData: Int = 0x20

    /** `int32` */
    public const val Coarseness: Int = 0x30

    /** `float` */
    public const val RaycastMinY: Int = 0x34

    /** `float` */
    public const val RaycastMaxY: Int = 0x38

    /** `int32` */
    public const val XRes: Int = 0x3c

    /** `int32` */
    public const val ZRes: Int = 0x40

    /** `float` */
    public const val HeightCenter: Int = 0x44

    /** `bool` */
    public const val UseProjectionBasedHeight: Int = 0x48

    /** `enum:uint8` */
    public const val HeightfieldType: Int = 0x49

    /** `hkVector4` */
    public const val IntToFloatScale: Int = 0x50

    /** `hkVector4` */
    public const val FloatToIntScale: Int = 0x60

    /** `hkVector4` */
    public const val FloatToIntOffsetFloorCorrected: Int = 0x70

    /** `hkVector4` */
    public const val Extents: Int = 0x80
}

/**
 * `hkpSampledHeightFieldShape::CoarseMinMaxLevel`, 0x18 bytes.
 */
public object HkpSampledHeightFieldShapeCoarseMinMaxLevel {
    public const val ByteSize: Int = 0x18

    /** `hkArray<hkVector4>` */
    public const val MinMaxData: Int = 0x0

    /** `int32` */
    public const val XRes: Int = 0x10

    /** `int32` */
    public const val ZRes: Int = 0x14
}

/**
 * `hkpSerializedAgentNnEntry`, 0x190 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedAgentNnEntry`
 */
public object HkpSerializedAgentNnEntry {
    public const val ByteSize: Int = 0x190

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val BodyA: Int = 0x10

    /** `struct*` */
    public const val BodyB: Int = 0x18

    /** `hkUlong` */
    public const val BodyAId: Int = 0x20

    /** `hkUlong` */
    public const val BodyBId: Int = 0x28

    /** `bool` */
    public const val UseEntityIds: Int = 0x30

    /** `enum:int8` */
    public const val AgentType: Int = 0x31

    /** `struct` */
    public const val Atom: Int = 0x40

    /** `hkArray<uint8>` */
    public const val PropertiesStream: Int = 0x70

    /** `hkArray<struct>` */
    public const val ContactPoints: Int = 0x80

    /** `hkArray<uint8>` */
    public const val CpIdMgr: Int = 0x90

    /** `uint8[192]` */
    public const val NnEntryData: Int = 0xa0

    /** `struct` */
    public const val TrackInfo: Int = 0x160

    /** `uint8[4]` */
    public const val EndianCheckBuffer: Int = 0x180

    /** `uint32` */
    public const val Version: Int = 0x184
}

/**
 * `hkpSerializedDisplayMarker`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayMarker`
 */
public object HkpSerializedDisplayMarker {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkTransform` */
    public const val Transform: Int = 0x10
}

/**
 * `hkpSerializedDisplayMarkerList`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayMarkerList`
 */
public object HkpSerializedDisplayMarkerList {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Markers: Int = 0x10
}

/**
 * `hkpSerializedDisplayRbTransforms`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayRbTransforms`
 */
public object HkpSerializedDisplayRbTransforms {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Transforms: Int = 0x10
}

/**
 * `hkpSerializedDisplayRbTransforms::DisplayTransformPair`, 0x50 bytes.
 */
public object HkpSerializedDisplayRbTransformsDisplayTransformPair {
    public const val ByteSize: Int = 0x50

    /** `struct*` */
    public const val Rb: Int = 0x0

    /** `hkTransform` */
    public const val LocalToDisplay: Int = 0x10
}

/**
 * `hkpSerializedSubTrack1nInfo`, 0x28 bytes.
 *
 * `hkpSerializedTrack1nInfo` -> `hkpSerializedSubTrack1nInfo`
 */
public object HkpSerializedSubTrack1nInfo {
    public const val ByteSize: Int = 0x28

    /** `hkArray<ptr>`, from `hkpSerializedTrack1nInfo` */
    public const val Sectors: Int = 0x0

    /** `hkArray<ptr>`, from `hkpSerializedTrack1nInfo` */
    public const val SubTracks: Int = 0x10

    /** `int32` */
    public const val SectorIndex: Int = 0x20

    /** `int32` */
    public const val OffsetInSector: Int = 0x24
}

/**
 * `hkpSerializedTrack1nInfo`, 0x20 bytes.
 */
public object HkpSerializedTrack1nInfo {
    public const val ByteSize: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Sectors: Int = 0x0

    /** `hkArray<ptr>` */
    public const val SubTracks: Int = 0x10
}

/**
 * `hkpSerializedTrack1nInfo::Agent1nSector`, 0x200 bytes.
 */
public object HkpSerializedTrack1nInfoAgent1nSector {
    public const val ByteSize: Int = 0x200

    /** `uint32` */
    public const val BytesAllocated: Int = 0x0

    /** `hkStringPtr` */
    public const val RawData: Int = 0x4
}

/**
 * `hkpSetLocalRotationsConstraintAtom`, 0x70 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetLocalRotationsConstraintAtom`
 */
public object HkpSetLocalRotationsConstraintAtom {
    public const val ByteSize: Int = 0x70

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `hkRotation` */
    public const val RotationA: Int = 0x10

    /** `hkRotation` */
    public const val RotationB: Int = 0x40
}

/**
 * `hkpSetLocalTransformsConstraintAtom`, 0x90 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetLocalTransformsConstraintAtom`
 */
public object HkpSetLocalTransformsConstraintAtom {
    public const val ByteSize: Int = 0x90

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `hkTransform` */
    public const val TransformA: Int = 0x10

    /** `hkTransform` */
    public const val TransformB: Int = 0x50
}

/**
 * `hkpSetLocalTranslationsConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetLocalTranslationsConstraintAtom`
 */
public object HkpSetLocalTranslationsConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `hkVector4` */
    public const val TranslationA: Int = 0x10

    /** `hkVector4` */
    public const val TranslationB: Int = 0x20
}

/**
 * `hkpSetupStabilizationAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetupStabilizationAtom`
 */
public object HkpSetupStabilizationAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `bool` */
    public const val Enabled: Int = 0x2

    /** `float` */
    public const val MaxLinImpulse: Int = 0x4

    /** `float` */
    public const val MaxAngImpulse: Int = 0x8

    /** `float` */
    public const val MaxAngle: Int = 0xc
}

/**
 * `hkpShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape`
 */
public object HkpShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong` */
    public const val UserData: Int = 0x18
}

/**
 * `hkpShapeBase`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase`
 */
public object HkpShapeBase {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13
}

/**
 * `hkpShapeCollection`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection`
 */
public object HkpShapeCollection {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8` */
    public const val CollectionType: Int = 0x29
}

/**
 * `hkpShapeCollectionFilter`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpShapeCollectionFilter {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpShapeContainer`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpShapeContainer {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpShapeInfo`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShapeInfo`
 */
public object HkpShapeInfo {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Shape: Int = 0x10

    /** `bool` */
    public const val IsHierarchicalCompound: Int = 0x18

    /** `bool` */
    public const val HkdShapesCollected: Int = 0x19

    /** `hkArray<hkStringPtr>` */
    public const val ChildShapeNames: Int = 0x20

    /** `hkArray<hkTransform>` */
    public const val ChildTransforms: Int = 0x30

    /** `hkTransform` */
    public const val Transform: Int = 0x40
}

/**
 * `hkpShapeKeyTable`, 0x10 bytes.
 */
public object HkpShapeKeyTable {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Lists: Int = 0x0

    /** `uint32` */
    public const val OccupancyBitField: Int = 0x8
}

/**
 * `hkpShapeKeyTable::Block`, 0x110 bytes.
 */
public object HkpShapeKeyTableBlock {
    public const val ByteSize: Int = 0x110

    /** `uint32[63]` */
    public const val Slots: Int = 0x0

    /** `struct*` */
    public const val Next: Int = 0x100
}

/**
 * `hkpShapeModifier`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpShapeModifier {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpShapePhantom`, 0x1a0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom`
 */
public object HkpShapePhantom {
    public const val ByteSize: Int = 0x1a0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0xc8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0xd8

    /** `struct` */
    public const val MotionState: Int = 0xf0
}

/**
 * `hkpSimpleBreakableMaterial`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMaterial` -> `hkpSimpleBreakableMaterial`
 */
public object HkpSimpleBreakableMaterial {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float`, from `hkpBreakableMaterial` */
    public const val Strength: Int = 0x10

    /** `int32`, from `hkpBreakableMaterial` */
    public const val TypeAndFlags: Int = 0x14

    /** `struct*`, from `hkpBreakableMaterial` */
    public const val Properties: Int = 0x18
}

/**
 * `hkpSimpleContactConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSimpleContactConstraintAtom`
 */
public object HkpSimpleContactConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16` */
    public const val SizeOfAllAtoms: Int = 0x2

    /** `uint16` */
    public const val NumContactPoints: Int = 0x4

    /** `uint16` */
    public const val NumReservedContactPoints: Int = 0x6

    /** `uint8` */
    public const val NumUserDatasForBodyA: Int = 0x8

    /** `uint8` */
    public const val NumUserDatasForBodyB: Int = 0x9

    /** `uint8` */
    public const val ContactPointPropertiesStriding: Int = 0xa

    /** `uint16` */
    public const val MaxNumContactPoints: Int = 0xc

    /** `struct` */
    public const val Info: Int = 0x10
}

/**
 * `hkpSimpleContactConstraintDataInfo`, 0x20 bytes.
 */
public object HkpSimpleContactConstraintDataInfo {
    public const val ByteSize: Int = 0x20

    /** `uint16` */
    public const val Flags: Int = 0x0

    /** `uint16` */
    public const val BiNormalAxis: Int = 0x2

    /** `hkHalf` */
    public const val RollingFrictionMultiplier: Int = 0x4

    /** `hkHalf` */
    public const val InternalData1: Int = 0x6

    /** `hkHalf[2]` */
    public const val RhsRolling: Int = 0x8

    /** `float` */
    public const val ContactRadius: Int = 0xc

    /** `float[4]` */
    public const val Data: Int = 0x10
}

/**
 * `hkpSimpleMeshShape`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpSimpleMeshShape`
 */
public object HkpSimpleMeshShape {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `hkArray<hkVector4>` */
    public const val Vertices: Int = 0x30

    /** `hkArray<struct>` */
    public const val Triangles: Int = 0x40

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x50

    /** `float` */
    public const val Radius: Int = 0x60

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x64
}

/**
 * `hkpSimpleMeshShape::Triangle`, 0x10 bytes.
 */
public object HkpSimpleMeshShapeTriangle {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val A: Int = 0x0

    /** `int32` */
    public const val B: Int = 0x4

    /** `int32` */
    public const val C: Int = 0x8

    /** `uint16` */
    public const val WeldingInfo: Int = 0xc
}

/**
 * `hkpSimpleShapePhantom`, 0x1c0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom` -> `hkpSimpleShapePhantom`
 */
public object HkpSimpleShapePhantom {
    public const val ByteSize: Int = 0x1c0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x10

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0x18

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x20

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0xb8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0xc8

    /** `hkArray<void>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0xd8

    /** `struct`, from `hkpShapePhantom` */
    public const val MotionState: Int = 0xf0

    /** `hkArray<struct>` */
    public const val CollisionDetails: Int = 0x1a0

    /** `bool` */
    public const val OrderDirty: Int = 0x1b0
}

/**
 * `hkpSimpleShapePhantom::CollisionDetail`, 0x8 bytes.
 */
public object HkpSimpleShapePhantomCollisionDetail {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val Collidable: Int = 0x0
}

/**
 * `hkpSimulation`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimulation`
 */
public object HkpSimulation {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32` */
    public const val DeterminismCheckFrameCounter: Int = 0x10

    /** `struct*` */
    public const val World: Int = 0x18

    /** `enum:uint8` */
    public const val LastProcessingStep: Int = 0x20

    /** `float` */
    public const val CurrentTime: Int = 0x24

    /** `float` */
    public const val CurrentPsiTime: Int = 0x28

    /** `float` */
    public const val PhysicsDeltaTime: Int = 0x2c

    /** `float` */
    public const val SimulateUntilTime: Int = 0x30

    /** `float` */
    public const val FrameMarkerPsiSnap: Int = 0x34

    /** `uint32` */
    public const val PreviousStepResult: Int = 0x38
}

/**
 * `hkpSingleShapeContainer`, 0x10 bytes.
 *
 * `hkpShapeContainer` -> `hkpSingleShapeContainer`
 */
public object HkpSingleShapeContainer {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val ChildShape: Int = 0x8
}

/**
 * `hkpSoftContactModifierConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpSoftContactModifierConstraintAtom`
 */
public object HkpSoftContactModifierConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x18

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x20

    /** `float` */
    public const val Tau: Int = 0x30

    /** `float` */
    public const val MaxAcceleration: Int = 0x34
}

/**
 * `hkpSphereMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpSphereMotion`
 */
public object HkpSphereMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpSphereRepShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape`
 */
public object HkpSphereRepShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18
}

/**
 * `hkpSphereShape`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpSphereShape`
 */
public object HkpSphereShape {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `uint32[3]` */
    public const val Pad16: Int = 0x28
}

/**
 * `hkpSpringAction`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpSpringAction`
 */
public object HkpSpringAction {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityA: Int = 0x30

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityB: Int = 0x38

    /** `hkVector4` */
    public const val LastForce: Int = 0x40

    /** `hkVector4` */
    public const val PositionAinA: Int = 0x50

    /** `hkVector4` */
    public const val PositionBinB: Int = 0x60

    /** `float` */
    public const val RestLength: Int = 0x70

    /** `float` */
    public const val Strength: Int = 0x74

    /** `float` */
    public const val Damping: Int = 0x78

    /** `bool` */
    public const val OnCompression: Int = 0x7c

    /** `bool` */
    public const val OnExtension: Int = 0x7d
}

/**
 * `hkpSpringDamperConstraintMotor`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpSpringDamperConstraintMotor`
 */
public object HkpSpringDamperConstraintMotor {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x10

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0x18

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x1c

    /** `float` */
    public const val SpringConstant: Int = 0x20

    /** `float` */
    public const val SpringDamping: Int = 0x24
}

/**
 * `hkpStaticCompoundShape`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvTreeShape` -> `hkpStaticCompoundShape`
 */
public object HkpStaticCompoundShape {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val BvTreeType: Int = 0x20

    /** `int8` */
    public const val NumBitsForChildShapeKey: Int = 0x30

    /** `int8` */
    public const val ReferencePolicy: Int = 0x31

    /** `uint32` */
    public const val ChildShapeKeyMask: Int = 0x34

    /** `hkArray<struct>` */
    public const val Instances: Int = 0x38

    /** `hkArray<uint16>` */
    public const val InstanceExtraInfos: Int = 0x48

    /** `struct` */
    public const val DisabledLargeShapeKeyTable: Int = 0x58

    /** `struct` */
    public const val Tree: Int = 0x70
}

/**
 * `hkpStaticCompoundShape::Instance`, 0x50 bytes.
 */
public object HkpStaticCompoundShapeInstance {
    public const val ByteSize: Int = 0x50

    /** `hkQsTransform` */
    public const val Transform: Int = 0x0

    /** `struct*` */
    public const val Shape: Int = 0x30

    /** `uint32` */
    public const val FilterInfo: Int = 0x38

    /** `uint32` */
    public const val ChildFilterInfoMask: Int = 0x3c

    /** `hkUlong` */
    public const val UserData: Int = 0x40
}

/**
 * `hkpStaticCompoundShapeBreakableMaterial`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableMaterial` -> `hkpBreakableMultiMaterial` -> `hkpStaticCompoundShapeBreakableMaterial`
 */
public object HkpStaticCompoundShapeBreakableMaterial {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float`, from `hkpBreakableMaterial` */
    public const val Strength: Int = 0x10

    /** `int32`, from `hkpBreakableMaterial` */
    public const val TypeAndFlags: Int = 0x14

    /** `struct*`, from `hkpBreakableMaterial` */
    public const val Properties: Int = 0x18

    /** `hkArray<ptr>`, from `hkpBreakableMultiMaterial` */
    public const val SubMaterials: Int = 0x20

    /** `struct*`, from `hkpBreakableMultiMaterial` */
    public const val InverseMapping: Int = 0x30
}

/**
 * `hkpStiffSpringChainData`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpStiffSpringChainData`
 */
public object HkpStiffSpringChainData {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `hkArray<struct>` */
    public const val Infos: Int = 0x40

    /** `float` */
    public const val Tau: Int = 0x50

    /** `float` */
    public const val Damping: Int = 0x54

    /** `float` */
    public const val Cfm: Int = 0x58
}

/**
 * `hkpStiffSpringChainData::ConstraintInfo`, 0x30 bytes.
 */
public object HkpStiffSpringChainDataConstraintInfo {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val PivotInA: Int = 0x0

    /** `hkVector4` */
    public const val PivotInB: Int = 0x10

    /** `float` */
    public const val SpringLength: Int = 0x20
}

/**
 * `hkpStiffSpringConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpStiffSpringConstraintAtom`
 */
public object HkpStiffSpringConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `float` */
    public const val Length: Int = 0x10

    /** `float` */
    public const val MaxLength: Int = 0x14

    /** `float` */
    public const val SpringConstant: Int = 0x18

    /** `float` */
    public const val SpringDamping: Int = 0x1c
}

/**
 * `hkpStiffSpringConstraintData`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpStiffSpringConstraintData`
 */
public object HkpStiffSpringConstraintData {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpStiffSpringConstraintData::Atoms`, 0x60 bytes.
 */
public object HkpStiffSpringConstraintDataAtoms {
    public const val ByteSize: Int = 0x60

    /** `struct` */
    public const val Pivots: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x30

    /** `struct` */
    public const val Spring: Int = 0x40
}

/**
 * `hkpStorageExtendedMeshShape`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape` -> `hkpStorageExtendedMeshShape`
 */
public object HkpStorageExtendedMeshShape {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `struct`, from `hkpExtendedMeshShape` */
    public const val EmbeddedTrianglesSubpart: Int = 0x30

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val AabbHalfExtents: Int = 0xc0

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val AabbCenter: Int = 0xd0

    /** `void*`, from `hkpExtendedMeshShape` */
    public const val MaterialClass: Int = 0xe0

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0xe8

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val TrianglesSubparts: Int = 0xf0

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val ShapesSubparts: Int = 0x100

    /** `hkArray<uint16>`, from `hkpExtendedMeshShape` */
    public const val WeldingInfo: Int = 0x110

    /** `enum:uint8`, from `hkpExtendedMeshShape` */
    public const val WeldingType: Int = 0x120

    /** `uint32`, from `hkpExtendedMeshShape` */
    public const val DefaultCollisionFilterInfo: Int = 0x124

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val CachedNumChildShapes: Int = 0x128

    /** `float`, from `hkpExtendedMeshShape` */
    public const val TriangleRadius: Int = 0x12c

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val Padding: Int = 0x130

    /** `hkArray<ptr>` */
    public const val MeshStorage: Int = 0x140

    /** `hkArray<ptr>` */
    public const val ShapeStorage: Int = 0x150
}

/**
 * `hkpStorageExtendedMeshShape::Material`, 0x10 bytes.
 *
 * `hkpMeshMaterial` -> `hkpStorageExtendedMeshShape::Material`
 */
public object HkpStorageExtendedMeshShapeMaterial {
    public const val ByteSize: Int = 0x10

    /** `uint32`, from `hkpMeshMaterial` */
    public const val FilterInfo: Int = 0x0

    /** `hkHalf` */
    public const val Restitution: Int = 0x4

    /** `hkHalf` */
    public const val Friction: Int = 0x6

    /** `hkUlong` */
    public const val UserData: Int = 0x8
}

/**
 * `hkpStorageExtendedMeshShape::MeshSubpartStorage`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageExtendedMeshShape::MeshSubpartStorage`
 */
public object HkpStorageExtendedMeshShapeMeshSubpartStorage {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<hkVector4>` */
    public const val Vertices: Int = 0x10

    /** `hkArray<uint8>` */
    public const val Indices8: Int = 0x20

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x30

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x40

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x50

    /** `hkArray<struct>` */
    public const val Materials: Int = 0x60

    /** `hkArray<struct>` */
    public const val NamedMaterials: Int = 0x70

    /** `hkArray<uint16>` */
    public const val MaterialIndices16: Int = 0x80
}

/**
 * `hkpStorageExtendedMeshShape::ShapeSubpartStorage`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageExtendedMeshShape::ShapeSubpartStorage`
 */
public object HkpStorageExtendedMeshShapeShapeSubpartStorage {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x10

    /** `hkArray<struct>` */
    public const val Materials: Int = 0x20

    /** `hkArray<uint16>` */
    public const val MaterialIndices16: Int = 0x30
}

/**
 * `hkpStorageMeshShape`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpStorageMeshShape`
 */
public object HkpStorageMeshShape {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `hkVector4`, from `hkpMeshShape` */
    public const val Scaling: Int = 0x30

    /** `int32`, from `hkpMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0x40

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val Subparts: Int = 0x48

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val WeldingInfo: Int = 0x58

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val WeldingType: Int = 0x68

    /** `float`, from `hkpMeshShape` */
    public const val Radius: Int = 0x6c

    /** `int32[3]`, from `hkpMeshShape` */
    public const val Pad: Int = 0x70

    /** `hkArray<ptr>` */
    public const val Storage: Int = 0x80
}

/**
 * `hkpStorageMeshShape::SubpartStorage`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageMeshShape::SubpartStorage`
 */
public object HkpStorageMeshShapeSubpartStorage {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<float>` */
    public const val Vertices: Int = 0x10

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x20

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x30

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x40

    /** `hkArray<uint32>` */
    public const val Materials: Int = 0x50

    /** `hkArray<uint16>` */
    public const val MaterialIndices16: Int = 0x60
}

/**
 * `hkpStorageSampledHeightFieldShape`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpHeightFieldShape` -> `hkpSampledHeightFieldShape` -> `hkpStorageSampledHeightFieldShape`
 */
public object HkpStorageSampledHeightFieldShape {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `hkArray<struct>`, from `hkpSampledHeightFieldShape` */
    public const val CoarseTreeData: Int = 0x20

    /** `int32`, from `hkpSampledHeightFieldShape` */
    public const val Coarseness: Int = 0x30

    /** `float`, from `hkpSampledHeightFieldShape` */
    public const val RaycastMinY: Int = 0x34

    /** `float`, from `hkpSampledHeightFieldShape` */
    public const val RaycastMaxY: Int = 0x38

    /** `int32`, from `hkpSampledHeightFieldShape` */
    public const val XRes: Int = 0x3c

    /** `int32`, from `hkpSampledHeightFieldShape` */
    public const val ZRes: Int = 0x40

    /** `float`, from `hkpSampledHeightFieldShape` */
    public const val HeightCenter: Int = 0x44

    /** `bool`, from `hkpSampledHeightFieldShape` */
    public const val UseProjectionBasedHeight: Int = 0x48

    /** `enum:uint8`, from `hkpSampledHeightFieldShape` */
    public const val HeightfieldType: Int = 0x49

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val IntToFloatScale: Int = 0x50

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val FloatToIntScale: Int = 0x60

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val FloatToIntOffsetFloorCorrected: Int = 0x70

    /** `hkVector4`, from `hkpSampledHeightFieldShape` */
    public const val Extents: Int = 0x80

    /** `hkArray<float>` */
    public const val Storage: Int = 0x90

    /** `bool` */
    public const val TriangleFlip: Int = 0xa0
}

/**
 * `hkpThinBoxMotion`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpBoxMotion` -> `hkpThinBoxMotion`
 */
public object HkpThinBoxMotion {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x10

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x11

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0x12

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x20

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xe0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xf0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0x100

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x120

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x128

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x130

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x132
}

/**
 * `hkpTransformShape`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpTransformShape`
 */
public object HkpTransformShape {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `struct` */
    public const val ChildShape: Int = 0x20

    /** `int32` */
    public const val ChildShapeSize: Int = 0x30

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x40

    /** `hkTransform` */
    public const val Transform: Int = 0x50
}

/**
 * `hkpTriSampledHeightFieldBvTreeShape`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvTreeShape` -> `hkpTriSampledHeightFieldBvTreeShape`
 */
public object HkpTriSampledHeightFieldBvTreeShape {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val BvTreeType: Int = 0x20

    /** `struct` */
    public const val ChildContainer: Int = 0x28

    /** `int32` */
    public const val ChildSize: Int = 0x38

    /** `bool` */
    public const val WantAabbRejectionTest: Int = 0x3c

    /** `uint8[12]` */
    public const val Padding: Int = 0x3d
}

/**
 * `hkpTriSampledHeightFieldCollection`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpTriSampledHeightFieldCollection`
 */
public object HkpTriSampledHeightFieldCollection {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `struct*` */
    public const val Heightfield: Int = 0x30

    /** `int32` */
    public const val ChildSize: Int = 0x38

    /** `float` */
    public const val Radius: Int = 0x3c

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0x40

    /** `hkVector4` */
    public const val TriangleExtrusion: Int = 0x50
}

/**
 * `hkpTriangleShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpTriangleShape`
 */
public object HkpTriangleShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x20

    /** `uint16` */
    public const val WeldingInfo: Int = 0x28

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x2a

    /** `uint8` */
    public const val IsExtruded: Int = 0x2b

    /** `hkVector4` */
    public const val VertexA: Int = 0x30

    /** `hkVector4` */
    public const val VertexB: Int = 0x40

    /** `hkVector4` */
    public const val VertexC: Int = 0x50

    /** `hkVector4` */
    public const val Extrusion: Int = 0x60
}

/**
 * `hkpTriggerVolume`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpTriggerVolume`
 */
public object HkpTriggerVolume {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val OverlappingBodies: Int = 0x28

    /** `hkArray<struct>` */
    public const val EventQueue: Int = 0x38

    /** `struct*` */
    public const val TriggerBody: Int = 0x48

    /** `uint32` */
    public const val SequenceNumber: Int = 0x50

    /** `bool` */
    public const val IsProcessingBodyOverlaps: Int = 0x54

    /** `hkArray<void>` */
    public const val NewOverlappingBodies: Int = 0x58
}

/**
 * `hkpTriggerVolume::EventInfo`, 0x18 bytes.
 */
public object HkpTriggerVolumeEventInfo {
    public const val ByteSize: Int = 0x18

    /** `int64` */
    public const val SortValue: Int = 0x0

    /** `struct*` */
    public const val Body: Int = 0x8

    /** `enum:int32` */
    public const val Operation: Int = 0x10
}

/**
 * `hkpTwistLimitConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpTwistLimitConstraintAtom`
 */
public object HkpTwistLimitConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val TwistAxis: Int = 0x3

    /** `uint8` */
    public const val RefAxis: Int = 0x4

    /** `float` */
    public const val MinAngle: Int = 0x8

    /** `float` */
    public const val MaxAngle: Int = 0xc

    /** `float` */
    public const val AngularLimitsTauFactor: Int = 0x10

    /** `float` */
    public const val AngularLimitsDampFactor: Int = 0x14
}

/**
 * `hkpTypedBroadPhaseHandle`, 0xc bytes.
 *
 * `hkpBroadPhaseHandle` -> `hkpTypedBroadPhaseHandle`
 */
public object HkpTypedBroadPhaseHandle {
    public const val ByteSize: Int = 0xc

    /** `uint32`, from `hkpBroadPhaseHandle` */
    public const val Id: Int = 0x0

    /** `int8` */
    public const val Type: Int = 0x4

    /** `int8` */
    public const val OwnerOffset: Int = 0x5

    /** `int8` */
    public const val ObjectQualityType: Int = 0x6

    /** `uint32` */
    public const val CollisionFilterInfo: Int = 0x8
}

/**
 * `hkpUnaryAction`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction`
 */
public object HkpUnaryAction {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x10

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0x18

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x20

    /** `hkStringPtr`, from `hkpAction` */
    public const val Name: Int = 0x28

    /** `struct*` */
    public const val Entity: Int = 0x30
}

/**
 * `hkpVehicleFrictionDescription`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpVehicleFrictionDescription`
 */
public object HkpVehicleFrictionDescription {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val WheelDistance: Int = 0x10

    /** `float` */
    public const val ChassisMassInv: Int = 0x14

    /** `struct[2]` */
    public const val AxleDescr: Int = 0x18
}

/**
 * `hkpVehicleFrictionDescription::AxisDescription`, 0x64 bytes.
 */
public object HkpVehicleFrictionDescriptionAxisDescription {
    public const val ByteSize: Int = 0x64

    /** `float[16]` */
    public const val FrictionCircleYtab: Int = 0x0

    /** `float` */
    public const val XStep: Int = 0x40

    /** `float` */
    public const val XStart: Int = 0x44

    /** `float` */
    public const val WheelSurfaceInertia: Int = 0x48

    /** `float` */
    public const val WheelSurfaceInertiaInv: Int = 0x4c

    /** `float` */
    public const val WheelChassisMassRatio: Int = 0x50

    /** `float` */
    public const val WheelRadius: Int = 0x54

    /** `float` */
    public const val WheelRadiusInv: Int = 0x58

    /** `float` */
    public const val WheelDownForceFactor: Int = 0x5c

    /** `float` */
    public const val WheelDownForceSumFactor: Int = 0x60
}

/**
 * `hkpVehicleFrictionStatus`, 0x48 bytes.
 */
public object HkpVehicleFrictionStatus {
    public const val ByteSize: Int = 0x48

    /** `struct[2]` */
    public const val Axis: Int = 0x0
}

/**
 * `hkpVehicleFrictionStatus::AxisStatus`, 0x24 bytes.
 */
public object HkpVehicleFrictionStatusAxisStatus {
    public const val ByteSize: Int = 0x24

    /** `float` */
    public const val Forward_slip_velocity: Int = 0x0

    /** `float` */
    public const val Side_slip_velocity: Int = 0x4

    /** `float` */
    public const val Skid_energy_density: Int = 0x8

    /** `float` */
    public const val Side_force: Int = 0xc

    /** `float` */
    public const val Delayed_forward_impulse: Int = 0x10

    /** `float` */
    public const val SideRhs: Int = 0x14

    /** `float` */
    public const val ForwardRhs: Int = 0x18

    /** `float` */
    public const val RelativeSideForce: Int = 0x1c

    /** `float` */
    public const val RelativeForwardForce: Int = 0x20
}

/**
 * `hkpVelocityConstraintMotor`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpVelocityConstraintMotor`
 */
public object HkpVelocityConstraintMotor {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x10

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0x18

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x1c

    /** `float` */
    public const val Tau: Int = 0x20

    /** `float` */
    public const val VelocityTarget: Int = 0x24

    /** `bool` */
    public const val UseVelocityTargetFromConstraintTargets: Int = 0x28
}

/**
 * `hkpViscousSurfaceModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpViscousSurfaceModifierConstraintAtom`
 */
public object HkpViscousSurfaceModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x18

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x20
}

/**
 * `hkpWeldingUtility`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpWeldingUtility {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkpWheelConstraintData`, 0x190 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpWheelConstraintData`
 */
public object HkpWheelConstraintData {
    public const val ByteSize: Int = 0x190

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20

    /** `hkVector4` */
    public const val InitialAxleInB: Int = 0x170

    /** `hkVector4` */
    public const val InitialSteeringAxisInB: Int = 0x180
}

/**
 * `hkpWheelConstraintData::Atoms`, 0x150 bytes.
 */
public object HkpWheelConstraintDataAtoms {
    public const val ByteSize: Int = 0x150

    /** `struct` */
    public const val SuspensionBase: Int = 0x0

    /** `struct` */
    public const val Lin0Limit: Int = 0x90

    /** `struct` */
    public const val Lin0Soft: Int = 0xa0

    /** `struct` */
    public const val Lin1: Int = 0xb0

    /** `struct` */
    public const val Lin2: Int = 0xc0

    /** `struct` */
    public const val SteeringBase: Int = 0xd0

    /** `struct` */
    public const val `2dAng`: Int = 0x140
}

/**
 * `hkpWheelFrictionConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpWheelFrictionConstraintAtom`
 */
public object HkpWheelFrictionConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val ForwardAxis: Int = 0x3

    /** `uint8` */
    public const val SideAxis: Int = 0x4

    /** `float` */
    public const val Radius: Int = 0x8

    /** `struct*` */
    public const val Axle: Int = 0x10

    /** `float` */
    public const val MaxFrictionForce: Int = 0x18

    /** `float` */
    public const val Torque: Int = 0x1c

    /** `float[2]` */
    public const val FrictionImpulse: Int = 0x20

    /** `float[2]` */
    public const val SlipImpulse: Int = 0x28
}

/**
 * `hkpWheelFrictionConstraintAtom::Axle`, 0x2c bytes.
 */
public object HkpWheelFrictionConstraintAtomAxle {
    public const val ByteSize: Int = 0x2c

    /** `float` */
    public const val SpinVelocity: Int = 0x0

    /** `float` */
    public const val SumVelocity: Int = 0x4

    /** `int32` */
    public const val NumWheels: Int = 0x8

    /** `int32` */
    public const val WheelsSolved: Int = 0xc

    /** `int32` */
    public const val StepsSolved: Int = 0x10

    /** `float` */
    public const val InvInertia: Int = 0x14

    /** `float` */
    public const val Inertia: Int = 0x18

    /** `float` */
    public const val ImpulseScaling: Int = 0x1c

    /** `float` */
    public const val ImpulseMax: Int = 0x20

    /** `bool` */
    public const val IsFixed: Int = 0x24

    /** `int32` */
    public const val NumWheelsOnGround: Int = 0x28
}

/**
 * `hkpWheelFrictionConstraintData`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpWheelFrictionConstraintData`
 */
public object HkpWheelFrictionConstraintData {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct` */
    public const val Atoms: Int = 0x20
}

/**
 * `hkpWheelFrictionConstraintData::Atoms`, 0xc0 bytes.
 */
public object HkpWheelFrictionConstraintDataAtoms {
    public const val ByteSize: Int = 0xc0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Friction: Int = 0x90
}

/**
 * `hkpWheelFrictionConstraintData::Runtime`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpWheelFrictionConstraintDataRuntime {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkpWorld`, 0x440 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorld`
 */
public object HkpWorld {
    public const val ByteSize: Int = 0x440

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Simulation: Int = 0x10

    /** `hkVector4` */
    public const val Gravity: Int = 0x20

    /** `void*` */
    public const val FixedIsland: Int = 0x30

    /** `struct*` */
    public const val FixedRigidBody: Int = 0x38

    /** `hkArray<void>` */
    public const val ActiveSimulationIslands: Int = 0x40

    /** `hkArray<void>` */
    public const val InactiveSimulationIslands: Int = 0x50

    /** `hkArray<void>` */
    public const val DirtySimulationIslands: Int = 0x60

    /** `void*` */
    public const val MaintenanceMgr: Int = 0x70

    /** `void*` */
    public const val MemoryWatchDog: Int = 0x78

    /** `bool` */
    public const val AssertOnRunningOutOfSolverMemory: Int = 0x80

    /** `enum:int8` */
    public const val BroadPhaseType: Int = 0x81

    /** `void*` */
    public const val BroadPhase: Int = 0x88

    /** `void*` */
    public const val BroadPhaseDispatcher: Int = 0x90

    /** `void*` */
    public const val PhantomBroadPhaseListener: Int = 0x98

    /** `void*` */
    public const val EntityEntityBroadPhaseListener: Int = 0xa0

    /** `void*` */
    public const val BroadPhaseBorderListener: Int = 0xa8

    /** `void*` */
    public const val MultithreadedSimulationJobData: Int = 0xb0

    /** `void*` */
    public const val CollisionInput: Int = 0xb8

    /** `void*` */
    public const val CollisionFilter: Int = 0xc0

    /** `void*` */
    public const val CollisionDispatcher: Int = 0xc8

    /** `void*` */
    public const val ConvexListFilter: Int = 0xd0

    /** `void*` */
    public const val PendingOperations: Int = 0xd8

    /** `int32` */
    public const val PendingOperationsCount: Int = 0xe0

    /** `int32` */
    public const val PendingBodyOperationsCount: Int = 0xe4

    /** `int32` */
    public const val CriticalOperationsLockCount: Int = 0xe8

    /** `int32` */
    public const val CriticalOperationsLockCountForPhantoms: Int = 0xec

    /** `bool` */
    public const val BlockExecutingPendingOperations: Int = 0xf0

    /** `bool` */
    public const val CriticalOperationsAllowed: Int = 0xf1

    /** `void*` */
    public const val PendingOperationQueues: Int = 0xf8

    /** `int32` */
    public const val PendingOperationQueueCount: Int = 0x100

    /** `struct` */
    public const val MultiThreadCheck: Int = 0x104

    /** `bool` */
    public const val ProcessActionsInSingleThread: Int = 0x110

    /** `bool` */
    public const val AllowIntegrationOfIslandsWithoutConstraintsInASeparateJob: Int = 0x111

    /** `uint32` */
    public const val MinDesiredIslandSize: Int = 0x114

    /** `void*` */
    public const val ModifyConstraintCriticalSection: Int = 0x118

    /** `int32` */
    public const val IsLocked: Int = 0x120

    /** `void*` */
    public const val IslandDirtyListCriticalSection: Int = 0x128

    /** `void*` */
    public const val PropertyMasterLock: Int = 0x130

    /** `bool` */
    public const val WantSimulationIslands: Int = 0x138

    /** `float` */
    public const val SnapCollisionToConvexEdgeThreshold: Int = 0x13c

    /** `float` */
    public const val SnapCollisionToConcaveEdgeThreshold: Int = 0x140

    /** `bool` */
    public const val EnableToiWeldRejection: Int = 0x144

    /** `bool` */
    public const val WantDeactivation: Int = 0x145

    /** `bool` */
    public const val ShouldActivateOnRigidBodyTransformChange: Int = 0x146

    /** `float` */
    public const val DeactivationReferenceDistance: Int = 0x148

    /** `float` */
    public const val ToiCollisionResponseRotateNormal: Int = 0x14c

    /** `int32` */
    public const val MaxSectorsPerMidphaseCollideTask: Int = 0x150

    /** `int32` */
    public const val MaxSectorsPerNarrowphaseCollideTask: Int = 0x154

    /** `bool` */
    public const val ProcessToisMultithreaded: Int = 0x158

    /** `int32` */
    public const val MaxEntriesPerToiMidphaseCollideTask: Int = 0x15c

    /** `int32` */
    public const val MaxEntriesPerToiNarrowphaseCollideTask: Int = 0x160

    /** `int32` */
    public const val MaxNumToiCollisionPairsSinglethreaded: Int = 0x164

    /** `enum:int32` */
    public const val SimulationType: Int = 0x168

    /** `float` */
    public const val NumToisTillAllowedPenetrationSimplifiedToi: Int = 0x16c

    /** `float` */
    public const val NumToisTillAllowedPenetrationToi: Int = 0x170

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiHigher: Int = 0x174

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiForced: Int = 0x178

    /** `uint32` */
    public const val LastEntityUid: Int = 0x17c

    /** `uint32` */
    public const val LastIslandUid: Int = 0x180

    /** `uint32` */
    public const val LastConstraintUid: Int = 0x184

    /** `hkArray<ptr>` */
    public const val Phantoms: Int = 0x188

    /** `hkArray<void>` */
    public const val ActionListeners: Int = 0x198

    /** `hkArray<void>` */
    public const val EntityListeners: Int = 0x1a8

    /** `hkArray<void>` */
    public const val PhantomListeners: Int = 0x1b8

    /** `hkArray<void>` */
    public const val ConstraintListeners: Int = 0x1c8

    /** `hkArray<void>` */
    public const val WorldDeletionListeners: Int = 0x1d8

    /** `hkArray<void>` */
    public const val IslandActivationListeners: Int = 0x1e8

    /** `hkArray<void>` */
    public const val WorldPostSimulationListeners: Int = 0x1f8

    /** `hkArray<void>` */
    public const val WorldPostIntegrateListeners: Int = 0x208

    /** `hkArray<void>` */
    public const val WorldPostCollideListeners: Int = 0x218

    /** `hkArray<void>` */
    public const val IslandPostIntegrateListeners: Int = 0x228

    /** `hkArray<void>` */
    public const val IslandPostCollideListeners: Int = 0x238

    /** `hkArray<void>` */
    public const val ContactListeners: Int = 0x248

    /** `hkArray<void>` */
    public const val ContactImpulseLimitBreachedListeners: Int = 0x258

    /** `hkArray<void>` */
    public const val WorldExtensions: Int = 0x268

    /** `void*` */
    public const val ViolatedConstraintArray: Int = 0x278

    /** `void*` */
    public const val BroadPhaseBorder: Int = 0x280

    /** `void*` */
    public const val DestructionWorld: Int = 0x288

    /** `void*` */
    public const val NpWorld: Int = 0x290

    /** `hkVector4[2]` */
    public const val BroadPhaseExtents: Int = 0x400

    /** `int32` */
    public const val BroadPhaseNumMarkers: Int = 0x420

    /** `int32` */
    public const val SizeOfToiEventQueue: Int = 0x424

    /** `int32` */
    public const val BroadPhaseQuerySize: Int = 0x428

    /** `int32` */
    public const val BroadPhaseUpdateSize: Int = 0x42c

    /** `enum:int8` */
    public const val ContactPointGeneration: Int = 0x430

    /** `bool` */
    public const val UseCompoundSpuElf: Int = 0x431
}

/**
 * `hkpWorldCinfo`, 0x100 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldCinfo`
 */
public object HkpWorldCinfo {
    public const val ByteSize: Int = 0x100

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkVector4` */
    public const val Gravity: Int = 0x10

    /** `int32` */
    public const val BroadPhaseQuerySize: Int = 0x20

    /** `float` */
    public const val ContactRestingVelocity: Int = 0x24

    /** `enum:int8` */
    public const val BroadPhaseType: Int = 0x28

    /** `enum:int8` */
    public const val BroadPhaseBorderBehaviour: Int = 0x29

    /** `bool` */
    public const val MtPostponeAndSortBroadPhaseBorderCallbacks: Int = 0x2a

    /** `struct` */
    public const val BroadPhaseWorldAabb: Int = 0x30

    /** `float` */
    public const val CollisionTolerance: Int = 0x50

    /** `struct*` */
    public const val CollisionFilter: Int = 0x58

    /** `struct*` */
    public const val ConvexListFilter: Int = 0x60

    /** `float` */
    public const val ExpectedMaxLinearVelocity: Int = 0x68

    /** `int32` */
    public const val SizeOfToiEventQueue: Int = 0x6c

    /** `float` */
    public const val ExpectedMinPsiDeltaTime: Int = 0x70

    /** `struct*` */
    public const val MemoryWatchDog: Int = 0x78

    /** `int32` */
    public const val BroadPhaseNumMarkers: Int = 0x80

    /** `enum:int8` */
    public const val ContactPointGeneration: Int = 0x84

    /** `bool` */
    public const val AllowToSkipConfirmedCallbacks: Int = 0x85

    /** `float` */
    public const val SolverTau: Int = 0x88

    /** `float` */
    public const val SolverDamp: Int = 0x8c

    /** `int32` */
    public const val SolverIterations: Int = 0x90

    /** `int32` */
    public const val SolverMicrosteps: Int = 0x94

    /** `float` */
    public const val MaxConstraintViolation: Int = 0x98

    /** `bool` */
    public const val ForceCoherentConstraintOrderingInSolver: Int = 0x9c

    /** `float` */
    public const val SnapCollisionToConvexEdgeThreshold: Int = 0xa0

    /** `float` */
    public const val SnapCollisionToConcaveEdgeThreshold: Int = 0xa4

    /** `bool` */
    public const val EnableToiWeldRejection: Int = 0xa8

    /** `bool` */
    public const val EnableDeprecatedWelding: Int = 0xa9

    /** `float` */
    public const val IterativeLinearCastEarlyOutDistance: Int = 0xac

    /** `int32` */
    public const val IterativeLinearCastMaxIterations: Int = 0xb0

    /** `uint8` */
    public const val DeactivationNumInactiveFramesSelectFlag0: Int = 0xb4

    /** `uint8` */
    public const val DeactivationNumInactiveFramesSelectFlag1: Int = 0xb5

    /** `uint8` */
    public const val DeactivationIntegrateCounter: Int = 0xb6

    /** `bool` */
    public const val ShouldActivateOnRigidBodyTransformChange: Int = 0xb7

    /** `float` */
    public const val DeactivationReferenceDistance: Int = 0xb8

    /** `float` */
    public const val ToiCollisionResponseRotateNormal: Int = 0xbc

    /** `bool` */
    public const val UseCompoundSpuElf: Int = 0xc0

    /** `int32` */
    public const val MaxSectorsPerMidphaseCollideTask: Int = 0xc4

    /** `int32` */
    public const val MaxSectorsPerNarrowphaseCollideTask: Int = 0xc8

    /** `bool` */
    public const val ProcessToisMultithreaded: Int = 0xcc

    /** `int32` */
    public const val MaxEntriesPerToiMidphaseCollideTask: Int = 0xd0

    /** `int32` */
    public const val MaxEntriesPerToiNarrowphaseCollideTask: Int = 0xd4

    /** `int32` */
    public const val MaxNumToiCollisionPairsSinglethreaded: Int = 0xd8

    /** `float` */
    public const val NumToisTillAllowedPenetrationSimplifiedToi: Int = 0xdc

    /** `float` */
    public const val NumToisTillAllowedPenetrationToi: Int = 0xe0

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiHigher: Int = 0xe4

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiForced: Int = 0xe8

    /** `bool` */
    public const val EnableDeactivation: Int = 0xec

    /** `enum:int8` */
    public const val SimulationType: Int = 0xed

    /** `bool` */
    public const val EnableSimulationIslands: Int = 0xee

    /** `uint32` */
    public const val MinDesiredIslandSize: Int = 0xf0

    /** `bool` */
    public const val ProcessActionsInSingleThread: Int = 0xf4

    /** `bool` */
    public const val AllowIntegrationOfIslandsWithoutConstraintsInASeparateJob: Int = 0xf5

    /** `float` */
    public const val FrameMarkerPsiSnap: Int = 0xf8

    /** `bool` */
    public const val FireCollisionCallbacks: Int = 0xfc
}

/**
 * `hkpWorldDeletionListener`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpWorldDeletionListener {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpWorldObject`, 0xc8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject`
 */
public object HkpWorldObject {
    public const val ByteSize: Int = 0xc8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `void*` */
    public const val World: Int = 0x10

    /** `hkUlong` */
    public const val UserData: Int = 0x18

    /** `struct` */
    public const val Collidable: Int = 0x20

    /** `struct` */
    public const val MultiThreadCheck: Int = 0xa0

    /** `hkStringPtr` */
    public const val Name: Int = 0xb0

    /** `hkArray<struct>` */
    public const val Properties: Int = 0xb8
}

/**
 * `hkpWorldPostSimulationListener`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpWorldPostSimulationListener {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkpWrappedConstraintData`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpWrappedConstraintData`
 */
public object HkpWrappedConstraintData {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x10

    /** `struct*` */
    public const val ConstraintData: Int = 0x18
}
