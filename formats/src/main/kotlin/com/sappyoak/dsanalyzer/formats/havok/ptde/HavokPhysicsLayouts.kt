// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generatePTDEHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Physics: 236 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.ptde

/**
 * `hkp2dAngConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkp2dAngConstraintAtom`
 */
public object Hkp2dAngConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val FreeRotationAxis: Int = 0x2
}

/**
 * `hkpAabbPhantom`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpAabbPhantom`
 */
public object HkpAabbPhantom {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0x98

    /** `struct` */
    public const val Aabb: Int = 0xb0

    /** `hkArray<ptr>` */
    public const val OverlappingCollidables: Int = 0xd0

    /** `bool` */
    public const val OrderDirty: Int = 0xdc
}

/**
 * `hkpAction`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction`
 */
public object HkpAction {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*` */
    public const val World: Int = 0x8

    /** `void*` */
    public const val Island: Int = 0xc

    /** `hkUlong` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr` */
    public const val Unnamed0x14: Int = 0x14
}

/**
 * `hkpAgent1nSector`, 0x200 bytes.
 */
public object HkpAgent1nSector {
    public const val ByteSize: Int = 0x200

    /** `uint32` */
    public const val BytesAllocated: Int = 0x0

    /** `uint32` */
    public const val Pad0: Int = 0x4

    /** `uint32` */
    public const val Pad1: Int = 0x8

    /** `uint32` */
    public const val Pad2: Int = 0xc

    /** `uint8[496]` */
    public const val Data: Int = 0x10
}

/**
 * `hkpAngConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngConstraintAtom`
 */
public object HkpAngConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val FirstConstrainedAxis: Int = 0x2

    /** `uint8` */
    public const val NumConstrainedAxes: Int = 0x3
}

/**
 * `hkpAngFrictionConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngFrictionConstraintAtom`
 */
public object HkpAngFrictionConstraintAtom {
    public const val ByteSize: Int = 0xc

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
 * `hkpAngLimitConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngLimitConstraintAtom`
 */
public object HkpAngLimitConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val IsEnabled: Int = 0x2

    /** `uint8` */
    public const val LimitAxis: Int = 0x3

    /** `float` */
    public const val MinAngle: Int = 0x4

    /** `float` */
    public const val MaxAngle: Int = 0x8

    /** `float` */
    public const val AngularLimitsTauFactor: Int = 0xc
}

/**
 * `hkpAngMotorConstraintAtom`, 0x14 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngMotorConstraintAtom`
 */
public object HkpAngMotorConstraintAtom {
    public const val ByteSize: Int = 0x14

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

    /** `int16` */
    public const val CorrespondingAngLimitSolverResultOffset: Int = 0x8

    /** `float` */
    public const val TargetAngle: Int = 0xc

    /** `struct*` */
    public const val Motor: Int = 0x10
}

/**
 * `hkpAngularDashpotAction`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpAngularDashpotAction`
 */
public object HkpAngularDashpotAction {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityA: Int = 0x18

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityB: Int = 0x1c

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x20

    /** `float` */
    public const val Strength: Int = 0x30

    /** `float` */
    public const val Damping: Int = 0x34
}

/**
 * `hkpArrayAction`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpArrayAction`
 */
public object HkpArrayAction {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `hkArray<ptr>` */
    public const val Entities: Int = 0x18
}

/**
 * `hkpBallAndSocketConstraintData`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpBallAndSocketConstraintData`
 */
public object HkpBallAndSocketConstraintData {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpBallAndSocketConstraintDataAtoms`, 0x50 bytes.
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
 * `hkpBallGun`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpBallGun`
 */
public object HkpBallGun {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x14

    /** `float` */
    public const val BulletRadius: Int = 0x20

    /** `float` */
    public const val BulletVelocity: Int = 0x24

    /** `float` */
    public const val BulletMass: Int = 0x28

    /** `float` */
    public const val DamageMultiplier: Int = 0x2c

    /** `int32` */
    public const val MaxBulletsInWorld: Int = 0x30

    /** `hkVector4` */
    public const val BulletOffsetFromCenter: Int = 0x40

    /** `void*` */
    public const val AddedBodies: Int = 0x50
}

/**
 * `hkpBallSocketChainDataConstraintInfo`, 0x20 bytes.
 */
public object HkpBallSocketChainDataConstraintInfo {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val PivotInA: Int = 0x0

    /** `hkVector4` */
    public const val PivotInB: Int = 0x10
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

    /** `uint8` */
    public const val VelocityStabilizationFactor: Int = 0x4

    /** `float` */
    public const val MaxImpulse: Int = 0x8

    /** `float` */
    public const val InertiaStabilizationFactor: Int = 0xc
}

/**
 * `hkpBinaryAction`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction`
 */
public object HkpBinaryAction {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val EntityA: Int = 0x18

    /** `struct*` */
    public const val EntityB: Int = 0x1c
}

/**
 * `hkpBoxMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpBoxMotion`
 */
public object HkpBoxMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpBoxShape`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpBoxShape`
 */
public object HkpBoxShape {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `hkVector4` */
    public const val HalfExtents: Int = 0x20
}

/**
 * `hkpBreakableBody`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableBody`
 */
public object HkpBreakableBody {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpBreakableConstraintData`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpBreakableConstraintData`
 */
public object HkpBreakableConstraintData {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0xc

    /** `struct*` */
    public const val ConstraintData: Int = 0x18

    /** `uint16` */
    public const val ChildRuntimeSize: Int = 0x1c

    /** `uint16` */
    public const val ChildNumSolverResults: Int = 0x1e

    /** `float` */
    public const val SolverResultLimit: Int = 0x20

    /** `bool` */
    public const val RemoveWhenBroken: Int = 0x24

    /** `bool` */
    public const val RevertBackVelocityOnBreak: Int = 0x25
}

/**
 * `hkpBridgeAtoms`, 0xc bytes.
 */
public object HkpBridgeAtoms {
    public const val ByteSize: Int = 0xc

    /** `struct` */
    public const val BridgeAtom: Int = 0x0
}

/**
 * `hkpBridgeConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpBridgeConstraintAtom`
 */
public object HkpBridgeConstraintAtom {
    public const val ByteSize: Int = 0xc

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `void*` */
    public const val BuildJacobianFunc: Int = 0x4

    /** `struct*` */
    public const val ConstraintData: Int = 0x8
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
 * `hkpBvShape`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvShape`
 */
public object HkpBvShape {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `struct*` */
    public const val BoundingVolumeShape: Int = 0x10

    /** `struct` */
    public const val ChildShape: Int = 0x14
}

/**
 * `hkpBvTreeShape`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvTreeShape`
 */
public object HkpBvTreeShape {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `enum:uint8` */
    public const val BvTreeType: Int = 0x10
}

/**
 * `hkpCachingShapePhantom`, 0x170 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom` -> `hkpCachingShapePhantom`
 */
public object HkpCachingShapePhantom {
    public const val ByteSize: Int = 0x170

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0x98

    /** `struct`, from `hkpShapePhantom` */
    public const val MotionState: Int = 0xb0

    /** `hkArray<void>` */
    public const val CollisionDetails: Int = 0x160

    /** `bool` */
    public const val OrderDirty: Int = 0x16c
}

/**
 * `hkpCallbackConstraintMotor`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpCallbackConstraintMotor`
 */
public object HkpCallbackConstraintMotor {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x10

    /** `void*` */
    public const val CallbackFunc: Int = 0x14

    /** `enum:uint32` */
    public const val CallbackType: Int = 0x18

    /** `hkUlong` */
    public const val UserData0: Int = 0x1c

    /** `hkUlong` */
    public const val UserData1: Int = 0x20

    /** `hkUlong` */
    public const val UserData2: Int = 0x24
}

/**
 * `hkpCapsuleShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpCapsuleShape`
 */
public object HkpCapsuleShape {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `hkVector4` */
    public const val VertexA: Int = 0x20

    /** `hkVector4` */
    public const val VertexB: Int = 0x30
}

/**
 * `hkpCdBody`, 0x10 bytes.
 */
public object HkpCdBody {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Shape: Int = 0x0

    /** `uint32` */
    public const val ShapeKey: Int = 0x4

    /** `void*` */
    public const val Motion: Int = 0x8

    /** `struct*` */
    public const val Parent: Int = 0xc
}

/**
 * `hkpCenterOfMassChangerModifierConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpCenterOfMassChangerModifierConstraintAtom`
 */
public object HkpCenterOfMassChangerModifierConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x18

    /** `hkVector4` */
    public const val DisplacementA: Int = 0x20

    /** `hkVector4` */
    public const val DisplacementB: Int = 0x30
}

/**
 * `hkpCharacterControllerCinfo`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo`
 */
public object HkpCharacterControllerCinfo {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpCharacterMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpCharacterMotion`
 */
public object HkpCharacterMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpCharacterProxyCinfo`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo` -> `hkpCharacterProxyCinfo`
 */
public object HkpCharacterProxyCinfo {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

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
    public const val KeepDistance: Int = 0x5c

    /** `float` */
    public const val ContactAngleSensitivity: Int = 0x60

    /** `uint32` */
    public const val UserPlanes: Int = 0x64

    /** `float` */
    public const val MaxCharacterSpeedForSolver: Int = 0x68

    /** `float` */
    public const val CharacterStrength: Int = 0x6c

    /** `float` */
    public const val CharacterMass: Int = 0x70

    /** `float` */
    public const val MaxSlope: Int = 0x74

    /** `float` */
    public const val PenetrationRecoverySpeed: Int = 0x78

    /** `int32` */
    public const val MaxCastIterations: Int = 0x7c

    /** `bool` */
    public const val RefreshManifoldInCheckSupport: Int = 0x80
}

/**
 * `hkpCharacterRigidBodyCinfo`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo` -> `hkpCharacterRigidBodyCinfo`
 */
public object HkpCharacterRigidBodyCinfo {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32` */
    public const val CollisionFilterInfo: Int = 0x8

    /** `struct*` */
    public const val Shape: Int = 0xc

    /** `hkVector4` */
    public const val Position: Int = 0x10

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x20

    /** `float` */
    public const val Mass: Int = 0x30

    /** `float` */
    public const val Friction: Int = 0x34

    /** `float` */
    public const val MaxLinearVelocity: Int = 0x38

    /** `float` */
    public const val AllowedPenetrationDepth: Int = 0x3c

    /** `hkVector4` */
    public const val Up: Int = 0x40

    /** `float` */
    public const val MaxSlope: Int = 0x50

    /** `float` */
    public const val MaxForce: Int = 0x54

    /** `float` */
    public const val UnweldingHeightOffsetFactor: Int = 0x58

    /** `float` */
    public const val MaxSpeedForSimplexSolver: Int = 0x5c

    /** `float` */
    public const val SupportDistance: Int = 0x60

    /** `float` */
    public const val HardSupportDistance: Int = 0x64

    /** `int32` */
    public const val VdbColor: Int = 0x68
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
 * `hkpCogWheelConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpCogWheelConstraintData`
 */
public object HkpCogWheelConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpCogWheelConstraintDataAtoms`, 0xa0 bytes.
 */
public object HkpCogWheelConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val CogWheels: Int = 0x90
}

/**
 * `hkpCollidable`, 0x50 bytes.
 *
 * `hkpCdBody` -> `hkpCollidable`
 */
public object HkpCollidable {
    public const val ByteSize: Int = 0x50

    /** `struct*`, from `hkpCdBody` */
    public const val Shape: Int = 0x0

    /** `uint32`, from `hkpCdBody` */
    public const val ShapeKey: Int = 0x4

    /** `void*`, from `hkpCdBody` */
    public const val Motion: Int = 0x8

    /** `struct*`, from `hkpCdBody` */
    public const val Parent: Int = 0xc

    /** `int8` */
    public const val OwnerOffset: Int = 0x10

    /** `uint8` */
    public const val ForceCollideOntoPpu: Int = 0x11

    /** `uint16` */
    public const val ShapeSizeOnSpu: Int = 0x12

    /** `struct` */
    public const val BroadPhaseHandle: Int = 0x14

    /** `struct` */
    public const val BoundingVolumeData: Int = 0x20

    /** `float` */
    public const val AllowedPenetrationDepth: Int = 0x4c
}

/**
 * `hkpCollidableBoundingVolumeData`, 0x2c bytes.
 */
public object HkpCollidableBoundingVolumeData {
    public const val ByteSize: Int = 0x2c

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
    public const val ChildShapeAabbs: Int = 0x24

    /** `void*` */
    public const val ChildShapeKeys: Int = 0x28
}

/**
 * `hkpCollidableCollidableFilter`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpCollidableCollidableFilter {
    public const val ByteSize: Int = 0x4
}

/**
 * `hkpCollisionFilter`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter`
 */
public object HkpCollisionFilter {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32` */
    public const val Type: Int = 0x20

    /** `uint32[3]` */
    public const val Postpad: Int = 0x24
}

/**
 * `hkpCollisionFilterList`, 0x3c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpCollisionFilterList`
 */
public object HkpCollisionFilterList {
    public const val ByteSize: Int = 0x3c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `hkArray<ptr>` */
    public const val CollisionFilters: Int = 0x30
}

/**
 * `hkpCompressedMeshShape`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpCompressedMeshShape`
 */
public object HkpCompressedMeshShape {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `int32` */
    public const val BitsPerIndex: Int = 0x18

    /** `int32` */
    public const val BitsPerWIndex: Int = 0x1c

    /** `int32` */
    public const val WIndexMask: Int = 0x20

    /** `int32` */
    public const val IndexMask: Int = 0x24

    /** `float` */
    public const val Radius: Int = 0x28

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x2c

    /** `enum:uint8` */
    public const val MaterialType: Int = 0x2d

    /** `hkArray<uint32>` */
    public const val Materials: Int = 0x30

    /** `hkArray<uint16>` */
    public const val Materials16: Int = 0x3c

    /** `hkArray<uint8>` */
    public const val Materials8: Int = 0x48

    /** `hkArray<hkQsTransform>` */
    public const val Transforms: Int = 0x54

    /** `hkArray<hkVector4>` */
    public const val BigVertices: Int = 0x60

    /** `hkArray<struct>` */
    public const val BigTriangles: Int = 0x6c

    /** `hkArray<struct>` */
    public const val Chunks: Int = 0x78

    /** `hkArray<struct>` */
    public const val ConvexPieces: Int = 0x84

    /** `float` */
    public const val Error: Int = 0x90

    /** `struct` */
    public const val Bounds: Int = 0xa0

    /** `uint32` */
    public const val DefaultCollisionFilterInfo: Int = 0xc0

    /** `void*` */
    public const val MeshMaterials: Int = 0xc4

    /** `uint16` */
    public const val MaterialStriding: Int = 0xc8

    /** `uint16` */
    public const val NumMaterials: Int = 0xca

    /** `hkArray<struct>` */
    public const val NamedMaterials: Int = 0xcc
}

/**
 * `hkpCompressedMeshShapeBigTriangle`, 0x10 bytes.
 */
public object HkpCompressedMeshShapeBigTriangle {
    public const val ByteSize: Int = 0x10

    /** `uint16` */
    public const val A: Int = 0x0

    /** `uint16` */
    public const val B: Int = 0x2

    /** `uint16` */
    public const val Unnamed0x4: Int = 0x4

    /** `uint32` */
    public const val Material: Int = 0x8

    /** `uint16` */
    public const val WeldingInfo: Int = 0xc

    /** `uint16` */
    public const val TransformIndex: Int = 0xe
}

/**
 * `hkpCompressedMeshShapeChunk`, 0x50 bytes.
 */
public object HkpCompressedMeshShapeChunk {
    public const val ByteSize: Int = 0x50

    /** `hkVector4` */
    public const val Offset: Int = 0x0

    /** `hkArray<uint16>` */
    public const val Vertices: Int = 0x10

    /** `hkArray<uint16>` */
    public const val Indices: Int = 0x1c

    /** `hkArray<uint16>` */
    public const val StripLengths: Int = 0x28

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0x34

    /** `uint32` */
    public const val MaterialInfo: Int = 0x40

    /** `uint16` */
    public const val Reference: Int = 0x44

    /** `uint16` */
    public const val TransformIndex: Int = 0x46
}

/**
 * `hkpCompressedMeshShapeConvexPiece`, 0x40 bytes.
 */
public object HkpCompressedMeshShapeConvexPiece {
    public const val ByteSize: Int = 0x40

    /** `hkVector4` */
    public const val Offset: Int = 0x0

    /** `hkArray<uint16>` */
    public const val Vertices: Int = 0x10

    /** `hkArray<uint8>` */
    public const val FaceVertices: Int = 0x1c

    /** `hkArray<uint16>` */
    public const val FaceOffsets: Int = 0x28

    /** `uint16` */
    public const val Reference: Int = 0x34

    /** `uint16` */
    public const val TransformIndex: Int = 0x36
}

/**
 * `hkpConeLimitConstraintAtom`, 0x14 bytes.
 *
 * `hkpConstraintAtom` -> `hkpConeLimitConstraintAtom`
 */
public object HkpConeLimitConstraintAtom {
    public const val ByteSize: Int = 0x14

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

    /** `uint8` */
    public const val MemOffsetToAngleOffset: Int = 0x6

    /** `float` */
    public const val MinAngle: Int = 0x8

    /** `float` */
    public const val MaxAngle: Int = 0xc

    /** `float` */
    public const val AngularLimitsTauFactor: Int = 0x10
}

/**
 * `hkpConstrainedSystemFilter`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpConstrainedSystemFilter`
 */
public object HkpConstrainedSystemFilter {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `struct*` */
    public const val OtherFilter: Int = 0x34
}

/**
 * `hkpConstraintAtom`, 0x2 bytes.
 */
public object HkpConstraintAtom {
    public const val ByteSize: Int = 0x2

    /** `enum:uint16` */
    public const val Type: Int = 0x0
}

/**
 * `hkpConstraintChainData`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData`
 */
public object HkpConstraintChainData {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8
}

/**
 * `hkpConstraintChainInstanceAction`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpConstraintChainInstanceAction`
 */
public object HkpConstraintChainInstanceAction {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val ConstraintInstance: Int = 0x18
}

/**
 * `hkpConstraintCollisionFilter`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpPairCollisionFilter` -> `hkpConstraintCollisionFilter`
 */
public object HkpConstraintCollisionFilter {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `struct`, from `hkpPairCollisionFilter` */
    public const val DisabledPairs: Int = 0x30

    /** `struct*`, from `hkpPairCollisionFilter` */
    public const val ChildFilter: Int = 0x3c
}

/**
 * `hkpConstraintData`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData`
 */
public object HkpConstraintData {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong` */
    public const val UserData: Int = 0x8
}

/**
 * `hkpConstraintInstance`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintInstance`
 */
public object HkpConstraintInstance {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*` */
    public const val Owner: Int = 0x8

    /** `struct*` */
    public const val Data: Int = 0xc

    /** `struct*` */
    public const val ConstraintModifiers: Int = 0x10

    /** `struct*[2]` */
    public const val Entities: Int = 0x14

    /** `enum:uint8` */
    public const val Priority: Int = 0x1c

    /** `bool` */
    public const val WantRuntime: Int = 0x1d

    /** `enum:uint8` */
    public const val DestructionRemapInfo: Int = 0x1e

    /** `struct` */
    public const val Listeners: Int = 0x20

    /** `hkStringPtr` */
    public const val Unnamed0x28: Int = 0x28

    /** `hkUlong` */
    public const val UserData: Int = 0x2c

    /** `void*` */
    public const val Internal: Int = 0x30

    /** `uint32` */
    public const val Uid: Int = 0x34
}

/**
 * `hkpConstraintInstanceSmallArraySerializeOverrideType`, 0x8 bytes.
 */
public object HkpConstraintInstanceSmallArraySerializeOverrideType {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Data: Int = 0x0

    /** `uint16` */
    public const val Size: Int = 0x4

    /** `uint16` */
    public const val CapacityAndFlags: Int = 0x6
}

/**
 * `hkpConstraintMotor`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor`
 */
public object HkpConstraintMotor {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8` */
    public const val Type: Int = 0x8
}

/**
 * `hkpConvexListFilter`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexListFilter`
 */
public object HkpConvexListFilter {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpConvexListShape`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexListShape`
 */
public object HkpConvexListShape {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `float` */
    public const val MinDistanceToUseConvexHullForGetClosestPoints: Int = 0x18

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x20

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x30

    /** `bool` */
    public const val UseCachedAabb: Int = 0x40

    /** `hkArray<ptr>` */
    public const val ChildShapes: Int = 0x44
}

/**
 * `hkpConvexPieceMeshShape`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpConvexPieceMeshShape`
 */
public object HkpConvexPieceMeshShape {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `struct*` */
    public const val ConvexPieceStream: Int = 0x18

    /** `struct*` */
    public const val DisplayMesh: Int = 0x1c

    /** `float` */
    public const val Radius: Int = 0x20
}

/**
 * `hkpConvexPieceStreamData`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexPieceStreamData`
 */
public object HkpConvexPieceStreamData {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<uint32>` */
    public const val ConvexPieceStream: Int = 0x8

    /** `hkArray<uint32>` */
    public const val ConvexPieceOffsets: Int = 0x14

    /** `hkArray<uint32>` */
    public const val ConvexPieceSingleTriangles: Int = 0x20
}

/**
 * `hkpConvexShape`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape`
 */
public object HkpConvexShape {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float` */
    public const val Radius: Int = 0x10
}

/**
 * `hkpConvexTransformShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase` -> `hkpConvexTransformShape`
 */
public object HkpConvexTransformShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `struct`, from `hkpConvexTransformShapeBase` */
    public const val ChildShape: Int = 0x14

    /** `int32`, from `hkpConvexTransformShapeBase` */
    public const val ChildShapeSize: Int = 0x1c

    /** `hkTransform` */
    public const val Transform: Int = 0x20
}

/**
 * `hkpConvexTransformShapeBase`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase`
 */
public object HkpConvexTransformShapeBase {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `struct` */
    public const val ChildShape: Int = 0x14

    /** `int32` */
    public const val ChildShapeSize: Int = 0x1c
}

/**
 * `hkpConvexTranslateShape`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase` -> `hkpConvexTranslateShape`
 */
public object HkpConvexTranslateShape {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `struct`, from `hkpConvexTransformShapeBase` */
    public const val ChildShape: Int = 0x14

    /** `int32`, from `hkpConvexTransformShapeBase` */
    public const val ChildShapeSize: Int = 0x1c

    /** `hkVector4` */
    public const val Translation: Int = 0x20
}

/**
 * `hkpConvexVerticesConnectivity`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexVerticesConnectivity`
 */
public object HkpConvexVerticesConnectivity {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<uint16>` */
    public const val VertexIndices: Int = 0x8

    /** `hkArray<uint8>` */
    public const val NumVerticesPerFace: Int = 0x14
}

/**
 * `hkpConvexVerticesShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexVerticesShape`
 */
public object HkpConvexVerticesShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x20

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x30

    /** `hkArray<struct>` */
    public const val RotatedVertices: Int = 0x40

    /** `int32` */
    public const val NumVertices: Int = 0x4c

    /** `void*` */
    public const val ExternalObject: Int = 0x50

    /** `void*` */
    public const val GetFaceNormals: Int = 0x54

    /** `hkArray<hkVector4>` */
    public const val PlaneEquations: Int = 0x58

    /** `struct*` */
    public const val Connectivity: Int = 0x64
}

/**
 * `hkpConvexVerticesShapeFourVectors`, 0x30 bytes.
 */
public object HkpConvexVerticesShapeFourVectors {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val X: Int = 0x0

    /** `hkVector4` */
    public const val Y: Int = 0x10

    /** `hkVector4` */
    public const val Z: Int = 0x20
}

/**
 * `hkpCylinderShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpCylinderShape`
 */
public object HkpCylinderShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `float` */
    public const val CylRadius: Int = 0x14

    /** `float` */
    public const val CylBaseRadiusFactorForHeightFieldCollisions: Int = 0x18

    /** `hkVector4` */
    public const val VertexA: Int = 0x20

    /** `hkVector4` */
    public const val VertexB: Int = 0x30

    /** `hkVector4` */
    public const val Perpendicular1: Int = 0x40

    /** `hkVector4` */
    public const val Perpendicular2: Int = 0x50
}

/**
 * `hkpDashpotAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpDashpotAction`
 */
public object HkpDashpotAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityA: Int = 0x18

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityB: Int = 0x1c

    /** `hkVector4[2]` */
    public const val Point: Int = 0x20

    /** `float` */
    public const val Strength: Int = 0x40

    /** `float` */
    public const val Damping: Int = 0x44

    /** `hkVector4` */
    public const val Impulse: Int = 0x50
}

/**
 * `hkpDefaultConvexListFilter`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexListFilter` -> `hkpDefaultConvexListFilter`
 */
public object HkpDefaultConvexListFilter {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpDefaultToiResourceMgr`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDefaultToiResourceMgr`
 */
public object HkpDefaultToiResourceMgr {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpDefaultWorldMemoryWatchDog`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkWorldMemoryAvailableWatchDog` -> `hkpDefaultWorldMemoryWatchDog`
 */
public object HkpDefaultWorldMemoryWatchDog {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `int32` */
    public const val FreeHeapMemoryRequested: Int = 0x8
}

/**
 * `hkpDisableEntityCollisionFilter`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpDisableEntityCollisionFilter`
 */
public object HkpDisableEntityCollisionFilter {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `hkArray<ptr>` */
    public const val DisabledEntities: Int = 0x34
}

/**
 * `hkpDisplayBindingData`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingData`
 */
public object HkpDisplayBindingData {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val RigidBodyBindings: Int = 0x8

    /** `hkArray<ptr>` */
    public const val PhysicsSystemBindings: Int = 0x14
}

/**
 * `hkpDisplayBindingDataPhysicsSystem`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingDataPhysicsSystem`
 */
public object HkpDisplayBindingDataPhysicsSystem {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val Bindings: Int = 0x8

    /** `struct*` */
    public const val System: Int = 0x14
}

/**
 * `hkpDisplayBindingDataRigidBody`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingDataRigidBody`
 */
public object HkpDisplayBindingDataRigidBody {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val RigidBody: Int = 0x8

    /** `struct*` */
    public const val DisplayObjectPtr: Int = 0xc

    /** `hkMatrix4` */
    public const val RigidBodyFromDisplayObjectTransform: Int = 0x10
}

/**
 * `hkpEntity`, 0x220 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpEntity`
 */
public object HkpEntity {
    public const val ByteSize: Int = 0x220

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `struct` */
    public const val Material: Int = 0x8c

    /** `void*` */
    public const val LimitContactImpulseUtilAndFlag: Int = 0x98

    /** `float` */
    public const val DamageMultiplier: Int = 0x9c

    /** `void*` */
    public const val BreakableBody: Int = 0xa0

    /** `uint32` */
    public const val SolverData: Int = 0xa4

    /** `uint16` */
    public const val StorageIndex: Int = 0xa8

    /** `uint16` */
    public const val ContactPointCallbackDelay: Int = 0xaa

    /** `struct` */
    public const val ConstraintsMaster: Int = 0xac

    /** `hkArray<ptr>` */
    public const val ConstraintsSlave: Int = 0xb4

    /** `hkArray<uint8>` */
    public const val ConstraintRuntime: Int = 0xc0

    /** `void*` */
    public const val SimulationIsland: Int = 0xcc

    /** `int8` */
    public const val AutoRemoveLevel: Int = 0xd0

    /** `uint8` */
    public const val NumShapeKeysInContactPointProperties: Int = 0xd1

    /** `uint8` */
    public const val ResponseModifierFlags: Int = 0xd2

    /** `uint32` */
    public const val Uid: Int = 0xd4

    /** `struct` */
    public const val SpuCollisionCallback: Int = 0xd8

    /** `struct` */
    public const val Motion: Int = 0xe0

    /** `struct` */
    public const val ContactListeners: Int = 0x200

    /** `struct` */
    public const val Actions: Int = 0x208

    /** `struct*` */
    public const val LocalFrame: Int = 0x210

    /** `struct*` */
    public const val ExtendedListeners: Int = 0x214

    /** `uint32` */
    public const val NpData: Int = 0x218
}

/**
 * `hkpEntityExtendedListeners`, 0x10 bytes.
 */
public object HkpEntityExtendedListeners {
    public const val ByteSize: Int = 0x10

    /** `struct` */
    public const val ActivationListeners: Int = 0x0

    /** `struct` */
    public const val EntityListeners: Int = 0x8
}

/**
 * `hkpEntitySmallArraySerializeOverrideType`, 0x8 bytes.
 */
public object HkpEntitySmallArraySerializeOverrideType {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Data: Int = 0x0

    /** `uint16` */
    public const val Size: Int = 0x4

    /** `uint16` */
    public const val CapacityAndFlags: Int = 0x6
}

/**
 * `hkpEntitySpuCollisionCallback`, 0x8 bytes.
 */
public object HkpEntitySpuCollisionCallback {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Util: Int = 0x0

    /** `uint16` */
    public const val Capacity: Int = 0x4

    /** `uint8` */
    public const val EventFilter: Int = 0x6

    /** `uint8` */
    public const val UserFilter: Int = 0x7
}

/**
 * `hkpExtendedMeshShape`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape`
 */
public object HkpExtendedMeshShape {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `struct` */
    public const val EmbeddedTrianglesSubpart: Int = 0x20

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x90

    /** `hkVector4` */
    public const val AabbCenter: Int = 0xa0

    /** `void*` */
    public const val MaterialClass: Int = 0xb0

    /** `int32` */
    public const val NumBitsForSubpartIndex: Int = 0xb4

    /** `hkArray<struct>` */
    public const val TrianglesSubparts: Int = 0xb8

    /** `hkArray<struct>` */
    public const val ShapesSubparts: Int = 0xc4

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0xd0

    /** `enum:uint8` */
    public const val WeldingType: Int = 0xdc

    /** `uint32` */
    public const val DefaultCollisionFilterInfo: Int = 0xe0

    /** `int32` */
    public const val CachedNumChildShapes: Int = 0xe4

    /** `float` */
    public const val TriangleRadius: Int = 0xe8

    /** `int32` */
    public const val Padding: Int = 0xec
}

/**
 * `hkpExtendedMeshShapeShapesSubpart`, 0x40 bytes.
 *
 * `hkpExtendedMeshShapeSubpart` -> `hkpExtendedMeshShapeShapesSubpart`
 */
public object HkpExtendedMeshShapeShapesSubpart {
    public const val ByteSize: Int = 0x40

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val Type: Int = 0x0

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialIndexStridingType: Int = 0x1

    /** `int16`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialStriding: Int = 0x2

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialIndexBase: Int = 0x4

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialIndexStriding: Int = 0x8

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val NumMaterials: Int = 0xa

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialBase: Int = 0xc

    /** `hkUlong`, from `hkpExtendedMeshShapeSubpart` */
    public const val UserData: Int = 0x10

    /** `hkArray<ptr>` */
    public const val ChildShapes: Int = 0x14

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x20

    /** `hkVector4` */
    public const val Translation: Int = 0x30
}

/**
 * `hkpExtendedMeshShapeSubpart`, 0x14 bytes.
 */
public object HkpExtendedMeshShapeSubpart {
    public const val ByteSize: Int = 0x14

    /** `enum:int8` */
    public const val Type: Int = 0x0

    /** `enum:int8` */
    public const val MaterialIndexStridingType: Int = 0x1

    /** `int16` */
    public const val MaterialStriding: Int = 0x2

    /** `void*` */
    public const val MaterialIndexBase: Int = 0x4

    /** `uint16` */
    public const val MaterialIndexStriding: Int = 0x8

    /** `uint16` */
    public const val NumMaterials: Int = 0xa

    /** `void*` */
    public const val MaterialBase: Int = 0xc

    /** `hkUlong` */
    public const val UserData: Int = 0x10
}

/**
 * `hkpExtendedMeshShapeTrianglesSubpart`, 0x70 bytes.
 *
 * `hkpExtendedMeshShapeSubpart` -> `hkpExtendedMeshShapeTrianglesSubpart`
 */
public object HkpExtendedMeshShapeTrianglesSubpart {
    public const val ByteSize: Int = 0x70

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val Type: Int = 0x0

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialIndexStridingType: Int = 0x1

    /** `int16`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialStriding: Int = 0x2

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialIndexBase: Int = 0x4

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialIndexStriding: Int = 0x8

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val NumMaterials: Int = 0xa

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val MaterialBase: Int = 0xc

    /** `hkUlong`, from `hkpExtendedMeshShapeSubpart` */
    public const val UserData: Int = 0x10

    /** `int32` */
    public const val NumTriangleShapes: Int = 0x14

    /** `void*` */
    public const val VertexBase: Int = 0x18

    /** `int32` */
    public const val NumVertices: Int = 0x1c

    /** `void*` */
    public const val IndexBase: Int = 0x20

    /** `uint16` */
    public const val VertexStriding: Int = 0x24

    /** `int32` */
    public const val TriangleOffset: Int = 0x28

    /** `uint16` */
    public const val IndexStriding: Int = 0x2c

    /** `enum:int8` */
    public const val StridingType: Int = 0x2e

    /** `int8` */
    public const val FlipAlternateTriangles: Int = 0x2f

    /** `hkVector4` */
    public const val Extrusion: Int = 0x30

    /** `hkQsTransform` */
    public const val Transform: Int = 0x40
}

/**
 * `hkpFastMeshShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpFastMeshShape`
 */
public object HkpFastMeshShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `hkVector4`, from `hkpMeshShape` */
    public const val Scaling: Int = 0x20

    /** `int32`, from `hkpMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0x30

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val Subparts: Int = 0x34

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val WeldingInfo: Int = 0x40

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val WeldingType: Int = 0x4c

    /** `float`, from `hkpMeshShape` */
    public const val Radius: Int = 0x50

    /** `int32[3]`, from `hkpMeshShape` */
    public const val Pad: Int = 0x54
}

/**
 * `hkpFirstPersonGun`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun`
 */
public object HkpFirstPersonGun {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8` */
    public const val Type: Int = 0x8

    /** `hkStringPtr` */
    public const val Unnamed0xc: Int = 0xc

    /** `enum:uint8` */
    public const val KeyboardKey: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Listeners: Int = 0x14
}

/**
 * `hkpFixedRigidMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion` -> `hkpFixedRigidMotion`
 */
public object HkpFixedRigidMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpGenericConstraintData`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpGenericConstraintData`
 */
public object HkpGenericConstraintData {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0xc

    /** `struct` */
    public const val Scheme: Int = 0x18
}

/**
 * `hkpGenericConstraintDataScheme`, 0x40 bytes.
 */
public object HkpGenericConstraintDataScheme {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val Info: Int = 0x0

    /** `hkArray<hkVector4>` */
    public const val Data: Int = 0x10

    /** `hkArray<int32>` */
    public const val Commands: Int = 0x1c

    /** `hkArray<ptr>` */
    public const val Modifiers: Int = 0x28

    /** `hkArray<ptr>` */
    public const val Motors: Int = 0x34
}

/**
 * `hkpGenericConstraintDataSchemeConstraintInfo`, 0x10 bytes.
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
 * `hkpGravityGun`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpGravityGun`
 */
public object HkpGravityGun {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x14

    /** `hkArray<ptr>` */
    public const val GrabbedBodies: Int = 0x20

    /** `int32` */
    public const val MaxNumObjectsPicked: Int = 0x2c

    /** `float` */
    public const val MaxMassOfObjectPicked: Int = 0x30

    /** `float` */
    public const val MaxDistOfObjectPicked: Int = 0x34

    /** `float` */
    public const val ImpulseAppliedWhenObjectNotPicked: Int = 0x38

    /** `float` */
    public const val ThrowVelocity: Int = 0x3c

    /** `hkVector4` */
    public const val CapturedObjectPosition: Int = 0x40

    /** `hkVector4` */
    public const val CapturedObjectsOffset: Int = 0x50
}

/**
 * `hkpGroupCollisionFilter`, 0xb4 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpGroupCollisionFilter`
 */
public object HkpGroupCollisionFilter {
    public const val ByteSize: Int = 0xb4

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `bool` */
    public const val NoGroupCollisionEnabled: Int = 0x30

    /** `uint32[32]` */
    public const val CollisionGroups: Int = 0x34
}

/**
 * `hkpGroupFilter`, 0x100 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpGroupFilter`
 */
public object HkpGroupFilter {
    public const val ByteSize: Int = 0x100

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `int32` */
    public const val NextFreeSystemGroup: Int = 0x30

    /** `uint32[32]` */
    public const val CollisionLookupTable: Int = 0x34

    /** `hkVector4[4]` */
    public const val Pad256: Int = 0xc0
}

/**
 * `hkpHingeConstraintData`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpHingeConstraintData`
 */
public object HkpHingeConstraintData {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpHingeConstraintDataAtoms`, 0xc0 bytes.
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
    public const val BallSocket: Int = 0xa4
}

/**
 * `hkpHingeLimitsData`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpHingeLimitsData`
 */
public object HkpHingeLimitsData {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpHingeLimitsDataAtoms`, 0x90 bytes.
 */
public object HkpHingeLimitsDataAtoms {
    public const val ByteSize: Int = 0x90

    /** `struct` */
    public const val Rotations: Int = 0x0

    /** `struct` */
    public const val AngLimit: Int = 0x70

    /** `struct` */
    public const val `2dAng`: Int = 0x80
}

/**
 * `hkpIgnoreModifierConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpIgnoreModifierConstraintAtom`
 */
public object HkpIgnoreModifierConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x18
}

/**
 * `hkpKeyframedRigidMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion`
 */
public object HkpKeyframedRigidMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpLimitedForceConstraintMotor`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor`
 */
public object HkpLimitedForceConstraintMotor {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x8

    /** `float` */
    public const val MinForce: Int = 0xc

    /** `float` */
    public const val MaxForce: Int = 0x10
}

/**
 * `hkpLimitedHingeConstraintData`, 0x100 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpLimitedHingeConstraintData`
 */
public object HkpLimitedHingeConstraintData {
    public const val ByteSize: Int = 0x100

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpLimitedHingeConstraintDataAtoms`, 0xf0 bytes.
 */
public object HkpLimitedHingeConstraintDataAtoms {
    public const val ByteSize: Int = 0xf0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x90

    /** `struct` */
    public const val AngMotor: Int = 0xa0

    /** `struct` */
    public const val AngFriction: Int = 0xb4

    /** `struct` */
    public const val AngLimit: Int = 0xc0

    /** `struct` */
    public const val `2dAng`: Int = 0xd0

    /** `struct` */
    public const val BallSocket: Int = 0xd4
}

/**
 * `hkpLinConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinConstraintAtom`
 */
public object HkpLinConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val AxisIndex: Int = 0x2
}

/**
 * `hkpLinFrictionConstraintAtom`, 0x8 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinFrictionConstraintAtom`
 */
public object HkpLinFrictionConstraintAtom {
    public const val ByteSize: Int = 0x8

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
 * `hkpLinLimitConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinLimitConstraintAtom`
 */
public object HkpLinLimitConstraintAtom {
    public const val ByteSize: Int = 0xc

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
 * `hkpLinMotorConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinMotorConstraintAtom`
 */
public object HkpLinMotorConstraintAtom {
    public const val ByteSize: Int = 0x10

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

    /** `float` */
    public const val TargetPosition: Int = 0x8

    /** `struct*` */
    public const val Motor: Int = 0xc
}

/**
 * `hkpLinSoftConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinSoftConstraintAtom`
 */
public object HkpLinSoftConstraintAtom {
    public const val ByteSize: Int = 0xc

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
 * `hkpLinearParametricCurve`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpParametricCurve` -> `hkpLinearParametricCurve`
 */
public object HkpLinearParametricCurve {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `float` */
    public const val SmoothingFactor: Int = 0x8

    /** `bool` */
    public const val ClosedLoop: Int = 0xc

    /** `hkVector4` */
    public const val DirNotParallelToTangentAlongWholePath: Int = 0x10

    /** `hkArray<hkVector4>` */
    public const val Points: Int = 0x20

    /** `hkArray<float>` */
    public const val Distance: Int = 0x2c
}

/**
 * `hkpLinkedCollidable`, 0x5c bytes.
 *
 * `hkpCdBody` -> `hkpCollidable` -> `hkpLinkedCollidable`
 */
public object HkpLinkedCollidable {
    public const val ByteSize: Int = 0x5c

    /** `struct*`, from `hkpCdBody` */
    public const val Shape: Int = 0x0

    /** `uint32`, from `hkpCdBody` */
    public const val ShapeKey: Int = 0x4

    /** `void*`, from `hkpCdBody` */
    public const val Motion: Int = 0x8

    /** `struct*`, from `hkpCdBody` */
    public const val Parent: Int = 0xc

    /** `int8`, from `hkpCollidable` */
    public const val OwnerOffset: Int = 0x10

    /** `uint8`, from `hkpCollidable` */
    public const val ForceCollideOntoPpu: Int = 0x11

    /** `uint16`, from `hkpCollidable` */
    public const val ShapeSizeOnSpu: Int = 0x12

    /** `struct`, from `hkpCollidable` */
    public const val BroadPhaseHandle: Int = 0x14

    /** `struct`, from `hkpCollidable` */
    public const val BoundingVolumeData: Int = 0x20

    /** `float`, from `hkpCollidable` */
    public const val AllowedPenetrationDepth: Int = 0x4c

    /** `hkArray<void>` */
    public const val CollisionEntries: Int = 0x50
}

/**
 * `hkpListShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpListShape`
 */
public object HkpListShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `hkArray<struct>` */
    public const val ChildInfo: Int = 0x18

    /** `uint16` */
    public const val Flags: Int = 0x24

    /** `uint16` */
    public const val NumDisabledChildren: Int = 0x26

    /** `hkVector4` */
    public const val AabbHalfExtents: Int = 0x30

    /** `hkVector4` */
    public const val AabbCenter: Int = 0x40

    /** `uint32[8]` */
    public const val EnabledChildren: Int = 0x50
}

/**
 * `hkpListShapeChildInfo`, 0x10 bytes.
 */
public object HkpListShapeChildInfo {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Shape: Int = 0x0

    /** `uint32` */
    public const val CollisionFilterInfo: Int = 0x4

    /** `int32` */
    public const val ShapeSize: Int = 0x8

    /** `int32` */
    public const val NumChildShapes: Int = 0xc
}

/**
 * `hkpMalleableConstraintData`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpMalleableConstraintData`
 */
public object HkpMalleableConstraintData {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct*` */
    public const val ConstraintData: Int = 0xc

    /** `struct` */
    public const val Atoms: Int = 0x10

    /** `float` */
    public const val Strength: Int = 0x1c
}

/**
 * `hkpMassChangerModifierConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpMassChangerModifierConstraintAtom`
 */
public object HkpMassChangerModifierConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x18

    /** `hkVector4` */
    public const val FactorA: Int = 0x20

    /** `hkVector4` */
    public const val FactorB: Int = 0x30
}

/**
 * `hkpMassProperties`, 0x50 bytes.
 */
public object HkpMassProperties {
    public const val ByteSize: Int = 0x50

    /** `float` */
    public const val Volume: Int = 0x0

    /** `float` */
    public const val Mass: Int = 0x4

    /** `hkVector4` */
    public const val CenterOfMass: Int = 0x10

    /** `hkMatrix3` */
    public const val InertiaTensor: Int = 0x20
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
 * `hkpMaxSizeMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion` -> `hkpMaxSizeMotion`
 */
public object HkpMaxSizeMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
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
 * `hkpMeshShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape`
 */
public object HkpMeshShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `hkVector4` */
    public const val Scaling: Int = 0x20

    /** `int32` */
    public const val NumBitsForSubpartIndex: Int = 0x30

    /** `hkArray<struct>` */
    public const val Subparts: Int = 0x34

    /** `hkArray<uint16>` */
    public const val WeldingInfo: Int = 0x40

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x4c

    /** `float` */
    public const val Radius: Int = 0x50

    /** `int32[3]` */
    public const val Pad: Int = 0x54
}

/**
 * `hkpMeshShapeSubpart`, 0x38 bytes.
 */
public object HkpMeshShapeSubpart {
    public const val ByteSize: Int = 0x38

    /** `void*` */
    public const val VertexBase: Int = 0x0

    /** `int32` */
    public const val VertexStriding: Int = 0x4

    /** `int32` */
    public const val NumVertices: Int = 0x8

    /** `void*` */
    public const val IndexBase: Int = 0xc

    /** `enum:int8` */
    public const val StridingType: Int = 0x10

    /** `enum:int8` */
    public const val MaterialIndexStridingType: Int = 0x11

    /** `int32` */
    public const val IndexStriding: Int = 0x14

    /** `int32` */
    public const val FlipAlternateTriangles: Int = 0x18

    /** `int32` */
    public const val NumTriangles: Int = 0x1c

    /** `void*` */
    public const val MaterialIndexBase: Int = 0x20

    /** `int32` */
    public const val MaterialIndexStriding: Int = 0x24

    /** `void*` */
    public const val MaterialBase: Int = 0x28

    /** `int32` */
    public const val MaterialStriding: Int = 0x2c

    /** `int32` */
    public const val NumMaterials: Int = 0x30

    /** `int32` */
    public const val TriangleOffset: Int = 0x34
}

/**
 * `hkpModifierConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom`
 */
public object HkpModifierConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16` */
    public const val ChildSize: Int = 0x12

    /** `struct*` */
    public const val Child: Int = 0x14

    /** `uint32[2]` */
    public const val Pad: Int = 0x18
}

/**
 * `hkpMoppBvTreeShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvTreeShape` -> `hkMoppBvTreeShapeBase` -> `hkpMoppBvTreeShape`
 */
public object HkpMoppBvTreeShape {
    public const val ByteSize: Int = 0x40

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

    /** `struct*`, from `hkMoppBvTreeShapeBase` */
    public const val Code: Int = 0x14

    /** `void*`, from `hkMoppBvTreeShapeBase` */
    public const val MoppData: Int = 0x18

    /** `uint32`, from `hkMoppBvTreeShapeBase` */
    public const val MoppDataSize: Int = 0x1c

    /** `hkVector4`, from `hkMoppBvTreeShapeBase` */
    public const val CodeInfoCopy: Int = 0x20

    /** `struct` */
    public const val Child: Int = 0x30

    /** `int32` */
    public const val ChildSize: Int = 0x38
}

/**
 * `hkpMoppCode`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMoppCode`
 */
public object HkpMoppCode {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct` */
    public const val Info: Int = 0x10

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x20

    /** `enum:int8` */
    public const val BuildType: Int = 0x2c
}

/**
 * `hkpMoppCodeCodeInfo`, 0x10 bytes.
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
 * `hkpMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion`
 */
public object HkpMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8` */
    public const val Type: Int = 0x8

    /** `uint8` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct` */
    public const val MotionState: Int = 0x10

    /** `hkVector4` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*` */
    public const val SavedMotion: Int = 0x118

    /** `uint16` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpMotorAction`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpMotorAction`
 */
public object HkpMotorAction {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpUnaryAction` */
    public const val Entity: Int = 0x18

    /** `hkVector4` */
    public const val Axis: Int = 0x20

    /** `float` */
    public const val SpinRate: Int = 0x30

    /** `float` */
    public const val Gain: Int = 0x34

    /** `bool` */
    public const val Active: Int = 0x38
}

/**
 * `hkpMountedBallGun`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpBallGun` -> `hkpMountedBallGun`
 */
public object HkpMountedBallGun {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x14

    /** `float`, from `hkpBallGun` */
    public const val BulletRadius: Int = 0x20

    /** `float`, from `hkpBallGun` */
    public const val BulletVelocity: Int = 0x24

    /** `float`, from `hkpBallGun` */
    public const val BulletMass: Int = 0x28

    /** `float`, from `hkpBallGun` */
    public const val DamageMultiplier: Int = 0x2c

    /** `int32`, from `hkpBallGun` */
    public const val MaxBulletsInWorld: Int = 0x30

    /** `hkVector4`, from `hkpBallGun` */
    public const val BulletOffsetFromCenter: Int = 0x40

    /** `void*`, from `hkpBallGun` */
    public const val AddedBodies: Int = 0x50

    /** `hkVector4` */
    public const val Position: Int = 0x60
}

/**
 * `hkpMouseSpringAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpMouseSpringAction`
 */
public object HkpMouseSpringAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpUnaryAction` */
    public const val Entity: Int = 0x18

    /** `hkVector4` */
    public const val PositionInRbLocal: Int = 0x20

    /** `hkVector4` */
    public const val MousePositionInWorld: Int = 0x30

    /** `float` */
    public const val SpringDamping: Int = 0x40

    /** `float` */
    public const val SpringElasticity: Int = 0x44

    /** `float` */
    public const val MaxRelativeForce: Int = 0x48

    /** `float` */
    public const val ObjectDamping: Int = 0x4c

    /** `uint32` */
    public const val ShapeKey: Int = 0x50

    /** `hkArray<ptr>` */
    public const val ApplyCallbacks: Int = 0x54
}

/**
 * `hkpMovingSurfaceModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpMovingSurfaceModifierConstraintAtom`
 */
public object HkpMovingSurfaceModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x18

    /** `hkVector4` */
    public const val Velocity: Int = 0x20
}

/**
 * `hkpMultiRayShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpMultiRayShape`
 */
public object HkpMultiRayShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `hkArray<struct>` */
    public const val Rays: Int = 0x10

    /** `float` */
    public const val RayPenetrationDistance: Int = 0x1c
}

/**
 * `hkpMultiRayShapeRay`, 0x20 bytes.
 */
public object HkpMultiRayShapeRay {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val Start: Int = 0x0

    /** `hkVector4` */
    public const val End: Int = 0x10
}

/**
 * `hkpMultiSphereShape`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpMultiSphereShape`
 */
public object HkpMultiSphereShape {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `int32` */
    public const val NumSpheres: Int = 0x10

    /** `hkVector4[8]` */
    public const val Spheres: Int = 0x20
}

/**
 * `hkpNamedMeshMaterial`, 0x8 bytes.
 *
 * `hkpMeshMaterial` -> `hkpNamedMeshMaterial`
 */
public object HkpNamedMeshMaterial {
    public const val ByteSize: Int = 0x8

    /** `uint32`, from `hkpMeshMaterial` */
    public const val FilterInfo: Int = 0x0

    /** `hkStringPtr` */
    public const val Unnamed0x4: Int = 0x4
}

/**
 * `hkpNullCollisionFilter`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpNullCollisionFilter`
 */
public object HkpNullCollisionFilter {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24
}

/**
 * `hkpOverwritePivotConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkpOverwritePivotConstraintAtom`
 */
public object HkpOverwritePivotConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint8` */
    public const val CopyToPivotBFromPivotA: Int = 0x2
}

/**
 * `hkpPairCollisionFilter`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpPairCollisionFilter`
 */
public object HkpPairCollisionFilter {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val Prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val Type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val Postpad: Int = 0x24

    /** `struct` */
    public const val DisabledPairs: Int = 0x30

    /** `struct*` */
    public const val ChildFilter: Int = 0x3c
}

/**
 * `hkpPairCollisionFilterMapPairFilterKeyOverrideType`, 0xc bytes.
 */
public object HkpPairCollisionFilterMapPairFilterKeyOverrideType {
    public const val ByteSize: Int = 0xc

    /** `void*` */
    public const val Elem: Int = 0x0

    /** `int32` */
    public const val NumElems: Int = 0x4

    /** `int32` */
    public const val HashMod: Int = 0x8
}

/**
 * `hkpParametricCurve`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpParametricCurve`
 */
public object HkpParametricCurve {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpPhantom`, 0xa4 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom`
 */
public object HkpPhantom {
    public const val ByteSize: Int = 0xa4

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `hkArray<ptr>` */
    public const val OverlapListeners: Int = 0x8c

    /** `hkArray<ptr>` */
    public const val PhantomListeners: Int = 0x98
}

/**
 * `hkpPhantomCallbackShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpPhantomCallbackShape`
 */
public object HkpPhantomCallbackShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc
}

/**
 * `hkpPhysicsData`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsData`
 */
public object HkpPhysicsData {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val WorldCinfo: Int = 0x8

    /** `hkArray<ptr>` */
    public const val Systems: Int = 0xc
}

/**
 * `hkpPhysicsSystem`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsSystem`
 */
public object HkpPhysicsSystem {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val RigidBodies: Int = 0x8

    /** `hkArray<ptr>` */
    public const val Constraints: Int = 0x14

    /** `hkArray<ptr>` */
    public const val Actions: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Phantoms: Int = 0x2c

    /** `hkStringPtr` */
    public const val Unnamed0x38: Int = 0x38

    /** `hkUlong` */
    public const val UserData: Int = 0x3c

    /** `bool` */
    public const val Active: Int = 0x40
}

/**
 * `hkpPhysicsSystemWithContacts`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsSystem` -> `hkpPhysicsSystemWithContacts`
 */
public object HkpPhysicsSystemWithContacts {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val RigidBodies: Int = 0x8

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val Constraints: Int = 0x14

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val Actions: Int = 0x20

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val Phantoms: Int = 0x2c

    /** `hkStringPtr`, from `hkpPhysicsSystem` */
    public const val Unnamed0x38: Int = 0x38

    /** `hkUlong`, from `hkpPhysicsSystem` */
    public const val UserData: Int = 0x3c

    /** `bool`, from `hkpPhysicsSystem` */
    public const val Active: Int = 0x40

    /** `hkArray<ptr>` */
    public const val Contacts: Int = 0x44
}

/**
 * `hkpPointToPathConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPointToPathConstraintData`
 */
public object HkpPointToPathConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0xc

    /** `struct*` */
    public const val Path: Int = 0x18

    /** `float` */
    public const val MaxFrictionForce: Int = 0x1c

    /** `enum:int8` */
    public const val AngularConstrainedDOF: Int = 0x20

    /** `hkTransform[2]` */
    public const val Transform_OS_KS: Int = 0x30
}

/**
 * `hkpPointToPlaneConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPointToPlaneConstraintData`
 */
public object HkpPointToPlaneConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpPointToPlaneConstraintDataAtoms`, 0xa0 bytes.
 */
public object HkpPointToPlaneConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Lin: Int = 0x90
}

/**
 * `hkpPositionConstraintMotor`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpPositionConstraintMotor`
 */
public object HkpPositionConstraintMotor {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x10

    /** `float` */
    public const val Tau: Int = 0x14

    /** `float` */
    public const val Damping: Int = 0x18

    /** `float` */
    public const val ProportionalRecoveryVelocity: Int = 0x1c

    /** `float` */
    public const val ConstantRecoveryVelocity: Int = 0x20
}

/**
 * `hkpPoweredChainData`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpPoweredChainData`
 */
public object HkpPoweredChainData {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0xc

    /** `hkArray<struct>` */
    public const val Infos: Int = 0x18

    /** `float` */
    public const val Tau: Int = 0x24

    /** `float` */
    public const val Damping: Int = 0x28

    /** `float` */
    public const val CfmLinAdd: Int = 0x2c

    /** `float` */
    public const val CfmLinMul: Int = 0x30

    /** `float` */
    public const val CfmAngAdd: Int = 0x34

    /** `float` */
    public const val CfmAngMul: Int = 0x38

    /** `float` */
    public const val MaxErrorDistance: Int = 0x3c
}

/**
 * `hkpPoweredChainDataConstraintInfo`, 0x50 bytes.
 */
public object HkpPoweredChainDataConstraintInfo {
    public const val ByteSize: Int = 0x50

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
    public const val SwitchBodies: Int = 0x4c
}

/**
 * `hkpPoweredChainMapper`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPoweredChainMapper`
 */
public object HkpPoweredChainMapper {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val Links: Int = 0x8

    /** `hkArray<struct>` */
    public const val Targets: Int = 0x14

    /** `hkArray<ptr>` */
    public const val Chains: Int = 0x20
}

/**
 * `hkpPoweredChainMapperLinkInfo`, 0xc bytes.
 */
public object HkpPoweredChainMapperLinkInfo {
    public const val ByteSize: Int = 0xc

    /** `int32` */
    public const val FirstTargetIdx: Int = 0x0

    /** `int32` */
    public const val NumTargets: Int = 0x4

    /** `struct*` */
    public const val LimitConstraint: Int = 0x8
}

/**
 * `hkpPoweredChainMapperTarget`, 0x8 bytes.
 */
public object HkpPoweredChainMapperTarget {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val Chain: Int = 0x0

    /** `int32` */
    public const val InfoIndex: Int = 0x4
}

/**
 * `hkpPrismaticConstraintData`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPrismaticConstraintData`
 */
public object HkpPrismaticConstraintData {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpPrismaticConstraintDataAtoms`, 0xc0 bytes.
 */
public object HkpPrismaticConstraintDataAtoms {
    public const val ByteSize: Int = 0xc0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val Motor: Int = 0x90

    /** `struct` */
    public const val Friction: Int = 0xa0

    /** `struct` */
    public const val Ang: Int = 0xa8

    /** `struct` */
    public const val Lin0: Int = 0xac

    /** `struct` */
    public const val Lin1: Int = 0xb0

    /** `struct` */
    public const val LinLimit: Int = 0xb4
}

/**
 * `hkpProjectileGun`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpProjectileGun`
 */
public object HkpProjectileGun {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val Type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val Unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val KeyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val Listeners: Int = 0x14

    /** `int32` */
    public const val MaxProjectiles: Int = 0x20

    /** `float` */
    public const val ReloadTime: Int = 0x24

    /** `float` */
    public const val Reload: Int = 0x28

    /** `hkArray<ptr>` */
    public const val Projectiles: Int = 0x2c

    /** `void*` */
    public const val World: Int = 0x38

    /** `void*` */
    public const val DestructionWorld: Int = 0x3c
}

/**
 * `hkpProperty`, 0x10 bytes.
 */
public object HkpProperty {
    public const val ByteSize: Int = 0x10

    /** `uint32` */
    public const val Key: Int = 0x0

    /** `uint32` */
    public const val AlignmentPadding: Int = 0x4

    /** `struct` */
    public const val Value: Int = 0x8
}

/**
 * `hkpPropertyValue`, 0x8 bytes.
 */
public object HkpPropertyValue {
    public const val ByteSize: Int = 0x8

    /** `int64` */
    public const val Data: Int = 0x0
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
 * `hkpPulleyConstraintData`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPulleyConstraintData`
 */
public object HkpPulleyConstraintData {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpPulleyConstraintDataAtoms`, 0x70 bytes.
 */
public object HkpPulleyConstraintDataAtoms {
    public const val ByteSize: Int = 0x70

    /** `struct` */
    public const val Translations: Int = 0x0

    /** `struct` */
    public const val Pulley: Int = 0x30
}

/**
 * `hkpRackAndPinionConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpRackAndPinionConstraintAtom`
 */
public object HkpRackAndPinionConstraintAtom {
    public const val ByteSize: Int = 0xc

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
 * `hkpRackAndPinionConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRackAndPinionConstraintData`
 */
public object HkpRackAndPinionConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpRackAndPinionConstraintDataAtoms`, 0xa0 bytes.
 */
public object HkpRackAndPinionConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val RackAndPinion: Int = 0x90
}

/**
 * `hkpRagdollConstraintData`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRagdollConstraintData`
 */
public object HkpRagdollConstraintData {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpRagdollConstraintDataAtoms`, 0x150 bytes.
 */
public object HkpRagdollConstraintDataAtoms {
    public const val ByteSize: Int = 0x150

    /** `struct` */
    public const val Transforms: Int = 0x0

    /** `struct` */
    public const val SetupStabilization: Int = 0x90

    /** `struct` */
    public const val RagdollMotors: Int = 0xa0

    /** `struct` */
    public const val AngFriction: Int = 0xf0

    /** `struct` */
    public const val TwistLimit: Int = 0xfc

    /** `struct` */
    public const val ConeLimit: Int = 0x110

    /** `struct` */
    public const val PlanesLimit: Int = 0x124

    /** `struct` */
    public const val BallSocket: Int = 0x138
}

/**
 * `hkpRagdollLimitsData`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRagdollLimitsData`
 */
public object HkpRagdollLimitsData {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpRagdollLimitsDataAtoms`, 0xb0 bytes.
 */
public object HkpRagdollLimitsDataAtoms {
    public const val ByteSize: Int = 0xb0

    /** `struct` */
    public const val Rotations: Int = 0x0

    /** `struct` */
    public const val TwistLimit: Int = 0x70

    /** `struct` */
    public const val ConeLimit: Int = 0x84

    /** `struct` */
    public const val PlanesLimit: Int = 0x98
}

/**
 * `hkpRagdollMotorConstraintAtom`, 0x50 bytes.
 *
 * `hkpConstraintAtom` -> `hkpRagdollMotorConstraintAtom`
 */
public object HkpRagdollMotorConstraintAtom {
    public const val ByteSize: Int = 0x50

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
 * `hkpRayCollidableFilter`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpRayCollidableFilter {
    public const val ByteSize: Int = 0x4
}

/**
 * `hkpRayShapeCollectionFilter`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpRayShapeCollectionFilter {
    public const val ByteSize: Int = 0x4
}

/**
 * `hkpRemoveTerminalsMoppModifier`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpRemoveTerminalsMoppModifier`
 */
public object HkpRemoveTerminalsMoppModifier {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<uint32>` */
    public const val RemoveInfo: Int = 0xc

    /** `void*` */
    public const val TempShapesToRemove: Int = 0x18
}

/**
 * `hkpReorientAction`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpReorientAction`
 */
public object HkpReorientAction {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpUnaryAction` */
    public const val Entity: Int = 0x18

    /** `hkVector4` */
    public const val RotationAxis: Int = 0x20

    /** `hkVector4` */
    public const val UpAxis: Int = 0x30

    /** `float` */
    public const val Strength: Int = 0x40

    /** `float` */
    public const val Damping: Int = 0x44
}

/**
 * `hkpRigidBody`, 0x220 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpEntity` -> `hkpRigidBody`
 */
public object HkpRigidBody {
    public const val ByteSize: Int = 0x220

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `struct`, from `hkpEntity` */
    public const val Material: Int = 0x8c

    /** `void*`, from `hkpEntity` */
    public const val LimitContactImpulseUtilAndFlag: Int = 0x98

    /** `float`, from `hkpEntity` */
    public const val DamageMultiplier: Int = 0x9c

    /** `void*`, from `hkpEntity` */
    public const val BreakableBody: Int = 0xa0

    /** `uint32`, from `hkpEntity` */
    public const val SolverData: Int = 0xa4

    /** `uint16`, from `hkpEntity` */
    public const val StorageIndex: Int = 0xa8

    /** `uint16`, from `hkpEntity` */
    public const val ContactPointCallbackDelay: Int = 0xaa

    /** `struct`, from `hkpEntity` */
    public const val ConstraintsMaster: Int = 0xac

    /** `hkArray<ptr>`, from `hkpEntity` */
    public const val ConstraintsSlave: Int = 0xb4

    /** `hkArray<uint8>`, from `hkpEntity` */
    public const val ConstraintRuntime: Int = 0xc0

    /** `void*`, from `hkpEntity` */
    public const val SimulationIsland: Int = 0xcc

    /** `int8`, from `hkpEntity` */
    public const val AutoRemoveLevel: Int = 0xd0

    /** `uint8`, from `hkpEntity` */
    public const val NumShapeKeysInContactPointProperties: Int = 0xd1

    /** `uint8`, from `hkpEntity` */
    public const val ResponseModifierFlags: Int = 0xd2

    /** `uint32`, from `hkpEntity` */
    public const val Uid: Int = 0xd4

    /** `struct`, from `hkpEntity` */
    public const val SpuCollisionCallback: Int = 0xd8

    /** `struct`, from `hkpEntity` */
    public const val Motion: Int = 0xe0

    /** `struct`, from `hkpEntity` */
    public const val ContactListeners: Int = 0x200

    /** `struct`, from `hkpEntity` */
    public const val Actions: Int = 0x208

    /** `struct*`, from `hkpEntity` */
    public const val LocalFrame: Int = 0x210

    /** `struct*`, from `hkpEntity` */
    public const val ExtendedListeners: Int = 0x214

    /** `uint32`, from `hkpEntity` */
    public const val NpData: Int = 0x218
}

/**
 * `hkpRotationalConstraintData`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRotationalConstraintData`
 */
public object HkpRotationalConstraintData {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpRotationalConstraintDataAtoms`, 0x80 bytes.
 */
public object HkpRotationalConstraintDataAtoms {
    public const val ByteSize: Int = 0x80

    /** `struct` */
    public const val Rotations: Int = 0x0

    /** `struct` */
    public const val Ang: Int = 0x70
}

/**
 * `hkpSerializedAgentNnEntry`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedAgentNnEntry`
 */
public object HkpSerializedAgentNnEntry {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val BodyA: Int = 0x8

    /** `struct*` */
    public const val BodyB: Int = 0xc

    /** `hkUlong` */
    public const val BodyAId: Int = 0x10

    /** `hkUlong` */
    public const val BodyBId: Int = 0x14

    /** `bool` */
    public const val UseEntityIds: Int = 0x18

    /** `enum:int8` */
    public const val AgentType: Int = 0x19

    /** `struct` */
    public const val Atom: Int = 0x20

    /** `hkArray<uint8>` */
    public const val PropertiesStream: Int = 0x50

    /** `hkArray<struct>` */
    public const val ContactPoints: Int = 0x5c

    /** `hkArray<uint8>` */
    public const val CpIdMgr: Int = 0x68

    /** `uint8[160]` */
    public const val NnEntryData: Int = 0x74

    /** `struct` */
    public const val TrackInfo: Int = 0x114

    /** `uint8[4]` */
    public const val EndianCheckBuffer: Int = 0x12c

    /** `uint32` */
    public const val Unnamed0x130: Int = 0x130
}

/**
 * `hkpSerializedDisplayMarker`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayMarker`
 */
public object HkpSerializedDisplayMarker {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkTransform` */
    public const val Transform: Int = 0x10
}

/**
 * `hkpSerializedDisplayMarkerList`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayMarkerList`
 */
public object HkpSerializedDisplayMarkerList {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val Markers: Int = 0x8
}

/**
 * `hkpSerializedDisplayRbTransforms`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayRbTransforms`
 */
public object HkpSerializedDisplayRbTransforms {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val Transforms: Int = 0x8
}

/**
 * `hkpSerializedDisplayRbTransformsDisplayTransformPair`, 0x50 bytes.
 */
public object HkpSerializedDisplayRbTransformsDisplayTransformPair {
    public const val ByteSize: Int = 0x50

    /** `struct*` */
    public const val Rb: Int = 0x0

    /** `hkTransform` */
    public const val LocalToDisplay: Int = 0x10
}

/**
 * `hkpSerializedSubTrack1nInfo`, 0x20 bytes.
 *
 * `hkpSerializedTrack1nInfo` -> `hkpSerializedSubTrack1nInfo`
 */
public object HkpSerializedSubTrack1nInfo {
    public const val ByteSize: Int = 0x20

    /** `hkArray<ptr>`, from `hkpSerializedTrack1nInfo` */
    public const val Sectors: Int = 0x0

    /** `hkArray<ptr>`, from `hkpSerializedTrack1nInfo` */
    public const val SubTracks: Int = 0xc

    /** `int32` */
    public const val SectorIndex: Int = 0x18

    /** `int32` */
    public const val OffsetInSector: Int = 0x1c
}

/**
 * `hkpSerializedTrack1nInfo`, 0x18 bytes.
 */
public object HkpSerializedTrack1nInfo {
    public const val ByteSize: Int = 0x18

    /** `hkArray<ptr>` */
    public const val Sectors: Int = 0x0

    /** `hkArray<ptr>` */
    public const val SubTracks: Int = 0xc
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
    public const val MaxAngle: Int = 0x4

    /** `uint8[8]` */
    public const val Padding: Int = 0x8
}

/**
 * `hkpShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape`
 */
public object HkpShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong` */
    public const val UserData: Int = 0x8

    /** `enum:uint32` */
    public const val Type: Int = 0xc
}

/**
 * `hkpShapeCollection`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection`
 */
public object HkpShapeCollection {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8` */
    public const val CollectionType: Int = 0x15
}

/**
 * `hkpShapeCollectionFilter`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpShapeCollectionFilter {
    public const val ByteSize: Int = 0x4
}

/**
 * `hkpShapeContainer`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkpShapeContainer {
    public const val ByteSize: Int = 0x4
}

/**
 * `hkpShapeInfo`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShapeInfo`
 */
public object HkpShapeInfo {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val Shape: Int = 0x8

    /** `bool` */
    public const val IsHierarchicalCompound: Int = 0xc

    /** `bool` */
    public const val HkdShapesCollected: Int = 0xd

    /** `hkArray<hkStringPtr>` */
    public const val ChildShapeNames: Int = 0x10

    /** `hkArray<hkTransform>` */
    public const val ChildTransforms: Int = 0x1c

    /** `hkTransform` */
    public const val Transform: Int = 0x30
}

/**
 * `hkpShapeModifier`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShapeModifier`
 */
public object HkpShapeModifier {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpShapePhantom`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom`
 */
public object HkpShapePhantom {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0x98

    /** `struct` */
    public const val MotionState: Int = 0xb0
}

/**
 * `hkpSimpleConstraintContactMgr`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimpleConstraintContactMgr`
 */
public object HkpSimpleConstraintContactMgr {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
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
    public const val Index: Int = 0x2

    /** `float` */
    public const val InternalData0: Int = 0x4

    /** `hkHalf` */
    public const val RollingFrictionMultiplier: Int = 0x8

    /** `hkHalf` */
    public const val InternalData1: Int = 0xa

    /** `uint32[5]` */
    public const val Data: Int = 0xc
}

/**
 * `hkpSimpleMeshShape`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpSimpleMeshShape`
 */
public object HkpSimpleMeshShape {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `hkArray<hkVector4>` */
    public const val Vertices: Int = 0x18

    /** `hkArray<struct>` */
    public const val Triangles: Int = 0x24

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x30

    /** `float` */
    public const val Radius: Int = 0x3c

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x40
}

/**
 * `hkpSimpleMeshShapeTriangle`, 0x10 bytes.
 */
public object HkpSimpleMeshShapeTriangle {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val A: Int = 0x0

    /** `int32` */
    public const val B: Int = 0x4

    /** `int32` */
    public const val Unnamed0x8: Int = 0x8

    /** `uint16` */
    public const val WeldingInfo: Int = 0xc
}

/**
 * `hkpSimpleShapePhantom`, 0x170 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom` -> `hkpSimpleShapePhantom`
 */
public object HkpSimpleShapePhantom {
    public const val ByteSize: Int = 0x170

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val World: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val UserData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val Collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val Properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val TreeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val OverlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val PhantomListeners: Int = 0x98

    /** `struct`, from `hkpShapePhantom` */
    public const val MotionState: Int = 0xb0

    /** `hkArray<struct>` */
    public const val CollisionDetails: Int = 0x160

    /** `bool` */
    public const val OrderDirty: Int = 0x16c
}

/**
 * `hkpSimpleShapePhantomCollisionDetail`, 0x4 bytes.
 */
public object HkpSimpleShapePhantomCollisionDetail {
    public const val ByteSize: Int = 0x4

    /** `struct*` */
    public const val Collidable: Int = 0x0
}

/**
 * `hkpSimulation`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimulation`
 */
public object HkpSimulation {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `uint32` */
    public const val DeterminismCheckFrameCounter: Int = 0x8

    /** `struct*` */
    public const val World: Int = 0xc

    /** `enum:uint8` */
    public const val LastProcessingStep: Int = 0x10

    /** `float` */
    public const val CurrentTime: Int = 0x14

    /** `float` */
    public const val CurrentPsiTime: Int = 0x18

    /** `float` */
    public const val PhysicsDeltaTime: Int = 0x1c

    /** `float` */
    public const val SimulateUntilTime: Int = 0x20

    /** `float` */
    public const val FrameMarkerPsiSnap: Int = 0x24

    /** `uint32` */
    public const val PreviousStepResult: Int = 0x28
}

/**
 * `hkpSimulationIsland`, 0x84 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimulationIsland`
 */
public object HkpSimulationIsland {
    public const val ByteSize: Int = 0x84

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6
}

/**
 * `hkpSingleShapeContainer`, 0x8 bytes.
 *
 * `hkpShapeContainer` -> `hkpSingleShapeContainer`
 */
public object HkpSingleShapeContainer {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val ChildShape: Int = 0x4
}

/**
 * `hkpSoftContactModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpSoftContactModifierConstraintAtom`
 */
public object HkpSoftContactModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x18

    /** `float` */
    public const val Tau: Int = 0x20

    /** `float` */
    public const val MaxAcceleration: Int = 0x24
}

/**
 * `hkpSphereMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpSphereMotion`
 */
public object HkpSphereMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpSphereRepShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape`
 */
public object HkpSphereRepShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc
}

/**
 * `hkpSphereShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpSphereShape`
 */
public object HkpSphereShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `uint32[3]` */
    public const val Pad16: Int = 0x14
}

/**
 * `hkpSpringAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpSpringAction`
 */
public object HkpSpringAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityA: Int = 0x18

    /** `struct*`, from `hkpBinaryAction` */
    public const val EntityB: Int = 0x1c

    /** `hkVector4` */
    public const val LastForce: Int = 0x20

    /** `hkVector4` */
    public const val PositionAinA: Int = 0x30

    /** `hkVector4` */
    public const val PositionBinB: Int = 0x40

    /** `float` */
    public const val RestLength: Int = 0x50

    /** `float` */
    public const val Strength: Int = 0x54

    /** `float` */
    public const val Damping: Int = 0x58

    /** `bool` */
    public const val OnCompression: Int = 0x5c

    /** `bool` */
    public const val OnExtension: Int = 0x5d
}

/**
 * `hkpSpringDamperConstraintMotor`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpSpringDamperConstraintMotor`
 */
public object HkpSpringDamperConstraintMotor {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x10

    /** `float` */
    public const val SpringConstant: Int = 0x14

    /** `float` */
    public const val SpringDamping: Int = 0x18
}

/**
 * `hkpStiffSpringChainData`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpStiffSpringChainData`
 */
public object HkpStiffSpringChainData {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0xc

    /** `hkArray<struct>` */
    public const val Infos: Int = 0x18

    /** `float` */
    public const val Tau: Int = 0x24

    /** `float` */
    public const val Damping: Int = 0x28

    /** `float` */
    public const val Cfm: Int = 0x2c
}

/**
 * `hkpStiffSpringChainDataConstraintInfo`, 0x30 bytes.
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
 * `hkpStiffSpringConstraintAtom`, 0x8 bytes.
 *
 * `hkpConstraintAtom` -> `hkpStiffSpringConstraintAtom`
 */
public object HkpStiffSpringConstraintAtom {
    public const val ByteSize: Int = 0x8

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `float` */
    public const val Length: Int = 0x4
}

/**
 * `hkpStiffSpringConstraintData`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpStiffSpringConstraintData`
 */
public object HkpStiffSpringConstraintData {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10
}

/**
 * `hkpStiffSpringConstraintDataAtoms`, 0x40 bytes.
 */
public object HkpStiffSpringConstraintDataAtoms {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val Pivots: Int = 0x0

    /** `struct` */
    public const val Spring: Int = 0x30
}

/**
 * `hkpStorageExtendedMeshShape`, 0x110 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape` -> `hkpStorageExtendedMeshShape`
 */
public object HkpStorageExtendedMeshShape {
    public const val ByteSize: Int = 0x110

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `struct`, from `hkpExtendedMeshShape` */
    public const val EmbeddedTrianglesSubpart: Int = 0x20

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val AabbHalfExtents: Int = 0x90

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val AabbCenter: Int = 0xa0

    /** `void*`, from `hkpExtendedMeshShape` */
    public const val MaterialClass: Int = 0xb0

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0xb4

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val TrianglesSubparts: Int = 0xb8

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val ShapesSubparts: Int = 0xc4

    /** `hkArray<uint16>`, from `hkpExtendedMeshShape` */
    public const val WeldingInfo: Int = 0xd0

    /** `enum:uint8`, from `hkpExtendedMeshShape` */
    public const val WeldingType: Int = 0xdc

    /** `uint32`, from `hkpExtendedMeshShape` */
    public const val DefaultCollisionFilterInfo: Int = 0xe0

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val CachedNumChildShapes: Int = 0xe4

    /** `float`, from `hkpExtendedMeshShape` */
    public const val TriangleRadius: Int = 0xe8

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val Padding: Int = 0xec

    /** `hkArray<ptr>` */
    public const val MeshStorage: Int = 0xf0

    /** `hkArray<ptr>` */
    public const val ShapeStorage: Int = 0xfc
}

/**
 * `hkpStorageExtendedMeshShapeMaterial`, 0xc bytes.
 *
 * `hkpMeshMaterial` -> `hkpStorageExtendedMeshShapeMaterial`
 */
public object HkpStorageExtendedMeshShapeMaterial {
    public const val ByteSize: Int = 0xc

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
 * `hkpStorageExtendedMeshShapeMeshSubpartStorage`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageExtendedMeshShapeMeshSubpartStorage`
 */
public object HkpStorageExtendedMeshShapeMeshSubpartStorage {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<hkVector4>` */
    public const val Vertices: Int = 0x8

    /** `hkArray<uint8>` */
    public const val Indices8: Int = 0x14

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x20

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x2c

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x38

    /** `hkArray<struct>` */
    public const val Materials: Int = 0x44

    /** `hkArray<struct>` */
    public const val NamedMaterials: Int = 0x50

    /** `hkArray<uint16>` */
    public const val MaterialIndices16: Int = 0x5c
}

/**
 * `hkpStorageExtendedMeshShapeShapeSubpartStorage`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageExtendedMeshShapeShapeSubpartStorage`
 */
public object HkpStorageExtendedMeshShapeShapeSubpartStorage {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x8

    /** `hkArray<struct>` */
    public const val Materials: Int = 0x14

    /** `hkArray<uint16>` */
    public const val MaterialIndices16: Int = 0x20
}

/**
 * `hkpStorageMeshShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpStorageMeshShape`
 */
public object HkpStorageMeshShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x15

    /** `hkVector4`, from `hkpMeshShape` */
    public const val Scaling: Int = 0x20

    /** `int32`, from `hkpMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0x30

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val Subparts: Int = 0x34

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val WeldingInfo: Int = 0x40

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val WeldingType: Int = 0x4c

    /** `float`, from `hkpMeshShape` */
    public const val Radius: Int = 0x50

    /** `int32[3]`, from `hkpMeshShape` */
    public const val Pad: Int = 0x54

    /** `hkArray<ptr>` */
    public const val Storage: Int = 0x60
}

/**
 * `hkpStorageMeshShapeSubpartStorage`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageMeshShapeSubpartStorage`
 */
public object HkpStorageMeshShapeSubpartStorage {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<float>` */
    public const val Vertices: Int = 0x8

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x14

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x20

    /** `hkArray<uint8>` */
    public const val MaterialIndices: Int = 0x2c

    /** `hkArray<uint32>` */
    public const val Materials: Int = 0x38

    /** `hkArray<uint16>` */
    public const val MaterialIndices16: Int = 0x44
}

/**
 * `hkpThinBoxMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpBoxMotion` -> `hkpThinBoxMotion`
 */
public object HkpThinBoxMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val Type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val DeactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val DeactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val MotionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val InertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val LinearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val AngularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val DeactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val DeactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val SavedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val SavedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val GravityFactor: Int = 0x11e
}

/**
 * `hkpTransformShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpTransformShape`
 */
public object HkpTransformShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `struct` */
    public const val ChildShape: Int = 0x10

    /** `int32` */
    public const val ChildShapeSize: Int = 0x18

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x20

    /** `hkTransform` */
    public const val Transform: Int = 0x30
}

/**
 * `hkpTriangleShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpTriangleShape`
 */
public object HkpTriangleShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val Type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val Radius: Int = 0x10

    /** `uint16` */
    public const val WeldingInfo: Int = 0x14

    /** `enum:uint8` */
    public const val WeldingType: Int = 0x16

    /** `uint8` */
    public const val IsExtruded: Int = 0x17

    /** `hkVector4` */
    public const val VertexA: Int = 0x20

    /** `hkVector4` */
    public const val VertexB: Int = 0x30

    /** `hkVector4` */
    public const val VertexC: Int = 0x40

    /** `hkVector4` */
    public const val Extrusion: Int = 0x50
}

/**
 * `hkpTriggerVolume`, 0x34 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpTriggerVolume`
 */
public object HkpTriggerVolume {
    public const val ByteSize: Int = 0x34

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val OverlappingBodies: Int = 0x14

    /** `hkArray<struct>` */
    public const val EventQueue: Int = 0x20

    /** `struct*` */
    public const val TriggerBody: Int = 0x2c

    /** `uint32` */
    public const val SequenceNumber: Int = 0x30
}

/**
 * `hkpTriggerVolumeEventInfo`, 0x10 bytes.
 */
public object HkpTriggerVolumeEventInfo {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val SortValue: Int = 0x0

    /** `struct*` */
    public const val Body: Int = 0x8

    /** `enum:int32` */
    public const val Operation: Int = 0xc
}

/**
 * `hkpTwistLimitConstraintAtom`, 0x14 bytes.
 *
 * `hkpConstraintAtom` -> `hkpTwistLimitConstraintAtom`
 */
public object HkpTwistLimitConstraintAtom {
    public const val ByteSize: Int = 0x14

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
 * `hkpUnaryAction`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction`
 */
public object HkpUnaryAction {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val World: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val Island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val UserData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val Unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val Entity: Int = 0x18
}

/**
 * `hkpVelocityConstraintMotor`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpVelocityConstraintMotor`
 */
public object HkpVelocityConstraintMotor {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val Type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MinForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val MaxForce: Int = 0x10

    /** `float` */
    public const val Tau: Int = 0x14

    /** `float` */
    public const val VelocityTarget: Int = 0x18

    /** `bool` */
    public const val UseVelocityTargetFromConstraintTargets: Int = 0x1c
}

/**
 * `hkpViscousSurfaceModifierConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpViscousSurfaceModifierConstraintAtom`
 */
public object HkpViscousSurfaceModifierConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val Type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ModifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val ChildSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val Child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val Pad: Int = 0x18
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
 * `hkpWheelConstraintData`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpWheelConstraintData`
 */
public object HkpWheelConstraintData {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val UserData: Int = 0x8

    /** `struct` */
    public const val Atoms: Int = 0x10

    /** `hkVector4` */
    public const val InitialAxleInB: Int = 0x140

    /** `hkVector4` */
    public const val InitialSteeringAxisInB: Int = 0x150
}

/**
 * `hkpWheelConstraintDataAtoms`, 0x130 bytes.
 */
public object HkpWheelConstraintDataAtoms {
    public const val ByteSize: Int = 0x130

    /** `struct` */
    public const val SuspensionBase: Int = 0x0

    /** `struct` */
    public const val Lin0Limit: Int = 0x90

    /** `struct` */
    public const val Lin0Soft: Int = 0x9c

    /** `struct` */
    public const val Lin1: Int = 0xa8

    /** `struct` */
    public const val Lin2: Int = 0xac

    /** `struct` */
    public const val SteeringBase: Int = 0xb0

    /** `struct` */
    public const val `2dAng`: Int = 0x120
}

/**
 * `hkpWorld`, 0x360 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorld`
 */
public object HkpWorld {
    public const val ByteSize: Int = 0x360

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `struct*` */
    public const val Simulation: Int = 0x8

    /** `hkVector4` */
    public const val Gravity: Int = 0x10

    /** `void*` */
    public const val FixedIsland: Int = 0x20

    /** `struct*` */
    public const val FixedRigidBody: Int = 0x24

    /** `hkArray<ptr>` */
    public const val ActiveSimulationIslands: Int = 0x28

    /** `hkArray<ptr>` */
    public const val InactiveSimulationIslands: Int = 0x34

    /** `hkArray<ptr>` */
    public const val DirtySimulationIslands: Int = 0x40

    /** `void*` */
    public const val MaintenanceMgr: Int = 0x4c

    /** `void*` */
    public const val MemoryWatchDog: Int = 0x50

    /** `bool` */
    public const val AssertOnRunningOutOfSolverMemory: Int = 0x54

    /** `void*` */
    public const val BroadPhase: Int = 0x58

    /** `void*` */
    public const val KdTreeManager: Int = 0x5c

    /** `bool` */
    public const val AutoUpdateTree: Int = 0x60

    /** `void*` */
    public const val BroadPhaseDispatcher: Int = 0x64

    /** `void*` */
    public const val PhantomBroadPhaseListener: Int = 0x68

    /** `void*` */
    public const val EntityEntityBroadPhaseListener: Int = 0x6c

    /** `void*` */
    public const val BroadPhaseBorderListener: Int = 0x70

    /** `void*` */
    public const val MultithreadedSimulationJobData: Int = 0x74

    /** `void*` */
    public const val CollisionInput: Int = 0x78

    /** `void*` */
    public const val CollisionFilter: Int = 0x7c

    /** `void*` */
    public const val CollisionDispatcher: Int = 0x80

    /** `void*` */
    public const val ConvexListFilter: Int = 0x84

    /** `void*` */
    public const val PendingOperations: Int = 0x88

    /** `int32` */
    public const val PendingOperationsCount: Int = 0x8c

    /** `int32` */
    public const val PendingBodyOperationsCount: Int = 0x90

    /** `int32` */
    public const val CriticalOperationsLockCount: Int = 0x94

    /** `int32` */
    public const val CriticalOperationsLockCountForPhantoms: Int = 0x98

    /** `bool` */
    public const val BlockExecutingPendingOperations: Int = 0x9c

    /** `bool` */
    public const val CriticalOperationsAllowed: Int = 0x9d

    /** `void*` */
    public const val PendingOperationQueues: Int = 0xa0

    /** `int32` */
    public const val PendingOperationQueueCount: Int = 0xa4

    /** `struct` */
    public const val MultiThreadCheck: Int = 0xa8

    /** `bool` */
    public const val ProcessActionsInSingleThread: Int = 0xb4

    /** `bool` */
    public const val AllowIntegrationOfIslandsWithoutConstraintsInASeparateJob: Int = 0xb5

    /** `uint32` */
    public const val MinDesiredIslandSize: Int = 0xb8

    /** `void*` */
    public const val ModifyConstraintCriticalSection: Int = 0xbc

    /** `int32` */
    public const val IsLocked: Int = 0xc0

    /** `void*` */
    public const val IslandDirtyListCriticalSection: Int = 0xc4

    /** `void*` */
    public const val PropertyMasterLock: Int = 0xc8

    /** `bool` */
    public const val WantSimulationIslands: Int = 0xcc

    /** `bool` */
    public const val UseHybridBroadphase: Int = 0xcd

    /** `float` */
    public const val SnapCollisionToConvexEdgeThreshold: Int = 0xd0

    /** `float` */
    public const val SnapCollisionToConcaveEdgeThreshold: Int = 0xd4

    /** `bool` */
    public const val EnableToiWeldRejection: Int = 0xd8

    /** `bool` */
    public const val WantDeactivation: Int = 0xd9

    /** `bool` */
    public const val ShouldActivateOnRigidBodyTransformChange: Int = 0xda

    /** `float` */
    public const val DeactivationReferenceDistance: Int = 0xdc

    /** `float` */
    public const val ToiCollisionResponseRotateNormal: Int = 0xe0

    /** `int32` */
    public const val MaxSectorsPerMidphaseCollideTask: Int = 0xe4

    /** `int32` */
    public const val MaxSectorsPerNarrowphaseCollideTask: Int = 0xe8

    /** `bool` */
    public const val ProcessToisMultithreaded: Int = 0xec

    /** `int32` */
    public const val MaxEntriesPerToiMidphaseCollideTask: Int = 0xf0

    /** `int32` */
    public const val MaxEntriesPerToiNarrowphaseCollideTask: Int = 0xf4

    /** `int32` */
    public const val MaxNumToiCollisionPairsSinglethreaded: Int = 0xf8

    /** `enum:int32` */
    public const val SimulationType: Int = 0xfc

    /** `float` */
    public const val NumToisTillAllowedPenetrationSimplifiedToi: Int = 0x100

    /** `float` */
    public const val NumToisTillAllowedPenetrationToi: Int = 0x104

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiHigher: Int = 0x108

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiForced: Int = 0x10c

    /** `uint32` */
    public const val LastEntityUid: Int = 0x110

    /** `uint32` */
    public const val LastIslandUid: Int = 0x114

    /** `uint32` */
    public const val LastConstraintUid: Int = 0x118

    /** `hkArray<ptr>` */
    public const val Phantoms: Int = 0x11c

    /** `hkArray<ptr>` */
    public const val ActionListeners: Int = 0x128

    /** `hkArray<ptr>` */
    public const val EntityListeners: Int = 0x134

    /** `hkArray<ptr>` */
    public const val PhantomListeners: Int = 0x140

    /** `hkArray<ptr>` */
    public const val ConstraintListeners: Int = 0x14c

    /** `hkArray<ptr>` */
    public const val WorldDeletionListeners: Int = 0x158

    /** `hkArray<ptr>` */
    public const val IslandActivationListeners: Int = 0x164

    /** `hkArray<ptr>` */
    public const val WorldPostSimulationListeners: Int = 0x170

    /** `hkArray<ptr>` */
    public const val WorldPostIntegrateListeners: Int = 0x17c

    /** `hkArray<ptr>` */
    public const val WorldPostCollideListeners: Int = 0x188

    /** `hkArray<ptr>` */
    public const val IslandPostIntegrateListeners: Int = 0x194

    /** `hkArray<ptr>` */
    public const val IslandPostCollideListeners: Int = 0x1a0

    /** `hkArray<ptr>` */
    public const val ContactListeners: Int = 0x1ac

    /** `hkArray<ptr>` */
    public const val ContactImpulseLimitBreachedListeners: Int = 0x1b8

    /** `hkArray<ptr>` */
    public const val WorldExtensions: Int = 0x1c4

    /** `void*` */
    public const val ViolatedConstraintArray: Int = 0x1d0

    /** `void*` */
    public const val BroadPhaseBorder: Int = 0x1d4

    /** `void*` */
    public const val DestructionWorld: Int = 0x1d8

    /** `void*` */
    public const val NpWorld: Int = 0x1dc

    /** `hkVector4[2]` */
    public const val BroadPhaseExtents: Int = 0x320

    /** `int32` */
    public const val BroadPhaseNumMarkers: Int = 0x340

    /** `int32` */
    public const val SizeOfToiEventQueue: Int = 0x344

    /** `int32` */
    public const val BroadPhaseQuerySize: Int = 0x348

    /** `int32` */
    public const val BroadPhaseUpdateSize: Int = 0x34c

    /** `enum:int8` */
    public const val ContactPointGeneration: Int = 0x350
}

/**
 * `hkpWorldCinfo`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldCinfo`
 */
public object HkpWorldCinfo {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `hkVector4` */
    public const val Gravity: Int = 0x10

    /** `int32` */
    public const val BroadPhaseQuerySize: Int = 0x20

    /** `float` */
    public const val ContactRestingVelocity: Int = 0x24

    /** `enum:int8` */
    public const val BroadPhaseBorderBehaviour: Int = 0x28

    /** `bool` */
    public const val MtPostponeAndSortBroadPhaseBorderCallbacks: Int = 0x29

    /** `struct` */
    public const val BroadPhaseWorldAabb: Int = 0x30

    /** `bool` */
    public const val UseKdTree: Int = 0x50

    /** `bool` */
    public const val UseMultipleTree: Int = 0x51

    /** `enum:int8` */
    public const val TreeUpdateType: Int = 0x52

    /** `bool` */
    public const val AutoUpdateKdTree: Int = 0x53

    /** `float` */
    public const val CollisionTolerance: Int = 0x54

    /** `struct*` */
    public const val CollisionFilter: Int = 0x58

    /** `struct*` */
    public const val ConvexListFilter: Int = 0x5c

    /** `float` */
    public const val ExpectedMaxLinearVelocity: Int = 0x60

    /** `int32` */
    public const val SizeOfToiEventQueue: Int = 0x64

    /** `float` */
    public const val ExpectedMinPsiDeltaTime: Int = 0x68

    /** `struct*` */
    public const val MemoryWatchDog: Int = 0x6c

    /** `int32` */
    public const val BroadPhaseNumMarkers: Int = 0x70

    /** `enum:int8` */
    public const val ContactPointGeneration: Int = 0x74

    /** `bool` */
    public const val AllowToSkipConfirmedCallbacks: Int = 0x75

    /** `bool` */
    public const val UseHybridBroadphase: Int = 0x76

    /** `float` */
    public const val SolverTau: Int = 0x78

    /** `float` */
    public const val SolverDamp: Int = 0x7c

    /** `int32` */
    public const val SolverIterations: Int = 0x80

    /** `int32` */
    public const val SolverMicrosteps: Int = 0x84

    /** `float` */
    public const val MaxConstraintViolation: Int = 0x88

    /** `bool` */
    public const val ForceCoherentConstraintOrderingInSolver: Int = 0x8c

    /** `float` */
    public const val SnapCollisionToConvexEdgeThreshold: Int = 0x90

    /** `float` */
    public const val SnapCollisionToConcaveEdgeThreshold: Int = 0x94

    /** `bool` */
    public const val EnableToiWeldRejection: Int = 0x98

    /** `bool` */
    public const val EnableDeprecatedWelding: Int = 0x99

    /** `float` */
    public const val IterativeLinearCastEarlyOutDistance: Int = 0x9c

    /** `int32` */
    public const val IterativeLinearCastMaxIterations: Int = 0xa0

    /** `uint8` */
    public const val DeactivationNumInactiveFramesSelectFlag0: Int = 0xa4

    /** `uint8` */
    public const val DeactivationNumInactiveFramesSelectFlag1: Int = 0xa5

    /** `uint8` */
    public const val DeactivationIntegrateCounter: Int = 0xa6

    /** `bool` */
    public const val ShouldActivateOnRigidBodyTransformChange: Int = 0xa7

    /** `float` */
    public const val DeactivationReferenceDistance: Int = 0xa8

    /** `float` */
    public const val ToiCollisionResponseRotateNormal: Int = 0xac

    /** `int32` */
    public const val MaxSectorsPerMidphaseCollideTask: Int = 0xb0

    /** `int32` */
    public const val MaxSectorsPerNarrowphaseCollideTask: Int = 0xb4

    /** `bool` */
    public const val ProcessToisMultithreaded: Int = 0xb8

    /** `int32` */
    public const val MaxEntriesPerToiMidphaseCollideTask: Int = 0xbc

    /** `int32` */
    public const val MaxEntriesPerToiNarrowphaseCollideTask: Int = 0xc0

    /** `int32` */
    public const val MaxNumToiCollisionPairsSinglethreaded: Int = 0xc4

    /** `float` */
    public const val NumToisTillAllowedPenetrationSimplifiedToi: Int = 0xc8

    /** `float` */
    public const val NumToisTillAllowedPenetrationToi: Int = 0xcc

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiHigher: Int = 0xd0

    /** `float` */
    public const val NumToisTillAllowedPenetrationToiForced: Int = 0xd4

    /** `bool` */
    public const val EnableDeactivation: Int = 0xd8

    /** `enum:int8` */
    public const val SimulationType: Int = 0xd9

    /** `bool` */
    public const val EnableSimulationIslands: Int = 0xda

    /** `uint32` */
    public const val MinDesiredIslandSize: Int = 0xdc

    /** `bool` */
    public const val ProcessActionsInSingleThread: Int = 0xe0

    /** `bool` */
    public const val AllowIntegrationOfIslandsWithoutConstraintsInASeparateJob: Int = 0xe1

    /** `float` */
    public const val FrameMarkerPsiSnap: Int = 0xe4

    /** `bool` */
    public const val FireCollisionCallbacks: Int = 0xe8
}

/**
 * `hkpWorldObject`, 0x8c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject`
 */
public object HkpWorldObject {
    public const val ByteSize: Int = 0x8c

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val ReferenceCount: Int = 0x6

    /** `void*` */
    public const val World: Int = 0x8

    /** `hkUlong` */
    public const val UserData: Int = 0xc

    /** `struct` */
    public const val Collidable: Int = 0x10

    /** `struct` */
    public const val MultiThreadCheck: Int = 0x6c

    /** `hkStringPtr` */
    public const val Unnamed0x78: Int = 0x78

    /** `hkArray<struct>` */
    public const val Properties: Int = 0x7c

    /** `void*` */
    public const val TreeData: Int = 0x88
}
