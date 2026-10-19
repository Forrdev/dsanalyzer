// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateHavokLayouts
//
// Source: tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)
//
// Physics: 236 of 401 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.runtime.ptde.havok

/**
 * `hkp2dAngConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkp2dAngConstraintAtom`
 */
public object Hkp2dAngConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val freeRotationAxis: Int = 0x2
}

/**
 * `hkpAabbPhantom`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpAabbPhantom`
 */
public object HkpAabbPhantom {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val overlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val phantomListeners: Int = 0x98

    /** `struct` */
    public const val aabb: Int = 0xb0

    /** `hkArray<ptr>` */
    public const val overlappingCollidables: Int = 0xd0

    /** `bool` */
    public const val orderDirty: Int = 0xdc
}

/**
 * `hkpAction`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction`
 */
public object HkpAction {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*` */
    public const val world: Int = 0x8

    /** `void*` */
    public const val island: Int = 0xc

    /** `hkUlong` */
    public const val userData: Int = 0x10

    /** `hkStringPtr` */
    public const val unnamed0x14: Int = 0x14
}

/**
 * `hkpAgent1nSector`, 0x200 bytes.
 */
public object HkpAgent1nSector {
    public const val ByteSize: Int = 0x200

    /** `uint32` */
    public const val bytesAllocated: Int = 0x0

    /** `uint32` */
    public const val pad0: Int = 0x4

    /** `uint32` */
    public const val pad1: Int = 0x8

    /** `uint32` */
    public const val pad2: Int = 0xc

    /** `uint8[496]` */
    public const val data: Int = 0x10
}

/**
 * `hkpAngConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngConstraintAtom`
 */
public object HkpAngConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val firstConstrainedAxis: Int = 0x2

    /** `uint8` */
    public const val numConstrainedAxes: Int = 0x3
}

/**
 * `hkpAngFrictionConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngFrictionConstraintAtom`
 */
public object HkpAngFrictionConstraintAtom {
    public const val ByteSize: Int = 0xc

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val firstFrictionAxis: Int = 0x3

    /** `uint8` */
    public const val numFrictionAxes: Int = 0x4

    /** `float` */
    public const val maxFrictionTorque: Int = 0x8
}

/**
 * `hkpAngLimitConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngLimitConstraintAtom`
 */
public object HkpAngLimitConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val limitAxis: Int = 0x3

    /** `float` */
    public const val minAngle: Int = 0x4

    /** `float` */
    public const val maxAngle: Int = 0x8

    /** `float` */
    public const val angularLimitsTauFactor: Int = 0xc
}

/**
 * `hkpAngMotorConstraintAtom`, 0x14 bytes.
 *
 * `hkpConstraintAtom` -> `hkpAngMotorConstraintAtom`
 */
public object HkpAngMotorConstraintAtom {
    public const val ByteSize: Int = 0x14

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `bool` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val motorAxis: Int = 0x3

    /** `int16` */
    public const val initializedOffset: Int = 0x4

    /** `int16` */
    public const val previousTargetAngleOffset: Int = 0x6

    /** `int16` */
    public const val correspondingAngLimitSolverResultOffset: Int = 0x8

    /** `float` */
    public const val targetAngle: Int = 0xc

    /** `struct*` */
    public const val motor: Int = 0x10
}

/**
 * `hkpAngularDashpotAction`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpAngularDashpotAction`
 */
public object HkpAngularDashpotAction {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpBinaryAction` */
    public const val entityA: Int = 0x18

    /** `struct*`, from `hkpBinaryAction` */
    public const val entityB: Int = 0x1c

    /** `hkQuaternion` */
    public const val rotation: Int = 0x20

    /** `float` */
    public const val strength: Int = 0x30

    /** `float` */
    public const val damping: Int = 0x34
}

/**
 * `hkpArrayAction`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpArrayAction`
 */
public object HkpArrayAction {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `hkArray<ptr>` */
    public const val entities: Int = 0x18
}

/**
 * `hkpBallAndSocketConstraintData`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpBallAndSocketConstraintData`
 */
public object HkpBallAndSocketConstraintData {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpBallAndSocketConstraintDataAtoms`, 0x50 bytes.
 */
public object HkpBallAndSocketConstraintDataAtoms {
    public const val ByteSize: Int = 0x50

    /** `struct` */
    public const val pivots: Int = 0x0

    /** `struct` */
    public const val setupStabilization: Int = 0x30

    /** `struct` */
    public const val ballSocket: Int = 0x40
}

/**
 * `hkpBallGun`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpBallGun`
 */
public object HkpBallGun {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val keyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val listeners: Int = 0x14

    /** `float` */
    public const val bulletRadius: Int = 0x20

    /** `float` */
    public const val bulletVelocity: Int = 0x24

    /** `float` */
    public const val bulletMass: Int = 0x28

    /** `float` */
    public const val damageMultiplier: Int = 0x2c

    /** `int32` */
    public const val maxBulletsInWorld: Int = 0x30

    /** `hkVector4` */
    public const val bulletOffsetFromCenter: Int = 0x40

    /** `void*` */
    public const val addedBodies: Int = 0x50
}

/**
 * `hkpBallSocketChainDataConstraintInfo`, 0x20 bytes.
 */
public object HkpBallSocketChainDataConstraintInfo {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val pivotInA: Int = 0x0

    /** `hkVector4` */
    public const val pivotInB: Int = 0x10
}

/**
 * `hkpBallSocketConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpBallSocketConstraintAtom`
 */
public object HkpBallSocketConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `enum:uint8` */
    public const val solvingMethod: Int = 0x2

    /** `uint8` */
    public const val bodiesToNotify: Int = 0x3

    /** `uint8` */
    public const val velocityStabilizationFactor: Int = 0x4

    /** `float` */
    public const val maxImpulse: Int = 0x8

    /** `float` */
    public const val inertiaStabilizationFactor: Int = 0xc
}

/**
 * `hkpBinaryAction`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction`
 */
public object HkpBinaryAction {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val entityA: Int = 0x18

    /** `struct*` */
    public const val entityB: Int = 0x1c
}

/**
 * `hkpBoxMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpBoxMotion`
 */
public object HkpBoxMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpBoxShape`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpBoxShape`
 */
public object HkpBoxShape {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `hkVector4` */
    public const val halfExtents: Int = 0x20
}

/**
 * `hkpBreakableBody`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpBreakableBody`
 */
public object HkpBreakableBody {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpBreakableConstraintData`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpBreakableConstraintData`
 */
public object HkpBreakableConstraintData {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0xc

    /** `struct*` */
    public const val constraintData: Int = 0x18

    /** `uint16` */
    public const val childRuntimeSize: Int = 0x1c

    /** `uint16` */
    public const val childNumSolverResults: Int = 0x1e

    /** `float` */
    public const val solverResultLimit: Int = 0x20

    /** `bool` */
    public const val removeWhenBroken: Int = 0x24

    /** `bool` */
    public const val revertBackVelocityOnBreak: Int = 0x25
}

/**
 * `hkpBridgeAtoms`, 0xc bytes.
 */
public object HkpBridgeAtoms {
    public const val ByteSize: Int = 0xc

    /** `struct` */
    public const val bridgeAtom: Int = 0x0
}

/**
 * `hkpBridgeConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpBridgeConstraintAtom`
 */
public object HkpBridgeConstraintAtom {
    public const val ByteSize: Int = 0xc

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `void*` */
    public const val buildJacobianFunc: Int = 0x4

    /** `struct*` */
    public const val constraintData: Int = 0x8
}

/**
 * `hkpBroadPhaseHandle`, 0x4 bytes.
 */
public object HkpBroadPhaseHandle {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val id: Int = 0x0
}

/**
 * `hkpBvShape`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvShape`
 */
public object HkpBvShape {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `struct*` */
    public const val boundingVolumeShape: Int = 0x10

    /** `struct` */
    public const val childShape: Int = 0x14
}

/**
 * `hkpBvTreeShape`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvTreeShape`
 */
public object HkpBvTreeShape {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `enum:uint8` */
    public const val bvTreeType: Int = 0x10
}

/**
 * `hkpCachingShapePhantom`, 0x170 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom` -> `hkpCachingShapePhantom`
 */
public object HkpCachingShapePhantom {
    public const val ByteSize: Int = 0x170

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val overlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val phantomListeners: Int = 0x98

    /** `struct`, from `hkpShapePhantom` */
    public const val motionState: Int = 0xb0

    /** `hkArray<void>` */
    public const val collisionDetails: Int = 0x160

    /** `bool` */
    public const val orderDirty: Int = 0x16c
}

/**
 * `hkpCallbackConstraintMotor`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpCallbackConstraintMotor`
 */
public object HkpCallbackConstraintMotor {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val minForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val maxForce: Int = 0x10

    /** `void*` */
    public const val callbackFunc: Int = 0x14

    /** `enum:uint32` */
    public const val callbackType: Int = 0x18

    /** `hkUlong` */
    public const val userData0: Int = 0x1c

    /** `hkUlong` */
    public const val userData1: Int = 0x20

    /** `hkUlong` */
    public const val userData2: Int = 0x24
}

/**
 * `hkpCapsuleShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpCapsuleShape`
 */
public object HkpCapsuleShape {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `hkVector4` */
    public const val vertexA: Int = 0x20

    /** `hkVector4` */
    public const val vertexB: Int = 0x30
}

/**
 * `hkpCdBody`, 0x10 bytes.
 */
public object HkpCdBody {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val shape: Int = 0x0

    /** `uint32` */
    public const val shapeKey: Int = 0x4

    /** `void*` */
    public const val motion: Int = 0x8

    /** `struct*` */
    public const val parent: Int = 0xc
}

/**
 * `hkpCenterOfMassChangerModifierConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpCenterOfMassChangerModifierConstraintAtom`
 */
public object HkpCenterOfMassChangerModifierConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val childSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val pad: Int = 0x18

    /** `hkVector4` */
    public const val displacementA: Int = 0x20

    /** `hkVector4` */
    public const val displacementB: Int = 0x30
}

/**
 * `hkpCharacterControllerCinfo`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo`
 */
public object HkpCharacterControllerCinfo {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpCharacterMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpCharacterMotion`
 */
public object HkpCharacterMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpCharacterProxyCinfo`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo` -> `hkpCharacterProxyCinfo`
 */
public object HkpCharacterProxyCinfo {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkVector4` */
    public const val position: Int = 0x10

    /** `hkVector4` */
    public const val velocity: Int = 0x20

    /** `float` */
    public const val dynamicFriction: Int = 0x30

    /** `float` */
    public const val staticFriction: Int = 0x34

    /** `float` */
    public const val keepContactTolerance: Int = 0x38

    /** `hkVector4` */
    public const val up: Int = 0x40

    /** `float` */
    public const val extraUpStaticFriction: Int = 0x50

    /** `float` */
    public const val extraDownStaticFriction: Int = 0x54

    /** `struct*` */
    public const val shapePhantom: Int = 0x58

    /** `float` */
    public const val keepDistance: Int = 0x5c

    /** `float` */
    public const val contactAngleSensitivity: Int = 0x60

    /** `uint32` */
    public const val userPlanes: Int = 0x64

    /** `float` */
    public const val maxCharacterSpeedForSolver: Int = 0x68

    /** `float` */
    public const val characterStrength: Int = 0x6c

    /** `float` */
    public const val characterMass: Int = 0x70

    /** `float` */
    public const val maxSlope: Int = 0x74

    /** `float` */
    public const val penetrationRecoverySpeed: Int = 0x78

    /** `int32` */
    public const val maxCastIterations: Int = 0x7c

    /** `bool` */
    public const val refreshManifoldInCheckSupport: Int = 0x80
}

/**
 * `hkpCharacterRigidBodyCinfo`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCharacterControllerCinfo` -> `hkpCharacterRigidBodyCinfo`
 */
public object HkpCharacterRigidBodyCinfo {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32` */
    public const val collisionFilterInfo: Int = 0x8

    /** `struct*` */
    public const val shape: Int = 0xc

    /** `hkVector4` */
    public const val position: Int = 0x10

    /** `hkQuaternion` */
    public const val rotation: Int = 0x20

    /** `float` */
    public const val mass: Int = 0x30

    /** `float` */
    public const val friction: Int = 0x34

    /** `float` */
    public const val maxLinearVelocity: Int = 0x38

    /** `float` */
    public const val allowedPenetrationDepth: Int = 0x3c

    /** `hkVector4` */
    public const val up: Int = 0x40

    /** `float` */
    public const val maxSlope: Int = 0x50

    /** `float` */
    public const val maxForce: Int = 0x54

    /** `float` */
    public const val unweldingHeightOffsetFactor: Int = 0x58

    /** `float` */
    public const val maxSpeedForSimplexSolver: Int = 0x5c

    /** `float` */
    public const val supportDistance: Int = 0x60

    /** `float` */
    public const val hardSupportDistance: Int = 0x64

    /** `int32` */
    public const val vdbColor: Int = 0x68
}

/**
 * `hkpCogWheelConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpCogWheelConstraintAtom`
 */
public object HkpCogWheelConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `float` */
    public const val cogWheelRadiusA: Int = 0x4

    /** `float` */
    public const val cogWheelRadiusB: Int = 0x8

    /** `bool` */
    public const val isScrew: Int = 0xc

    /** `int8` */
    public const val memOffsetToInitialAngleOffset: Int = 0xd

    /** `int8` */
    public const val memOffsetToPrevAngle: Int = 0xe

    /** `int8` */
    public const val memOffsetToRevolutionCounter: Int = 0xf
}

/**
 * `hkpCogWheelConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpCogWheelConstraintData`
 */
public object HkpCogWheelConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpCogWheelConstraintDataAtoms`, 0xa0 bytes.
 */
public object HkpCogWheelConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val cogWheels: Int = 0x90
}

/**
 * `hkpCollidable`, 0x50 bytes.
 *
 * `hkpCdBody` -> `hkpCollidable`
 */
public object HkpCollidable {
    public const val ByteSize: Int = 0x50

    /** `struct*`, from `hkpCdBody` */
    public const val shape: Int = 0x0

    /** `uint32`, from `hkpCdBody` */
    public const val shapeKey: Int = 0x4

    /** `void*`, from `hkpCdBody` */
    public const val motion: Int = 0x8

    /** `struct*`, from `hkpCdBody` */
    public const val parent: Int = 0xc

    /** `int8` */
    public const val ownerOffset: Int = 0x10

    /** `uint8` */
    public const val forceCollideOntoPpu: Int = 0x11

    /** `uint16` */
    public const val shapeSizeOnSpu: Int = 0x12

    /** `struct` */
    public const val broadPhaseHandle: Int = 0x14

    /** `struct` */
    public const val boundingVolumeData: Int = 0x20

    /** `float` */
    public const val allowedPenetrationDepth: Int = 0x4c
}

/**
 * `hkpCollidableBoundingVolumeData`, 0x2c bytes.
 */
public object HkpCollidableBoundingVolumeData {
    public const val ByteSize: Int = 0x2c

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
    public const val padding: Int = 0x1f

    /** `uint16` */
    public const val numChildShapeAabbs: Int = 0x20

    /** `uint16` */
    public const val capacityChildShapeAabbs: Int = 0x22

    /** `void*` */
    public const val childShapeAabbs: Int = 0x24

    /** `void*` */
    public const val childShapeKeys: Int = 0x28
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
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]` */
    public const val prepad: Int = 0x18

    /** `enum:uint32` */
    public const val type: Int = 0x20

    /** `uint32[3]` */
    public const val postpad: Int = 0x24
}

/**
 * `hkpCollisionFilterList`, 0x3c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpCollisionFilterList`
 */
public object HkpCollisionFilterList {
    public const val ByteSize: Int = 0x3c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `hkArray<ptr>` */
    public const val collisionFilters: Int = 0x30
}

/**
 * `hkpCompressedMeshShape`, 0xe0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpCompressedMeshShape`
 */
public object HkpCompressedMeshShape {
    public const val ByteSize: Int = 0xe0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `int32` */
    public const val bitsPerIndex: Int = 0x18

    /** `int32` */
    public const val bitsPerWIndex: Int = 0x1c

    /** `int32` */
    public const val wIndexMask: Int = 0x20

    /** `int32` */
    public const val indexMask: Int = 0x24

    /** `float` */
    public const val radius: Int = 0x28

    /** `enum:uint8` */
    public const val weldingType: Int = 0x2c

    /** `enum:uint8` */
    public const val materialType: Int = 0x2d

    /** `hkArray<uint32>` */
    public const val materials: Int = 0x30

    /** `hkArray<uint16>` */
    public const val materials16: Int = 0x3c

    /** `hkArray<uint8>` */
    public const val materials8: Int = 0x48

    /** `hkArray<hkQsTransform>` */
    public const val transforms: Int = 0x54

    /** `hkArray<hkVector4>` */
    public const val bigVertices: Int = 0x60

    /** `hkArray<struct>` */
    public const val bigTriangles: Int = 0x6c

    /** `hkArray<struct>` */
    public const val chunks: Int = 0x78

    /** `hkArray<struct>` */
    public const val convexPieces: Int = 0x84

    /** `float` */
    public const val error: Int = 0x90

    /** `struct` */
    public const val bounds: Int = 0xa0

    /** `uint32` */
    public const val defaultCollisionFilterInfo: Int = 0xc0

    /** `void*` */
    public const val meshMaterials: Int = 0xc4

    /** `uint16` */
    public const val materialStriding: Int = 0xc8

    /** `uint16` */
    public const val numMaterials: Int = 0xca

    /** `hkArray<struct>` */
    public const val namedMaterials: Int = 0xcc
}

/**
 * `hkpCompressedMeshShapeBigTriangle`, 0x10 bytes.
 */
public object HkpCompressedMeshShapeBigTriangle {
    public const val ByteSize: Int = 0x10

    /** `uint16` */
    public const val a: Int = 0x0

    /** `uint16` */
    public const val b: Int = 0x2

    /** `uint16` */
    public const val unnamed0x4: Int = 0x4

    /** `uint32` */
    public const val material: Int = 0x8

    /** `uint16` */
    public const val weldingInfo: Int = 0xc

    /** `uint16` */
    public const val transformIndex: Int = 0xe
}

/**
 * `hkpCompressedMeshShapeChunk`, 0x50 bytes.
 */
public object HkpCompressedMeshShapeChunk {
    public const val ByteSize: Int = 0x50

    /** `hkVector4` */
    public const val offset: Int = 0x0

    /** `hkArray<uint16>` */
    public const val vertices: Int = 0x10

    /** `hkArray<uint16>` */
    public const val indices: Int = 0x1c

    /** `hkArray<uint16>` */
    public const val stripLengths: Int = 0x28

    /** `hkArray<uint16>` */
    public const val weldingInfo: Int = 0x34

    /** `uint32` */
    public const val materialInfo: Int = 0x40

    /** `uint16` */
    public const val reference: Int = 0x44

    /** `uint16` */
    public const val transformIndex: Int = 0x46
}

/**
 * `hkpCompressedMeshShapeConvexPiece`, 0x40 bytes.
 */
public object HkpCompressedMeshShapeConvexPiece {
    public const val ByteSize: Int = 0x40

    /** `hkVector4` */
    public const val offset: Int = 0x0

    /** `hkArray<uint16>` */
    public const val vertices: Int = 0x10

    /** `hkArray<uint8>` */
    public const val faceVertices: Int = 0x1c

    /** `hkArray<uint16>` */
    public const val faceOffsets: Int = 0x28

    /** `uint16` */
    public const val reference: Int = 0x34

    /** `uint16` */
    public const val transformIndex: Int = 0x36
}

/**
 * `hkpConeLimitConstraintAtom`, 0x14 bytes.
 *
 * `hkpConstraintAtom` -> `hkpConeLimitConstraintAtom`
 */
public object HkpConeLimitConstraintAtom {
    public const val ByteSize: Int = 0x14

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val twistAxisInA: Int = 0x3

    /** `uint8` */
    public const val refAxisInB: Int = 0x4

    /** `enum:uint8` */
    public const val angleMeasurementMode: Int = 0x5

    /** `uint8` */
    public const val memOffsetToAngleOffset: Int = 0x6

    /** `float` */
    public const val minAngle: Int = 0x8

    /** `float` */
    public const val maxAngle: Int = 0xc

    /** `float` */
    public const val angularLimitsTauFactor: Int = 0x10
}

/**
 * `hkpConstrainedSystemFilter`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpConstrainedSystemFilter`
 */
public object HkpConstrainedSystemFilter {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `struct*` */
    public const val otherFilter: Int = 0x34
}

/**
 * `hkpConstraintAtom`, 0x2 bytes.
 */
public object HkpConstraintAtom {
    public const val ByteSize: Int = 0x2

    /** `enum:uint16` */
    public const val type: Int = 0x0
}

/**
 * `hkpConstraintChainData`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData`
 */
public object HkpConstraintChainData {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8
}

/**
 * `hkpConstraintChainInstanceAction`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpConstraintChainInstanceAction`
 */
public object HkpConstraintChainInstanceAction {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val constraintInstance: Int = 0x18
}

/**
 * `hkpConstraintCollisionFilter`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpPairCollisionFilter` -> `hkpConstraintCollisionFilter`
 */
public object HkpConstraintCollisionFilter {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `struct`, from `hkpPairCollisionFilter` */
    public const val disabledPairs: Int = 0x30

    /** `struct*`, from `hkpPairCollisionFilter` */
    public const val childFilter: Int = 0x3c
}

/**
 * `hkpConstraintData`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData`
 */
public object HkpConstraintData {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong` */
    public const val userData: Int = 0x8
}

/**
 * `hkpConstraintInstance`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintInstance`
 */
public object HkpConstraintInstance {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*` */
    public const val owner: Int = 0x8

    /** `struct*` */
    public const val data: Int = 0xc

    /** `struct*` */
    public const val constraintModifiers: Int = 0x10

    /** `struct*[2]` */
    public const val entities: Int = 0x14

    /** `enum:uint8` */
    public const val priority: Int = 0x1c

    /** `bool` */
    public const val wantRuntime: Int = 0x1d

    /** `enum:uint8` */
    public const val destructionRemapInfo: Int = 0x1e

    /** `struct` */
    public const val listeners: Int = 0x20

    /** `hkStringPtr` */
    public const val unnamed0x28: Int = 0x28

    /** `hkUlong` */
    public const val userData: Int = 0x2c

    /** `void*` */
    public const val internal: Int = 0x30

    /** `uint32` */
    public const val uid: Int = 0x34
}

/**
 * `hkpConstraintInstanceSmallArraySerializeOverrideType`, 0x8 bytes.
 */
public object HkpConstraintInstanceSmallArraySerializeOverrideType {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val data: Int = 0x0

    /** `uint16` */
    public const val size: Int = 0x4

    /** `uint16` */
    public const val capacityAndFlags: Int = 0x6
}

/**
 * `hkpConstraintMotor`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor`
 */
public object HkpConstraintMotor {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8` */
    public const val type: Int = 0x8
}

/**
 * `hkpConvexListFilter`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexListFilter`
 */
public object HkpConvexListFilter {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpConvexListShape`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexListShape`
 */
public object HkpConvexListShape {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `float` */
    public const val minDistanceToUseConvexHullForGetClosestPoints: Int = 0x18

    /** `hkVector4` */
    public const val aabbHalfExtents: Int = 0x20

    /** `hkVector4` */
    public const val aabbCenter: Int = 0x30

    /** `bool` */
    public const val useCachedAabb: Int = 0x40

    /** `hkArray<ptr>` */
    public const val childShapes: Int = 0x44
}

/**
 * `hkpConvexPieceMeshShape`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpConvexPieceMeshShape`
 */
public object HkpConvexPieceMeshShape {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `struct*` */
    public const val convexPieceStream: Int = 0x18

    /** `struct*` */
    public const val displayMesh: Int = 0x1c

    /** `float` */
    public const val radius: Int = 0x20
}

/**
 * `hkpConvexPieceStreamData`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexPieceStreamData`
 */
public object HkpConvexPieceStreamData {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<uint32>` */
    public const val convexPieceStream: Int = 0x8

    /** `hkArray<uint32>` */
    public const val convexPieceOffsets: Int = 0x14

    /** `hkArray<uint32>` */
    public const val convexPieceSingleTriangles: Int = 0x20
}

/**
 * `hkpConvexShape`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape`
 */
public object HkpConvexShape {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float` */
    public const val radius: Int = 0x10
}

/**
 * `hkpConvexTransformShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase` -> `hkpConvexTransformShape`
 */
public object HkpConvexTransformShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `struct`, from `hkpConvexTransformShapeBase` */
    public const val childShape: Int = 0x14

    /** `int32`, from `hkpConvexTransformShapeBase` */
    public const val childShapeSize: Int = 0x1c

    /** `hkTransform` */
    public const val transform: Int = 0x20
}

/**
 * `hkpConvexTransformShapeBase`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase`
 */
public object HkpConvexTransformShapeBase {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `struct` */
    public const val childShape: Int = 0x14

    /** `int32` */
    public const val childShapeSize: Int = 0x1c
}

/**
 * `hkpConvexTranslateShape`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexTransformShapeBase` -> `hkpConvexTranslateShape`
 */
public object HkpConvexTranslateShape {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `struct`, from `hkpConvexTransformShapeBase` */
    public const val childShape: Int = 0x14

    /** `int32`, from `hkpConvexTransformShapeBase` */
    public const val childShapeSize: Int = 0x1c

    /** `hkVector4` */
    public const val translation: Int = 0x20
}

/**
 * `hkpConvexVerticesConnectivity`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexVerticesConnectivity`
 */
public object HkpConvexVerticesConnectivity {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<uint16>` */
    public const val vertexIndices: Int = 0x8

    /** `hkArray<uint8>` */
    public const val numVerticesPerFace: Int = 0x14
}

/**
 * `hkpConvexVerticesShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpConvexVerticesShape`
 */
public object HkpConvexVerticesShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `hkVector4` */
    public const val aabbHalfExtents: Int = 0x20

    /** `hkVector4` */
    public const val aabbCenter: Int = 0x30

    /** `hkArray<struct>` */
    public const val rotatedVertices: Int = 0x40

    /** `int32` */
    public const val numVertices: Int = 0x4c

    /** `void*` */
    public const val externalObject: Int = 0x50

    /** `void*` */
    public const val getFaceNormals: Int = 0x54

    /** `hkArray<hkVector4>` */
    public const val planeEquations: Int = 0x58

    /** `struct*` */
    public const val connectivity: Int = 0x64
}

/**
 * `hkpConvexVerticesShapeFourVectors`, 0x30 bytes.
 */
public object HkpConvexVerticesShapeFourVectors {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val x: Int = 0x0

    /** `hkVector4` */
    public const val y: Int = 0x10

    /** `hkVector4` */
    public const val z: Int = 0x20
}

/**
 * `hkpCylinderShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpCylinderShape`
 */
public object HkpCylinderShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `float` */
    public const val cylRadius: Int = 0x14

    /** `float` */
    public const val cylBaseRadiusFactorForHeightFieldCollisions: Int = 0x18

    /** `hkVector4` */
    public const val vertexA: Int = 0x20

    /** `hkVector4` */
    public const val vertexB: Int = 0x30

    /** `hkVector4` */
    public const val perpendicular1: Int = 0x40

    /** `hkVector4` */
    public const val perpendicular2: Int = 0x50
}

/**
 * `hkpDashpotAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpDashpotAction`
 */
public object HkpDashpotAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpBinaryAction` */
    public const val entityA: Int = 0x18

    /** `struct*`, from `hkpBinaryAction` */
    public const val entityB: Int = 0x1c

    /** `hkVector4[2]` */
    public const val point: Int = 0x20

    /** `float` */
    public const val strength: Int = 0x40

    /** `float` */
    public const val damping: Int = 0x44

    /** `hkVector4` */
    public const val impulse: Int = 0x50
}

/**
 * `hkpDefaultConvexListFilter`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConvexListFilter` -> `hkpDefaultConvexListFilter`
 */
public object HkpDefaultConvexListFilter {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpDefaultToiResourceMgr`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDefaultToiResourceMgr`
 */
public object HkpDefaultToiResourceMgr {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpDefaultWorldMemoryWatchDog`, 0xc bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkWorldMemoryAvailableWatchDog` -> `hkpDefaultWorldMemoryWatchDog`
 */
public object HkpDefaultWorldMemoryWatchDog {
    public const val ByteSize: Int = 0xc

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `int32` */
    public const val freeHeapMemoryRequested: Int = 0x8
}

/**
 * `hkpDisableEntityCollisionFilter`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpDisableEntityCollisionFilter`
 */
public object HkpDisableEntityCollisionFilter {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `hkArray<ptr>` */
    public const val disabledEntities: Int = 0x34
}

/**
 * `hkpDisplayBindingData`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingData`
 */
public object HkpDisplayBindingData {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val rigidBodyBindings: Int = 0x8

    /** `hkArray<ptr>` */
    public const val physicsSystemBindings: Int = 0x14
}

/**
 * `hkpDisplayBindingDataPhysicsSystem`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingDataPhysicsSystem`
 */
public object HkpDisplayBindingDataPhysicsSystem {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val bindings: Int = 0x8

    /** `struct*` */
    public const val system: Int = 0x14
}

/**
 * `hkpDisplayBindingDataRigidBody`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpDisplayBindingDataRigidBody`
 */
public object HkpDisplayBindingDataRigidBody {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val rigidBody: Int = 0x8

    /** `struct*` */
    public const val displayObjectPtr: Int = 0xc

    /** `hkMatrix4` */
    public const val rigidBodyFromDisplayObjectTransform: Int = 0x10
}

/**
 * `hkpEntity`, 0x220 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpEntity`
 */
public object HkpEntity {
    public const val ByteSize: Int = 0x220

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `struct` */
    public const val material: Int = 0x8c

    /** `void*` */
    public const val limitContactImpulseUtilAndFlag: Int = 0x98

    /** `float` */
    public const val damageMultiplier: Int = 0x9c

    /** `void*` */
    public const val breakableBody: Int = 0xa0

    /** `uint32` */
    public const val solverData: Int = 0xa4

    /** `uint16` */
    public const val storageIndex: Int = 0xa8

    /** `uint16` */
    public const val contactPointCallbackDelay: Int = 0xaa

    /** `struct` */
    public const val constraintsMaster: Int = 0xac

    /** `hkArray<ptr>` */
    public const val constraintsSlave: Int = 0xb4

    /** `hkArray<uint8>` */
    public const val constraintRuntime: Int = 0xc0

    /** `void*` */
    public const val simulationIsland: Int = 0xcc

    /** `int8` */
    public const val autoRemoveLevel: Int = 0xd0

    /** `uint8` */
    public const val numShapeKeysInContactPointProperties: Int = 0xd1

    /** `uint8` */
    public const val responseModifierFlags: Int = 0xd2

    /** `uint32` */
    public const val uid: Int = 0xd4

    /** `struct` */
    public const val spuCollisionCallback: Int = 0xd8

    /** `struct` */
    public const val motion: Int = 0xe0

    /** `struct` */
    public const val contactListeners: Int = 0x200

    /** `struct` */
    public const val actions: Int = 0x208

    /** `struct*` */
    public const val localFrame: Int = 0x210

    /** `struct*` */
    public const val extendedListeners: Int = 0x214

    /** `uint32` */
    public const val npData: Int = 0x218
}

/**
 * `hkpEntityExtendedListeners`, 0x10 bytes.
 */
public object HkpEntityExtendedListeners {
    public const val ByteSize: Int = 0x10

    /** `struct` */
    public const val activationListeners: Int = 0x0

    /** `struct` */
    public const val entityListeners: Int = 0x8
}

/**
 * `hkpEntitySmallArraySerializeOverrideType`, 0x8 bytes.
 */
public object HkpEntitySmallArraySerializeOverrideType {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val data: Int = 0x0

    /** `uint16` */
    public const val size: Int = 0x4

    /** `uint16` */
    public const val capacityAndFlags: Int = 0x6
}

/**
 * `hkpEntitySpuCollisionCallback`, 0x8 bytes.
 */
public object HkpEntitySpuCollisionCallback {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val util: Int = 0x0

    /** `uint16` */
    public const val capacity: Int = 0x4

    /** `uint8` */
    public const val eventFilter: Int = 0x6

    /** `uint8` */
    public const val userFilter: Int = 0x7
}

/**
 * `hkpExtendedMeshShape`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape`
 */
public object HkpExtendedMeshShape {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `struct` */
    public const val embeddedTrianglesSubpart: Int = 0x20

    /** `hkVector4` */
    public const val aabbHalfExtents: Int = 0x90

    /** `hkVector4` */
    public const val aabbCenter: Int = 0xa0

    /** `void*` */
    public const val materialClass: Int = 0xb0

    /** `int32` */
    public const val numBitsForSubpartIndex: Int = 0xb4

    /** `hkArray<struct>` */
    public const val trianglesSubparts: Int = 0xb8

    /** `hkArray<struct>` */
    public const val shapesSubparts: Int = 0xc4

    /** `hkArray<uint16>` */
    public const val weldingInfo: Int = 0xd0

    /** `enum:uint8` */
    public const val weldingType: Int = 0xdc

    /** `uint32` */
    public const val defaultCollisionFilterInfo: Int = 0xe0

    /** `int32` */
    public const val cachedNumChildShapes: Int = 0xe4

    /** `float` */
    public const val triangleRadius: Int = 0xe8

    /** `int32` */
    public const val padding: Int = 0xec
}

/**
 * `hkpExtendedMeshShapeShapesSubpart`, 0x40 bytes.
 *
 * `hkpExtendedMeshShapeSubpart` -> `hkpExtendedMeshShapeShapesSubpart`
 */
public object HkpExtendedMeshShapeShapesSubpart {
    public const val ByteSize: Int = 0x40

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val type: Int = 0x0

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialIndexStridingType: Int = 0x1

    /** `int16`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialStriding: Int = 0x2

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialIndexBase: Int = 0x4

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialIndexStriding: Int = 0x8

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val numMaterials: Int = 0xa

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialBase: Int = 0xc

    /** `hkUlong`, from `hkpExtendedMeshShapeSubpart` */
    public const val userData: Int = 0x10

    /** `hkArray<ptr>` */
    public const val childShapes: Int = 0x14

    /** `hkQuaternion` */
    public const val rotation: Int = 0x20

    /** `hkVector4` */
    public const val translation: Int = 0x30
}

/**
 * `hkpExtendedMeshShapeSubpart`, 0x14 bytes.
 */
public object HkpExtendedMeshShapeSubpart {
    public const val ByteSize: Int = 0x14

    /** `enum:int8` */
    public const val type: Int = 0x0

    /** `enum:int8` */
    public const val materialIndexStridingType: Int = 0x1

    /** `int16` */
    public const val materialStriding: Int = 0x2

    /** `void*` */
    public const val materialIndexBase: Int = 0x4

    /** `uint16` */
    public const val materialIndexStriding: Int = 0x8

    /** `uint16` */
    public const val numMaterials: Int = 0xa

    /** `void*` */
    public const val materialBase: Int = 0xc

    /** `hkUlong` */
    public const val userData: Int = 0x10
}

/**
 * `hkpExtendedMeshShapeTrianglesSubpart`, 0x70 bytes.
 *
 * `hkpExtendedMeshShapeSubpart` -> `hkpExtendedMeshShapeTrianglesSubpart`
 */
public object HkpExtendedMeshShapeTrianglesSubpart {
    public const val ByteSize: Int = 0x70

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val type: Int = 0x0

    /** `enum:int8`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialIndexStridingType: Int = 0x1

    /** `int16`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialStriding: Int = 0x2

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialIndexBase: Int = 0x4

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialIndexStriding: Int = 0x8

    /** `uint16`, from `hkpExtendedMeshShapeSubpart` */
    public const val numMaterials: Int = 0xa

    /** `void*`, from `hkpExtendedMeshShapeSubpart` */
    public const val materialBase: Int = 0xc

    /** `hkUlong`, from `hkpExtendedMeshShapeSubpart` */
    public const val userData: Int = 0x10

    /** `int32` */
    public const val numTriangleShapes: Int = 0x14

    /** `void*` */
    public const val vertexBase: Int = 0x18

    /** `int32` */
    public const val numVertices: Int = 0x1c

    /** `void*` */
    public const val indexBase: Int = 0x20

    /** `uint16` */
    public const val vertexStriding: Int = 0x24

    /** `int32` */
    public const val triangleOffset: Int = 0x28

    /** `uint16` */
    public const val indexStriding: Int = 0x2c

    /** `enum:int8` */
    public const val stridingType: Int = 0x2e

    /** `int8` */
    public const val flipAlternateTriangles: Int = 0x2f

    /** `hkVector4` */
    public const val extrusion: Int = 0x30

    /** `hkQsTransform` */
    public const val transform: Int = 0x40
}

/**
 * `hkpFastMeshShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpFastMeshShape`
 */
public object HkpFastMeshShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `hkVector4`, from `hkpMeshShape` */
    public const val scaling: Int = 0x20

    /** `int32`, from `hkpMeshShape` */
    public const val numBitsForSubpartIndex: Int = 0x30

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val subparts: Int = 0x34

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val weldingInfo: Int = 0x40

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val weldingType: Int = 0x4c

    /** `float`, from `hkpMeshShape` */
    public const val radius: Int = 0x50

    /** `int32[3]`, from `hkpMeshShape` */
    public const val pad: Int = 0x54
}

/**
 * `hkpFirstPersonGun`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun`
 */
public object HkpFirstPersonGun {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8` */
    public const val type: Int = 0x8

    /** `hkStringPtr` */
    public const val unnamed0xc: Int = 0xc

    /** `enum:uint8` */
    public const val keyboardKey: Int = 0x10

    /** `hkArray<ptr>` */
    public const val listeners: Int = 0x14
}

/**
 * `hkpFixedRigidMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion` -> `hkpFixedRigidMotion`
 */
public object HkpFixedRigidMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpGenericConstraintData`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpGenericConstraintData`
 */
public object HkpGenericConstraintData {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0xc

    /** `struct` */
    public const val scheme: Int = 0x18
}

/**
 * `hkpGenericConstraintDataScheme`, 0x40 bytes.
 */
public object HkpGenericConstraintDataScheme {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val info: Int = 0x0

    /** `hkArray<hkVector4>` */
    public const val data: Int = 0x10

    /** `hkArray<int32>` */
    public const val commands: Int = 0x1c

    /** `hkArray<ptr>` */
    public const val modifiers: Int = 0x28

    /** `hkArray<ptr>` */
    public const val motors: Int = 0x34
}

/**
 * `hkpGenericConstraintDataSchemeConstraintInfo`, 0x10 bytes.
 */
public object HkpGenericConstraintDataSchemeConstraintInfo {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val maxSizeOfSchema: Int = 0x0

    /** `int32` */
    public const val sizeOfSchemas: Int = 0x4

    /** `int32` */
    public const val numSolverResults: Int = 0x8

    /** `int32` */
    public const val numSolverElemTemps: Int = 0xc
}

/**
 * `hkpGravityGun`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpGravityGun`
 */
public object HkpGravityGun {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val keyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val listeners: Int = 0x14

    /** `hkArray<ptr>` */
    public const val grabbedBodies: Int = 0x20

    /** `int32` */
    public const val maxNumObjectsPicked: Int = 0x2c

    /** `float` */
    public const val maxMassOfObjectPicked: Int = 0x30

    /** `float` */
    public const val maxDistOfObjectPicked: Int = 0x34

    /** `float` */
    public const val impulseAppliedWhenObjectNotPicked: Int = 0x38

    /** `float` */
    public const val throwVelocity: Int = 0x3c

    /** `hkVector4` */
    public const val capturedObjectPosition: Int = 0x40

    /** `hkVector4` */
    public const val capturedObjectsOffset: Int = 0x50
}

/**
 * `hkpGroupCollisionFilter`, 0xb4 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpGroupCollisionFilter`
 */
public object HkpGroupCollisionFilter {
    public const val ByteSize: Int = 0xb4

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `bool` */
    public const val noGroupCollisionEnabled: Int = 0x30

    /** `uint32[32]` */
    public const val collisionGroups: Int = 0x34
}

/**
 * `hkpGroupFilter`, 0x100 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpGroupFilter`
 */
public object HkpGroupFilter {
    public const val ByteSize: Int = 0x100

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `int32` */
    public const val nextFreeSystemGroup: Int = 0x30

    /** `uint32[32]` */
    public const val collisionLookupTable: Int = 0x34

    /** `hkVector4[4]` */
    public const val pad256: Int = 0xc0
}

/**
 * `hkpHingeConstraintData`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpHingeConstraintData`
 */
public object HkpHingeConstraintData {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpHingeConstraintDataAtoms`, 0xc0 bytes.
 */
public object HkpHingeConstraintDataAtoms {
    public const val ByteSize: Int = 0xc0

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val setupStabilization: Int = 0x90

    /** `struct` */
    public const val `2dAng`: Int = 0xa0

    /** `struct` */
    public const val ballSocket: Int = 0xa4
}

/**
 * `hkpHingeLimitsData`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpHingeLimitsData`
 */
public object HkpHingeLimitsData {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpHingeLimitsDataAtoms`, 0x90 bytes.
 */
public object HkpHingeLimitsDataAtoms {
    public const val ByteSize: Int = 0x90

    /** `struct` */
    public const val rotations: Int = 0x0

    /** `struct` */
    public const val angLimit: Int = 0x70

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
    public const val type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val childSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val pad: Int = 0x18
}

/**
 * `hkpKeyframedRigidMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion`
 */
public object HkpKeyframedRigidMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpLimitedForceConstraintMotor`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor`
 */
public object HkpLimitedForceConstraintMotor {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val type: Int = 0x8

    /** `float` */
    public const val minForce: Int = 0xc

    /** `float` */
    public const val maxForce: Int = 0x10
}

/**
 * `hkpLimitedHingeConstraintData`, 0x100 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpLimitedHingeConstraintData`
 */
public object HkpLimitedHingeConstraintData {
    public const val ByteSize: Int = 0x100

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpLimitedHingeConstraintDataAtoms`, 0xf0 bytes.
 */
public object HkpLimitedHingeConstraintDataAtoms {
    public const val ByteSize: Int = 0xf0

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val setupStabilization: Int = 0x90

    /** `struct` */
    public const val angMotor: Int = 0xa0

    /** `struct` */
    public const val angFriction: Int = 0xb4

    /** `struct` */
    public const val angLimit: Int = 0xc0

    /** `struct` */
    public const val `2dAng`: Int = 0xd0

    /** `struct` */
    public const val ballSocket: Int = 0xd4
}

/**
 * `hkpLinConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinConstraintAtom`
 */
public object HkpLinConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val axisIndex: Int = 0x2
}

/**
 * `hkpLinFrictionConstraintAtom`, 0x8 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinFrictionConstraintAtom`
 */
public object HkpLinFrictionConstraintAtom {
    public const val ByteSize: Int = 0x8

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val frictionAxis: Int = 0x3

    /** `float` */
    public const val maxFrictionForce: Int = 0x4
}

/**
 * `hkpLinLimitConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinLimitConstraintAtom`
 */
public object HkpLinLimitConstraintAtom {
    public const val ByteSize: Int = 0xc

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val axisIndex: Int = 0x2

    /** `float` */
    public const val min: Int = 0x4

    /** `float` */
    public const val max: Int = 0x8
}

/**
 * `hkpLinMotorConstraintAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinMotorConstraintAtom`
 */
public object HkpLinMotorConstraintAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `bool` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val motorAxis: Int = 0x3

    /** `int16` */
    public const val initializedOffset: Int = 0x4

    /** `int16` */
    public const val previousTargetPositionOffset: Int = 0x6

    /** `float` */
    public const val targetPosition: Int = 0x8

    /** `struct*` */
    public const val motor: Int = 0xc
}

/**
 * `hkpLinSoftConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpLinSoftConstraintAtom`
 */
public object HkpLinSoftConstraintAtom {
    public const val ByteSize: Int = 0xc

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val axisIndex: Int = 0x2

    /** `float` */
    public const val tau: Int = 0x4

    /** `float` */
    public const val damping: Int = 0x8
}

/**
 * `hkpLinearParametricCurve`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpParametricCurve` -> `hkpLinearParametricCurve`
 */
public object HkpLinearParametricCurve {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `float` */
    public const val smoothingFactor: Int = 0x8

    /** `bool` */
    public const val closedLoop: Int = 0xc

    /** `hkVector4` */
    public const val dirNotParallelToTangentAlongWholePath: Int = 0x10

    /** `hkArray<hkVector4>` */
    public const val points: Int = 0x20

    /** `hkArray<float>` */
    public const val distance: Int = 0x2c
}

/**
 * `hkpLinkedCollidable`, 0x5c bytes.
 *
 * `hkpCdBody` -> `hkpCollidable` -> `hkpLinkedCollidable`
 */
public object HkpLinkedCollidable {
    public const val ByteSize: Int = 0x5c

    /** `struct*`, from `hkpCdBody` */
    public const val shape: Int = 0x0

    /** `uint32`, from `hkpCdBody` */
    public const val shapeKey: Int = 0x4

    /** `void*`, from `hkpCdBody` */
    public const val motion: Int = 0x8

    /** `struct*`, from `hkpCdBody` */
    public const val parent: Int = 0xc

    /** `int8`, from `hkpCollidable` */
    public const val ownerOffset: Int = 0x10

    /** `uint8`, from `hkpCollidable` */
    public const val forceCollideOntoPpu: Int = 0x11

    /** `uint16`, from `hkpCollidable` */
    public const val shapeSizeOnSpu: Int = 0x12

    /** `struct`, from `hkpCollidable` */
    public const val broadPhaseHandle: Int = 0x14

    /** `struct`, from `hkpCollidable` */
    public const val boundingVolumeData: Int = 0x20

    /** `float`, from `hkpCollidable` */
    public const val allowedPenetrationDepth: Int = 0x4c

    /** `hkArray<void>` */
    public const val collisionEntries: Int = 0x50
}

/**
 * `hkpListShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpListShape`
 */
public object HkpListShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `hkArray<struct>` */
    public const val childInfo: Int = 0x18

    /** `uint16` */
    public const val flags: Int = 0x24

    /** `uint16` */
    public const val numDisabledChildren: Int = 0x26

    /** `hkVector4` */
    public const val aabbHalfExtents: Int = 0x30

    /** `hkVector4` */
    public const val aabbCenter: Int = 0x40

    /** `uint32[8]` */
    public const val enabledChildren: Int = 0x50
}

/**
 * `hkpListShapeChildInfo`, 0x10 bytes.
 */
public object HkpListShapeChildInfo {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val shape: Int = 0x0

    /** `uint32` */
    public const val collisionFilterInfo: Int = 0x4

    /** `int32` */
    public const val shapeSize: Int = 0x8

    /** `int32` */
    public const val numChildShapes: Int = 0xc
}

/**
 * `hkpMalleableConstraintData`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpMalleableConstraintData`
 */
public object HkpMalleableConstraintData {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct*` */
    public const val constraintData: Int = 0xc

    /** `struct` */
    public const val atoms: Int = 0x10

    /** `float` */
    public const val strength: Int = 0x1c
}

/**
 * `hkpMassChangerModifierConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpMassChangerModifierConstraintAtom`
 */
public object HkpMassChangerModifierConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val childSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val pad: Int = 0x18

    /** `hkVector4` */
    public const val factorA: Int = 0x20

    /** `hkVector4` */
    public const val factorB: Int = 0x30
}

/**
 * `hkpMassProperties`, 0x50 bytes.
 */
public object HkpMassProperties {
    public const val ByteSize: Int = 0x50

    /** `float` */
    public const val volume: Int = 0x0

    /** `float` */
    public const val mass: Int = 0x4

    /** `hkVector4` */
    public const val centerOfMass: Int = 0x10

    /** `hkMatrix3` */
    public const val inertiaTensor: Int = 0x20
}

/**
 * `hkpMaterial`, 0xc bytes.
 */
public object HkpMaterial {
    public const val ByteSize: Int = 0xc

    /** `enum:int8` */
    public const val responseType: Int = 0x0

    /** `hkHalf` */
    public const val rollingFrictionMultiplier: Int = 0x2

    /** `float` */
    public const val friction: Int = 0x4

    /** `float` */
    public const val restitution: Int = 0x8
}

/**
 * `hkpMaxSizeMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpKeyframedRigidMotion` -> `hkpMaxSizeMotion`
 */
public object HkpMaxSizeMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpMeshMaterial`, 0x4 bytes.
 */
public object HkpMeshMaterial {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val filterInfo: Int = 0x0
}

/**
 * `hkpMeshShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape`
 */
public object HkpMeshShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `hkVector4` */
    public const val scaling: Int = 0x20

    /** `int32` */
    public const val numBitsForSubpartIndex: Int = 0x30

    /** `hkArray<struct>` */
    public const val subparts: Int = 0x34

    /** `hkArray<uint16>` */
    public const val weldingInfo: Int = 0x40

    /** `enum:uint8` */
    public const val weldingType: Int = 0x4c

    /** `float` */
    public const val radius: Int = 0x50

    /** `int32[3]` */
    public const val pad: Int = 0x54
}

/**
 * `hkpMeshShapeSubpart`, 0x38 bytes.
 */
public object HkpMeshShapeSubpart {
    public const val ByteSize: Int = 0x38

    /** `void*` */
    public const val vertexBase: Int = 0x0

    /** `int32` */
    public const val vertexStriding: Int = 0x4

    /** `int32` */
    public const val numVertices: Int = 0x8

    /** `void*` */
    public const val indexBase: Int = 0xc

    /** `enum:int8` */
    public const val stridingType: Int = 0x10

    /** `enum:int8` */
    public const val materialIndexStridingType: Int = 0x11

    /** `int32` */
    public const val indexStriding: Int = 0x14

    /** `int32` */
    public const val flipAlternateTriangles: Int = 0x18

    /** `int32` */
    public const val numTriangles: Int = 0x1c

    /** `void*` */
    public const val materialIndexBase: Int = 0x20

    /** `int32` */
    public const val materialIndexStriding: Int = 0x24

    /** `void*` */
    public const val materialBase: Int = 0x28

    /** `int32` */
    public const val materialStriding: Int = 0x2c

    /** `int32` */
    public const val numMaterials: Int = 0x30

    /** `int32` */
    public const val triangleOffset: Int = 0x34
}

/**
 * `hkpModifierConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom`
 */
public object HkpModifierConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16` */
    public const val childSize: Int = 0x12

    /** `struct*` */
    public const val child: Int = 0x14

    /** `uint32[2]` */
    public const val pad: Int = 0x18
}

/**
 * `hkpMoppBvTreeShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpBvTreeShape` -> `hkMoppBvTreeShapeBase` -> `hkpMoppBvTreeShape`
 */
public object HkpMoppBvTreeShape {
    public const val ByteSize: Int = 0x40

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

    /** `struct*`, from `hkMoppBvTreeShapeBase` */
    public const val code: Int = 0x14

    /** `void*`, from `hkMoppBvTreeShapeBase` */
    public const val moppData: Int = 0x18

    /** `uint32`, from `hkMoppBvTreeShapeBase` */
    public const val moppDataSize: Int = 0x1c

    /** `hkVector4`, from `hkMoppBvTreeShapeBase` */
    public const val codeInfoCopy: Int = 0x20

    /** `struct` */
    public const val child: Int = 0x30

    /** `int32` */
    public const val childSize: Int = 0x38
}

/**
 * `hkpMoppCode`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMoppCode`
 */
public object HkpMoppCode {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct` */
    public const val info: Int = 0x10

    /** `hkArray<uint8>` */
    public const val data: Int = 0x20

    /** `enum:int8` */
    public const val buildType: Int = 0x2c
}

/**
 * `hkpMoppCodeCodeInfo`, 0x10 bytes.
 */
public object HkpMoppCodeCodeInfo {
    public const val ByteSize: Int = 0x10

    /** `hkVector4` */
    public const val offset: Int = 0x0
}

/**
 * `hkpMoppCodeReindexedTerminal`, 0x8 bytes.
 */
public object HkpMoppCodeReindexedTerminal {
    public const val ByteSize: Int = 0x8

    /** `uint32` */
    public const val origShapeKey: Int = 0x0

    /** `uint32` */
    public const val reindexedShapeKey: Int = 0x4
}

/**
 * `hkpMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion`
 */
public object HkpMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8` */
    public const val type: Int = 0x8

    /** `uint8` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct` */
    public const val motionState: Int = 0x10

    /** `hkVector4` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*` */
    public const val savedMotion: Int = 0x118

    /** `uint16` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpMotorAction`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpMotorAction`
 */
public object HkpMotorAction {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpUnaryAction` */
    public const val entity: Int = 0x18

    /** `hkVector4` */
    public const val axis: Int = 0x20

    /** `float` */
    public const val spinRate: Int = 0x30

    /** `float` */
    public const val gain: Int = 0x34

    /** `bool` */
    public const val active: Int = 0x38
}

/**
 * `hkpMountedBallGun`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpBallGun` -> `hkpMountedBallGun`
 */
public object HkpMountedBallGun {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val keyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val listeners: Int = 0x14

    /** `float`, from `hkpBallGun` */
    public const val bulletRadius: Int = 0x20

    /** `float`, from `hkpBallGun` */
    public const val bulletVelocity: Int = 0x24

    /** `float`, from `hkpBallGun` */
    public const val bulletMass: Int = 0x28

    /** `float`, from `hkpBallGun` */
    public const val damageMultiplier: Int = 0x2c

    /** `int32`, from `hkpBallGun` */
    public const val maxBulletsInWorld: Int = 0x30

    /** `hkVector4`, from `hkpBallGun` */
    public const val bulletOffsetFromCenter: Int = 0x40

    /** `void*`, from `hkpBallGun` */
    public const val addedBodies: Int = 0x50

    /** `hkVector4` */
    public const val position: Int = 0x60
}

/**
 * `hkpMouseSpringAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpMouseSpringAction`
 */
public object HkpMouseSpringAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpUnaryAction` */
    public const val entity: Int = 0x18

    /** `hkVector4` */
    public const val positionInRbLocal: Int = 0x20

    /** `hkVector4` */
    public const val mousePositionInWorld: Int = 0x30

    /** `float` */
    public const val springDamping: Int = 0x40

    /** `float` */
    public const val springElasticity: Int = 0x44

    /** `float` */
    public const val maxRelativeForce: Int = 0x48

    /** `float` */
    public const val objectDamping: Int = 0x4c

    /** `uint32` */
    public const val shapeKey: Int = 0x50

    /** `hkArray<ptr>` */
    public const val applyCallbacks: Int = 0x54
}

/**
 * `hkpMovingSurfaceModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpMovingSurfaceModifierConstraintAtom`
 */
public object HkpMovingSurfaceModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val childSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val pad: Int = 0x18

    /** `hkVector4` */
    public const val velocity: Int = 0x20
}

/**
 * `hkpMultiRayShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpMultiRayShape`
 */
public object HkpMultiRayShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `hkArray<struct>` */
    public const val rays: Int = 0x10

    /** `float` */
    public const val rayPenetrationDistance: Int = 0x1c
}

/**
 * `hkpMultiRayShapeRay`, 0x20 bytes.
 */
public object HkpMultiRayShapeRay {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val start: Int = 0x0

    /** `hkVector4` */
    public const val end: Int = 0x10
}

/**
 * `hkpMultiSphereShape`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpMultiSphereShape`
 */
public object HkpMultiSphereShape {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `int32` */
    public const val numSpheres: Int = 0x10

    /** `hkVector4[8]` */
    public const val spheres: Int = 0x20
}

/**
 * `hkpNamedMeshMaterial`, 0x8 bytes.
 *
 * `hkpMeshMaterial` -> `hkpNamedMeshMaterial`
 */
public object HkpNamedMeshMaterial {
    public const val ByteSize: Int = 0x8

    /** `uint32`, from `hkpMeshMaterial` */
    public const val filterInfo: Int = 0x0

    /** `hkStringPtr` */
    public const val unnamed0x4: Int = 0x4
}

/**
 * `hkpNullCollisionFilter`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpNullCollisionFilter`
 */
public object HkpNullCollisionFilter {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24
}

/**
 * `hkpOverwritePivotConstraintAtom`, 0x4 bytes.
 *
 * `hkpConstraintAtom` -> `hkpOverwritePivotConstraintAtom`
 */
public object HkpOverwritePivotConstraintAtom {
    public const val ByteSize: Int = 0x4

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val copyToPivotBFromPivotA: Int = 0x2
}

/**
 * `hkpPairCollisionFilter`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpCollisionFilter` -> `hkpPairCollisionFilter`
 */
public object HkpPairCollisionFilter {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32[2]`, from `hkpCollisionFilter` */
    public const val prepad: Int = 0x18

    /** `enum:uint32`, from `hkpCollisionFilter` */
    public const val type: Int = 0x20

    /** `uint32[3]`, from `hkpCollisionFilter` */
    public const val postpad: Int = 0x24

    /** `struct` */
    public const val disabledPairs: Int = 0x30

    /** `struct*` */
    public const val childFilter: Int = 0x3c
}

/**
 * `hkpPairCollisionFilterMapPairFilterKeyOverrideType`, 0xc bytes.
 */
public object HkpPairCollisionFilterMapPairFilterKeyOverrideType {
    public const val ByteSize: Int = 0xc

    /** `void*` */
    public const val elem: Int = 0x0

    /** `int32` */
    public const val numElems: Int = 0x4

    /** `int32` */
    public const val hashMod: Int = 0x8
}

/**
 * `hkpParametricCurve`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpParametricCurve`
 */
public object HkpParametricCurve {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpPhantom`, 0xa4 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom`
 */
public object HkpPhantom {
    public const val ByteSize: Int = 0xa4

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `hkArray<ptr>` */
    public const val overlapListeners: Int = 0x8c

    /** `hkArray<ptr>` */
    public const val phantomListeners: Int = 0x98
}

/**
 * `hkpPhantomCallbackShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpPhantomCallbackShape`
 */
public object HkpPhantomCallbackShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc
}

/**
 * `hkpPhysicsData`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsData`
 */
public object HkpPhysicsData {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val worldCinfo: Int = 0x8

    /** `hkArray<ptr>` */
    public const val systems: Int = 0xc
}

/**
 * `hkpPhysicsSystem`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsSystem`
 */
public object HkpPhysicsSystem {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val rigidBodies: Int = 0x8

    /** `hkArray<ptr>` */
    public const val constraints: Int = 0x14

    /** `hkArray<ptr>` */
    public const val actions: Int = 0x20

    /** `hkArray<ptr>` */
    public const val phantoms: Int = 0x2c

    /** `hkStringPtr` */
    public const val unnamed0x38: Int = 0x38

    /** `hkUlong` */
    public const val userData: Int = 0x3c

    /** `bool` */
    public const val active: Int = 0x40
}

/**
 * `hkpPhysicsSystemWithContacts`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPhysicsSystem` -> `hkpPhysicsSystemWithContacts`
 */
public object HkpPhysicsSystemWithContacts {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val rigidBodies: Int = 0x8

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val constraints: Int = 0x14

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val actions: Int = 0x20

    /** `hkArray<ptr>`, from `hkpPhysicsSystem` */
    public const val phantoms: Int = 0x2c

    /** `hkStringPtr`, from `hkpPhysicsSystem` */
    public const val unnamed0x38: Int = 0x38

    /** `hkUlong`, from `hkpPhysicsSystem` */
    public const val userData: Int = 0x3c

    /** `bool`, from `hkpPhysicsSystem` */
    public const val active: Int = 0x40

    /** `hkArray<ptr>` */
    public const val contacts: Int = 0x44
}

/**
 * `hkpPointToPathConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPointToPathConstraintData`
 */
public object HkpPointToPathConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0xc

    /** `struct*` */
    public const val path: Int = 0x18

    /** `float` */
    public const val maxFrictionForce: Int = 0x1c

    /** `enum:int8` */
    public const val angularConstrainedDOF: Int = 0x20

    /** `hkTransform[2]` */
    public const val transform_OS_KS: Int = 0x30
}

/**
 * `hkpPointToPlaneConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPointToPlaneConstraintData`
 */
public object HkpPointToPlaneConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpPointToPlaneConstraintDataAtoms`, 0xa0 bytes.
 */
public object HkpPointToPlaneConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val lin: Int = 0x90
}

/**
 * `hkpPositionConstraintMotor`, 0x24 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpPositionConstraintMotor`
 */
public object HkpPositionConstraintMotor {
    public const val ByteSize: Int = 0x24

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val minForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val maxForce: Int = 0x10

    /** `float` */
    public const val tau: Int = 0x14

    /** `float` */
    public const val damping: Int = 0x18

    /** `float` */
    public const val proportionalRecoveryVelocity: Int = 0x1c

    /** `float` */
    public const val constantRecoveryVelocity: Int = 0x20
}

/**
 * `hkpPoweredChainData`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpPoweredChainData`
 */
public object HkpPoweredChainData {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0xc

    /** `hkArray<struct>` */
    public const val infos: Int = 0x18

    /** `float` */
    public const val tau: Int = 0x24

    /** `float` */
    public const val damping: Int = 0x28

    /** `float` */
    public const val cfmLinAdd: Int = 0x2c

    /** `float` */
    public const val cfmLinMul: Int = 0x30

    /** `float` */
    public const val cfmAngAdd: Int = 0x34

    /** `float` */
    public const val cfmAngMul: Int = 0x38

    /** `float` */
    public const val maxErrorDistance: Int = 0x3c
}

/**
 * `hkpPoweredChainDataConstraintInfo`, 0x50 bytes.
 */
public object HkpPoweredChainDataConstraintInfo {
    public const val ByteSize: Int = 0x50

    /** `hkVector4` */
    public const val pivotInA: Int = 0x0

    /** `hkVector4` */
    public const val pivotInB: Int = 0x10

    /** `hkQuaternion` */
    public const val aTc: Int = 0x20

    /** `hkQuaternion` */
    public const val bTc: Int = 0x30

    /** `struct*[3]` */
    public const val motors: Int = 0x40

    /** `bool` */
    public const val switchBodies: Int = 0x4c
}

/**
 * `hkpPoweredChainMapper`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpPoweredChainMapper`
 */
public object HkpPoweredChainMapper {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val links: Int = 0x8

    /** `hkArray<struct>` */
    public const val targets: Int = 0x14

    /** `hkArray<ptr>` */
    public const val chains: Int = 0x20
}

/**
 * `hkpPoweredChainMapperLinkInfo`, 0xc bytes.
 */
public object HkpPoweredChainMapperLinkInfo {
    public const val ByteSize: Int = 0xc

    /** `int32` */
    public const val firstTargetIdx: Int = 0x0

    /** `int32` */
    public const val numTargets: Int = 0x4

    /** `struct*` */
    public const val limitConstraint: Int = 0x8
}

/**
 * `hkpPoweredChainMapperTarget`, 0x8 bytes.
 */
public object HkpPoweredChainMapperTarget {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val chain: Int = 0x0

    /** `int32` */
    public const val infoIndex: Int = 0x4
}

/**
 * `hkpPrismaticConstraintData`, 0xd0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPrismaticConstraintData`
 */
public object HkpPrismaticConstraintData {
    public const val ByteSize: Int = 0xd0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpPrismaticConstraintDataAtoms`, 0xc0 bytes.
 */
public object HkpPrismaticConstraintDataAtoms {
    public const val ByteSize: Int = 0xc0

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val motor: Int = 0x90

    /** `struct` */
    public const val friction: Int = 0xa0

    /** `struct` */
    public const val ang: Int = 0xa8

    /** `struct` */
    public const val lin0: Int = 0xac

    /** `struct` */
    public const val lin1: Int = 0xb0

    /** `struct` */
    public const val linLimit: Int = 0xb4
}

/**
 * `hkpProjectileGun`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpFirstPersonGun` -> `hkpProjectileGun`
 */
public object HkpProjectileGun {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val type: Int = 0x8

    /** `hkStringPtr`, from `hkpFirstPersonGun` */
    public const val unnamed0xc: Int = 0xc

    /** `enum:uint8`, from `hkpFirstPersonGun` */
    public const val keyboardKey: Int = 0x10

    /** `hkArray<ptr>`, from `hkpFirstPersonGun` */
    public const val listeners: Int = 0x14

    /** `int32` */
    public const val maxProjectiles: Int = 0x20

    /** `float` */
    public const val reloadTime: Int = 0x24

    /** `float` */
    public const val reload: Int = 0x28

    /** `hkArray<ptr>` */
    public const val projectiles: Int = 0x2c

    /** `void*` */
    public const val world: Int = 0x38

    /** `void*` */
    public const val destructionWorld: Int = 0x3c
}

/**
 * `hkpProperty`, 0x10 bytes.
 */
public object HkpProperty {
    public const val ByteSize: Int = 0x10

    /** `uint32` */
    public const val key: Int = 0x0

    /** `uint32` */
    public const val alignmentPadding: Int = 0x4

    /** `struct` */
    public const val value: Int = 0x8
}

/**
 * `hkpPropertyValue`, 0x8 bytes.
 */
public object HkpPropertyValue {
    public const val ByteSize: Int = 0x8

    /** `int64` */
    public const val data: Int = 0x0
}

/**
 * `hkpPulleyConstraintAtom`, 0x40 bytes.
 *
 * `hkpConstraintAtom` -> `hkpPulleyConstraintAtom`
 */
public object HkpPulleyConstraintAtom {
    public const val ByteSize: Int = 0x40

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `hkVector4` */
    public const val fixedPivotAinWorld: Int = 0x10

    /** `hkVector4` */
    public const val fixedPivotBinWorld: Int = 0x20

    /** `float` */
    public const val ropeLength: Int = 0x30

    /** `float` */
    public const val leverageOnBodyB: Int = 0x34
}

/**
 * `hkpPulleyConstraintData`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpPulleyConstraintData`
 */
public object HkpPulleyConstraintData {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpPulleyConstraintDataAtoms`, 0x70 bytes.
 */
public object HkpPulleyConstraintDataAtoms {
    public const val ByteSize: Int = 0x70

    /** `struct` */
    public const val translations: Int = 0x0

    /** `struct` */
    public const val pulley: Int = 0x30
}

/**
 * `hkpRackAndPinionConstraintAtom`, 0xc bytes.
 *
 * `hkpConstraintAtom` -> `hkpRackAndPinionConstraintAtom`
 */
public object HkpRackAndPinionConstraintAtom {
    public const val ByteSize: Int = 0xc

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `float` */
    public const val pinionRadiusOrScrewPitch: Int = 0x4

    /** `bool` */
    public const val isScrew: Int = 0x8

    /** `int8` */
    public const val memOffsetToInitialAngleOffset: Int = 0x9

    /** `int8` */
    public const val memOffsetToPrevAngle: Int = 0xa

    /** `int8` */
    public const val memOffsetToRevolutionCounter: Int = 0xb
}

/**
 * `hkpRackAndPinionConstraintData`, 0xb0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRackAndPinionConstraintData`
 */
public object HkpRackAndPinionConstraintData {
    public const val ByteSize: Int = 0xb0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpRackAndPinionConstraintDataAtoms`, 0xa0 bytes.
 */
public object HkpRackAndPinionConstraintDataAtoms {
    public const val ByteSize: Int = 0xa0

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val rackAndPinion: Int = 0x90
}

/**
 * `hkpRagdollConstraintData`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRagdollConstraintData`
 */
public object HkpRagdollConstraintData {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpRagdollConstraintDataAtoms`, 0x150 bytes.
 */
public object HkpRagdollConstraintDataAtoms {
    public const val ByteSize: Int = 0x150

    /** `struct` */
    public const val transforms: Int = 0x0

    /** `struct` */
    public const val setupStabilization: Int = 0x90

    /** `struct` */
    public const val ragdollMotors: Int = 0xa0

    /** `struct` */
    public const val angFriction: Int = 0xf0

    /** `struct` */
    public const val twistLimit: Int = 0xfc

    /** `struct` */
    public const val coneLimit: Int = 0x110

    /** `struct` */
    public const val planesLimit: Int = 0x124

    /** `struct` */
    public const val ballSocket: Int = 0x138
}

/**
 * `hkpRagdollLimitsData`, 0xc0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRagdollLimitsData`
 */
public object HkpRagdollLimitsData {
    public const val ByteSize: Int = 0xc0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpRagdollLimitsDataAtoms`, 0xb0 bytes.
 */
public object HkpRagdollLimitsDataAtoms {
    public const val ByteSize: Int = 0xb0

    /** `struct` */
    public const val rotations: Int = 0x0

    /** `struct` */
    public const val twistLimit: Int = 0x70

    /** `struct` */
    public const val coneLimit: Int = 0x84

    /** `struct` */
    public const val planesLimit: Int = 0x98
}

/**
 * `hkpRagdollMotorConstraintAtom`, 0x50 bytes.
 *
 * `hkpConstraintAtom` -> `hkpRagdollMotorConstraintAtom`
 */
public object HkpRagdollMotorConstraintAtom {
    public const val ByteSize: Int = 0x50

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `bool` */
    public const val isEnabled: Int = 0x2

    /** `int16` */
    public const val initializedOffset: Int = 0x4

    /** `int16` */
    public const val previousTargetAnglesOffset: Int = 0x6

    /** `hkMatrix3` */
    public const val target_bRca: Int = 0x10

    /** `struct*[3]` */
    public const val motors: Int = 0x40
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
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<uint32>` */
    public const val removeInfo: Int = 0xc

    /** `void*` */
    public const val tempShapesToRemove: Int = 0x18
}

/**
 * `hkpReorientAction`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction` -> `hkpReorientAction`
 */
public object HkpReorientAction {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpUnaryAction` */
    public const val entity: Int = 0x18

    /** `hkVector4` */
    public const val rotationAxis: Int = 0x20

    /** `hkVector4` */
    public const val upAxis: Int = 0x30

    /** `float` */
    public const val strength: Int = 0x40

    /** `float` */
    public const val damping: Int = 0x44
}

/**
 * `hkpRigidBody`, 0x220 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpEntity` -> `hkpRigidBody`
 */
public object HkpRigidBody {
    public const val ByteSize: Int = 0x220

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `struct`, from `hkpEntity` */
    public const val material: Int = 0x8c

    /** `void*`, from `hkpEntity` */
    public const val limitContactImpulseUtilAndFlag: Int = 0x98

    /** `float`, from `hkpEntity` */
    public const val damageMultiplier: Int = 0x9c

    /** `void*`, from `hkpEntity` */
    public const val breakableBody: Int = 0xa0

    /** `uint32`, from `hkpEntity` */
    public const val solverData: Int = 0xa4

    /** `uint16`, from `hkpEntity` */
    public const val storageIndex: Int = 0xa8

    /** `uint16`, from `hkpEntity` */
    public const val contactPointCallbackDelay: Int = 0xaa

    /** `struct`, from `hkpEntity` */
    public const val constraintsMaster: Int = 0xac

    /** `hkArray<ptr>`, from `hkpEntity` */
    public const val constraintsSlave: Int = 0xb4

    /** `hkArray<uint8>`, from `hkpEntity` */
    public const val constraintRuntime: Int = 0xc0

    /** `void*`, from `hkpEntity` */
    public const val simulationIsland: Int = 0xcc

    /** `int8`, from `hkpEntity` */
    public const val autoRemoveLevel: Int = 0xd0

    /** `uint8`, from `hkpEntity` */
    public const val numShapeKeysInContactPointProperties: Int = 0xd1

    /** `uint8`, from `hkpEntity` */
    public const val responseModifierFlags: Int = 0xd2

    /** `uint32`, from `hkpEntity` */
    public const val uid: Int = 0xd4

    /** `struct`, from `hkpEntity` */
    public const val spuCollisionCallback: Int = 0xd8

    /** `struct`, from `hkpEntity` */
    public const val motion: Int = 0xe0

    /** `struct`, from `hkpEntity` */
    public const val contactListeners: Int = 0x200

    /** `struct`, from `hkpEntity` */
    public const val actions: Int = 0x208

    /** `struct*`, from `hkpEntity` */
    public const val localFrame: Int = 0x210

    /** `struct*`, from `hkpEntity` */
    public const val extendedListeners: Int = 0x214

    /** `uint32`, from `hkpEntity` */
    public const val npData: Int = 0x218
}

/**
 * `hkpRotationalConstraintData`, 0x90 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpRotationalConstraintData`
 */
public object HkpRotationalConstraintData {
    public const val ByteSize: Int = 0x90

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpRotationalConstraintDataAtoms`, 0x80 bytes.
 */
public object HkpRotationalConstraintDataAtoms {
    public const val ByteSize: Int = 0x80

    /** `struct` */
    public const val rotations: Int = 0x0

    /** `struct` */
    public const val ang: Int = 0x70
}

/**
 * `hkpSerializedAgentNnEntry`, 0x140 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedAgentNnEntry`
 */
public object HkpSerializedAgentNnEntry {
    public const val ByteSize: Int = 0x140

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val bodyA: Int = 0x8

    /** `struct*` */
    public const val bodyB: Int = 0xc

    /** `hkUlong` */
    public const val bodyAId: Int = 0x10

    /** `hkUlong` */
    public const val bodyBId: Int = 0x14

    /** `bool` */
    public const val useEntityIds: Int = 0x18

    /** `enum:int8` */
    public const val agentType: Int = 0x19

    /** `struct` */
    public const val atom: Int = 0x20

    /** `hkArray<uint8>` */
    public const val propertiesStream: Int = 0x50

    /** `hkArray<struct>` */
    public const val contactPoints: Int = 0x5c

    /** `hkArray<uint8>` */
    public const val cpIdMgr: Int = 0x68

    /** `uint8[160]` */
    public const val nnEntryData: Int = 0x74

    /** `struct` */
    public const val trackInfo: Int = 0x114

    /** `uint8[4]` */
    public const val endianCheckBuffer: Int = 0x12c

    /** `uint32` */
    public const val unnamed0x130: Int = 0x130
}

/**
 * `hkpSerializedDisplayMarker`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayMarker`
 */
public object HkpSerializedDisplayMarker {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkTransform` */
    public const val transform: Int = 0x10
}

/**
 * `hkpSerializedDisplayMarkerList`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayMarkerList`
 */
public object HkpSerializedDisplayMarkerList {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val markers: Int = 0x8
}

/**
 * `hkpSerializedDisplayRbTransforms`, 0x14 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSerializedDisplayRbTransforms`
 */
public object HkpSerializedDisplayRbTransforms {
    public const val ByteSize: Int = 0x14

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<struct>` */
    public const val transforms: Int = 0x8
}

/**
 * `hkpSerializedDisplayRbTransformsDisplayTransformPair`, 0x50 bytes.
 */
public object HkpSerializedDisplayRbTransformsDisplayTransformPair {
    public const val ByteSize: Int = 0x50

    /** `struct*` */
    public const val rb: Int = 0x0

    /** `hkTransform` */
    public const val localToDisplay: Int = 0x10
}

/**
 * `hkpSerializedSubTrack1nInfo`, 0x20 bytes.
 *
 * `hkpSerializedTrack1nInfo` -> `hkpSerializedSubTrack1nInfo`
 */
public object HkpSerializedSubTrack1nInfo {
    public const val ByteSize: Int = 0x20

    /** `hkArray<ptr>`, from `hkpSerializedTrack1nInfo` */
    public const val sectors: Int = 0x0

    /** `hkArray<ptr>`, from `hkpSerializedTrack1nInfo` */
    public const val subTracks: Int = 0xc

    /** `int32` */
    public const val sectorIndex: Int = 0x18

    /** `int32` */
    public const val offsetInSector: Int = 0x1c
}

/**
 * `hkpSerializedTrack1nInfo`, 0x18 bytes.
 */
public object HkpSerializedTrack1nInfo {
    public const val ByteSize: Int = 0x18

    /** `hkArray<ptr>` */
    public const val sectors: Int = 0x0

    /** `hkArray<ptr>` */
    public const val subTracks: Int = 0xc
}

/**
 * `hkpSetLocalRotationsConstraintAtom`, 0x70 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetLocalRotationsConstraintAtom`
 */
public object HkpSetLocalRotationsConstraintAtom {
    public const val ByteSize: Int = 0x70

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `hkRotation` */
    public const val rotationA: Int = 0x10

    /** `hkRotation` */
    public const val rotationB: Int = 0x40
}

/**
 * `hkpSetLocalTransformsConstraintAtom`, 0x90 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetLocalTransformsConstraintAtom`
 */
public object HkpSetLocalTransformsConstraintAtom {
    public const val ByteSize: Int = 0x90

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `hkTransform` */
    public const val transformA: Int = 0x10

    /** `hkTransform` */
    public const val transformB: Int = 0x50
}

/**
 * `hkpSetLocalTranslationsConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetLocalTranslationsConstraintAtom`
 */
public object HkpSetLocalTranslationsConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `hkVector4` */
    public const val translationA: Int = 0x10

    /** `hkVector4` */
    public const val translationB: Int = 0x20
}

/**
 * `hkpSetupStabilizationAtom`, 0x10 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSetupStabilizationAtom`
 */
public object HkpSetupStabilizationAtom {
    public const val ByteSize: Int = 0x10

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `bool` */
    public const val enabled: Int = 0x2

    /** `float` */
    public const val maxAngle: Int = 0x4

    /** `uint8[8]` */
    public const val padding: Int = 0x8
}

/**
 * `hkpShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape`
 */
public object HkpShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong` */
    public const val userData: Int = 0x8

    /** `enum:uint32` */
    public const val type: Int = 0xc
}

/**
 * `hkpShapeCollection`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection`
 */
public object HkpShapeCollection {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8` */
    public const val collectionType: Int = 0x15
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
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val shape: Int = 0x8

    /** `bool` */
    public const val isHierarchicalCompound: Int = 0xc

    /** `bool` */
    public const val hkdShapesCollected: Int = 0xd

    /** `hkArray<hkStringPtr>` */
    public const val childShapeNames: Int = 0x10

    /** `hkArray<hkTransform>` */
    public const val childTransforms: Int = 0x1c

    /** `hkTransform` */
    public const val transform: Int = 0x30
}

/**
 * `hkpShapeModifier`, 0x8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShapeModifier`
 */
public object HkpShapeModifier {
    public const val ByteSize: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpShapePhantom`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom`
 */
public object HkpShapePhantom {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val overlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val phantomListeners: Int = 0x98

    /** `struct` */
    public const val motionState: Int = 0xb0
}

/**
 * `hkpSimpleConstraintContactMgr`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimpleConstraintContactMgr`
 */
public object HkpSimpleConstraintContactMgr {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpSimpleContactConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpSimpleContactConstraintAtom`
 */
public object HkpSimpleContactConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16` */
    public const val sizeOfAllAtoms: Int = 0x2

    /** `uint16` */
    public const val numContactPoints: Int = 0x4

    /** `uint16` */
    public const val numReservedContactPoints: Int = 0x6

    /** `uint8` */
    public const val numUserDatasForBodyA: Int = 0x8

    /** `uint8` */
    public const val numUserDatasForBodyB: Int = 0x9

    /** `uint8` */
    public const val contactPointPropertiesStriding: Int = 0xa

    /** `uint16` */
    public const val maxNumContactPoints: Int = 0xc

    /** `struct` */
    public const val info: Int = 0x10
}

/**
 * `hkpSimpleContactConstraintDataInfo`, 0x20 bytes.
 */
public object HkpSimpleContactConstraintDataInfo {
    public const val ByteSize: Int = 0x20

    /** `uint16` */
    public const val flags: Int = 0x0

    /** `uint16` */
    public const val index: Int = 0x2

    /** `float` */
    public const val internalData0: Int = 0x4

    /** `hkHalf` */
    public const val rollingFrictionMultiplier: Int = 0x8

    /** `hkHalf` */
    public const val internalData1: Int = 0xa

    /** `uint32[5]` */
    public const val data: Int = 0xc
}

/**
 * `hkpSimpleMeshShape`, 0x44 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpSimpleMeshShape`
 */
public object HkpSimpleMeshShape {
    public const val ByteSize: Int = 0x44

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `hkArray<hkVector4>` */
    public const val vertices: Int = 0x18

    /** `hkArray<struct>` */
    public const val triangles: Int = 0x24

    /** `hkArray<uint8>` */
    public const val materialIndices: Int = 0x30

    /** `float` */
    public const val radius: Int = 0x3c

    /** `enum:uint8` */
    public const val weldingType: Int = 0x40
}

/**
 * `hkpSimpleMeshShapeTriangle`, 0x10 bytes.
 */
public object HkpSimpleMeshShapeTriangle {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val a: Int = 0x0

    /** `int32` */
    public const val b: Int = 0x4

    /** `int32` */
    public const val unnamed0x8: Int = 0x8

    /** `uint16` */
    public const val weldingInfo: Int = 0xc
}

/**
 * `hkpSimpleShapePhantom`, 0x170 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject` -> `hkpPhantom` -> `hkpShapePhantom` -> `hkpSimpleShapePhantom`
 */
public object HkpSimpleShapePhantom {
    public const val ByteSize: Int = 0x170

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpWorldObject` */
    public const val world: Int = 0x8

    /** `hkUlong`, from `hkpWorldObject` */
    public const val userData: Int = 0xc

    /** `struct`, from `hkpWorldObject` */
    public const val collidable: Int = 0x10

    /** `struct`, from `hkpWorldObject` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr`, from `hkpWorldObject` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>`, from `hkpWorldObject` */
    public const val properties: Int = 0x7c

    /** `void*`, from `hkpWorldObject` */
    public const val treeData: Int = 0x88

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val overlapListeners: Int = 0x8c

    /** `hkArray<ptr>`, from `hkpPhantom` */
    public const val phantomListeners: Int = 0x98

    /** `struct`, from `hkpShapePhantom` */
    public const val motionState: Int = 0xb0

    /** `hkArray<struct>` */
    public const val collisionDetails: Int = 0x160

    /** `bool` */
    public const val orderDirty: Int = 0x16c
}

/**
 * `hkpSimpleShapePhantomCollisionDetail`, 0x4 bytes.
 */
public object HkpSimpleShapePhantomCollisionDetail {
    public const val ByteSize: Int = 0x4

    /** `struct*` */
    public const val collidable: Int = 0x0
}

/**
 * `hkpSimulation`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimulation`
 */
public object HkpSimulation {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `uint32` */
    public const val determinismCheckFrameCounter: Int = 0x8

    /** `struct*` */
    public const val world: Int = 0xc

    /** `enum:uint8` */
    public const val lastProcessingStep: Int = 0x10

    /** `float` */
    public const val currentTime: Int = 0x14

    /** `float` */
    public const val currentPsiTime: Int = 0x18

    /** `float` */
    public const val physicsDeltaTime: Int = 0x1c

    /** `float` */
    public const val simulateUntilTime: Int = 0x20

    /** `float` */
    public const val frameMarkerPsiSnap: Int = 0x24

    /** `uint32` */
    public const val previousStepResult: Int = 0x28
}

/**
 * `hkpSimulationIsland`, 0x84 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpSimulationIsland`
 */
public object HkpSimulationIsland {
    public const val ByteSize: Int = 0x84

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6
}

/**
 * `hkpSingleShapeContainer`, 0x8 bytes.
 *
 * `hkpShapeContainer` -> `hkpSingleShapeContainer`
 */
public object HkpSingleShapeContainer {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val childShape: Int = 0x4
}

/**
 * `hkpSoftContactModifierConstraintAtom`, 0x30 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpSoftContactModifierConstraintAtom`
 */
public object HkpSoftContactModifierConstraintAtom {
    public const val ByteSize: Int = 0x30

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val childSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val pad: Int = 0x18

    /** `float` */
    public const val tau: Int = 0x20

    /** `float` */
    public const val maxAcceleration: Int = 0x24
}

/**
 * `hkpSphereMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpSphereMotion`
 */
public object HkpSphereMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpSphereRepShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape`
 */
public object HkpSphereRepShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc
}

/**
 * `hkpSphereShape`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpSphereShape`
 */
public object HkpSphereShape {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `uint32[3]` */
    public const val pad16: Int = 0x14
}

/**
 * `hkpSpringAction`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpBinaryAction` -> `hkpSpringAction`
 */
public object HkpSpringAction {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*`, from `hkpBinaryAction` */
    public const val entityA: Int = 0x18

    /** `struct*`, from `hkpBinaryAction` */
    public const val entityB: Int = 0x1c

    /** `hkVector4` */
    public const val lastForce: Int = 0x20

    /** `hkVector4` */
    public const val positionAinA: Int = 0x30

    /** `hkVector4` */
    public const val positionBinB: Int = 0x40

    /** `float` */
    public const val restLength: Int = 0x50

    /** `float` */
    public const val strength: Int = 0x54

    /** `float` */
    public const val damping: Int = 0x58

    /** `bool` */
    public const val onCompression: Int = 0x5c

    /** `bool` */
    public const val onExtension: Int = 0x5d
}

/**
 * `hkpSpringDamperConstraintMotor`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpSpringDamperConstraintMotor`
 */
public object HkpSpringDamperConstraintMotor {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val minForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val maxForce: Int = 0x10

    /** `float` */
    public const val springConstant: Int = 0x14

    /** `float` */
    public const val springDamping: Int = 0x18
}

/**
 * `hkpStiffSpringChainData`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpConstraintChainData` -> `hkpStiffSpringChainData`
 */
public object HkpStiffSpringChainData {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0xc

    /** `hkArray<struct>` */
    public const val infos: Int = 0x18

    /** `float` */
    public const val tau: Int = 0x24

    /** `float` */
    public const val damping: Int = 0x28

    /** `float` */
    public const val cfm: Int = 0x2c
}

/**
 * `hkpStiffSpringChainDataConstraintInfo`, 0x30 bytes.
 */
public object HkpStiffSpringChainDataConstraintInfo {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val pivotInA: Int = 0x0

    /** `hkVector4` */
    public const val pivotInB: Int = 0x10

    /** `float` */
    public const val springLength: Int = 0x20
}

/**
 * `hkpStiffSpringConstraintAtom`, 0x8 bytes.
 *
 * `hkpConstraintAtom` -> `hkpStiffSpringConstraintAtom`
 */
public object HkpStiffSpringConstraintAtom {
    public const val ByteSize: Int = 0x8

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `float` */
    public const val length: Int = 0x4
}

/**
 * `hkpStiffSpringConstraintData`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintData` -> `hkpStiffSpringConstraintData`
 */
public object HkpStiffSpringConstraintData {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10
}

/**
 * `hkpStiffSpringConstraintDataAtoms`, 0x40 bytes.
 */
public object HkpStiffSpringConstraintDataAtoms {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val pivots: Int = 0x0

    /** `struct` */
    public const val spring: Int = 0x30
}

/**
 * `hkpStorageExtendedMeshShape`, 0x110 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape` -> `hkpStorageExtendedMeshShape`
 */
public object HkpStorageExtendedMeshShape {
    public const val ByteSize: Int = 0x110

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `struct`, from `hkpExtendedMeshShape` */
    public const val embeddedTrianglesSubpart: Int = 0x20

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val aabbHalfExtents: Int = 0x90

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val aabbCenter: Int = 0xa0

    /** `void*`, from `hkpExtendedMeshShape` */
    public const val materialClass: Int = 0xb0

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val numBitsForSubpartIndex: Int = 0xb4

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val trianglesSubparts: Int = 0xb8

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val shapesSubparts: Int = 0xc4

    /** `hkArray<uint16>`, from `hkpExtendedMeshShape` */
    public const val weldingInfo: Int = 0xd0

    /** `enum:uint8`, from `hkpExtendedMeshShape` */
    public const val weldingType: Int = 0xdc

    /** `uint32`, from `hkpExtendedMeshShape` */
    public const val defaultCollisionFilterInfo: Int = 0xe0

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val cachedNumChildShapes: Int = 0xe4

    /** `float`, from `hkpExtendedMeshShape` */
    public const val triangleRadius: Int = 0xe8

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val padding: Int = 0xec

    /** `hkArray<ptr>` */
    public const val meshstorage: Int = 0xf0

    /** `hkArray<ptr>` */
    public const val shapestorage: Int = 0xfc
}

/**
 * `hkpStorageExtendedMeshShapeMaterial`, 0xc bytes.
 *
 * `hkpMeshMaterial` -> `hkpStorageExtendedMeshShapeMaterial`
 */
public object HkpStorageExtendedMeshShapeMaterial {
    public const val ByteSize: Int = 0xc

    /** `uint32`, from `hkpMeshMaterial` */
    public const val filterInfo: Int = 0x0

    /** `hkHalf` */
    public const val restitution: Int = 0x4

    /** `hkHalf` */
    public const val friction: Int = 0x6

    /** `hkUlong` */
    public const val userData: Int = 0x8
}

/**
 * `hkpStorageExtendedMeshShapeMeshSubpartStorage`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageExtendedMeshShapeMeshSubpartStorage`
 */
public object HkpStorageExtendedMeshShapeMeshSubpartStorage {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<hkVector4>` */
    public const val vertices: Int = 0x8

    /** `hkArray<uint8>` */
    public const val indices8: Int = 0x14

    /** `hkArray<uint16>` */
    public const val indices16: Int = 0x20

    /** `hkArray<uint32>` */
    public const val indices32: Int = 0x2c

    /** `hkArray<uint8>` */
    public const val materialIndices: Int = 0x38

    /** `hkArray<struct>` */
    public const val materials: Int = 0x44

    /** `hkArray<struct>` */
    public const val namedMaterials: Int = 0x50

    /** `hkArray<uint16>` */
    public const val materialIndices16: Int = 0x5c
}

/**
 * `hkpStorageExtendedMeshShapeShapeSubpartStorage`, 0x2c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageExtendedMeshShapeShapeSubpartStorage`
 */
public object HkpStorageExtendedMeshShapeShapeSubpartStorage {
    public const val ByteSize: Int = 0x2c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<uint8>` */
    public const val materialIndices: Int = 0x8

    /** `hkArray<struct>` */
    public const val materials: Int = 0x14

    /** `hkArray<uint16>` */
    public const val materialIndices16: Int = 0x20
}

/**
 * `hkpStorageMeshShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpStorageMeshShape`
 */
public object HkpStorageMeshShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `bool`, from `hkpShapeCollection` */
    public const val disableWelding: Int = 0x14

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val collectionType: Int = 0x15

    /** `hkVector4`, from `hkpMeshShape` */
    public const val scaling: Int = 0x20

    /** `int32`, from `hkpMeshShape` */
    public const val numBitsForSubpartIndex: Int = 0x30

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val subparts: Int = 0x34

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val weldingInfo: Int = 0x40

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val weldingType: Int = 0x4c

    /** `float`, from `hkpMeshShape` */
    public const val radius: Int = 0x50

    /** `int32[3]`, from `hkpMeshShape` */
    public const val pad: Int = 0x54

    /** `hkArray<ptr>` */
    public const val storage: Int = 0x60
}

/**
 * `hkpStorageMeshShapeSubpartStorage`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpStorageMeshShapeSubpartStorage`
 */
public object HkpStorageMeshShapeSubpartStorage {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<float>` */
    public const val vertices: Int = 0x8

    /** `hkArray<uint16>` */
    public const val indices16: Int = 0x14

    /** `hkArray<uint32>` */
    public const val indices32: Int = 0x20

    /** `hkArray<uint8>` */
    public const val materialIndices: Int = 0x2c

    /** `hkArray<uint32>` */
    public const val materials: Int = 0x38

    /** `hkArray<uint16>` */
    public const val materialIndices16: Int = 0x44
}

/**
 * `hkpThinBoxMotion`, 0x120 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpMotion` -> `hkpBoxMotion` -> `hkpThinBoxMotion`
 */
public object HkpThinBoxMotion {
    public const val ByteSize: Int = 0x120

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:uint8`, from `hkpMotion` */
    public const val type: Int = 0x8

    /** `uint8`, from `hkpMotion` */
    public const val deactivationIntegrateCounter: Int = 0x9

    /** `uint16[2]`, from `hkpMotion` */
    public const val deactivationNumInactiveFrames: Int = 0xa

    /** `struct`, from `hkpMotion` */
    public const val motionState: Int = 0x10

    /** `hkVector4`, from `hkpMotion` */
    public const val inertiaAndMassInv: Int = 0xc0

    /** `hkVector4`, from `hkpMotion` */
    public const val linearVelocity: Int = 0xd0

    /** `hkVector4`, from `hkpMotion` */
    public const val angularVelocity: Int = 0xe0

    /** `hkVector4[2]`, from `hkpMotion` */
    public const val deactivationRefPosition: Int = 0xf0

    /** `uint32[2]`, from `hkpMotion` */
    public const val deactivationRefOrientation: Int = 0x110

    /** `struct*`, from `hkpMotion` */
    public const val savedMotion: Int = 0x118

    /** `uint16`, from `hkpMotion` */
    public const val savedQualityTypeIndex: Int = 0x11c

    /** `hkHalf`, from `hkpMotion` */
    public const val gravityFactor: Int = 0x11e
}

/**
 * `hkpTransformShape`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpTransformShape`
 */
public object HkpTransformShape {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `struct` */
    public const val childShape: Int = 0x10

    /** `int32` */
    public const val childShapeSize: Int = 0x18

    /** `hkQuaternion` */
    public const val rotation: Int = 0x20

    /** `hkTransform` */
    public const val transform: Int = 0x30
}

/**
 * `hkpTriangleShape`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpShape` -> `hkpSphereRepShape` -> `hkpConvexShape` -> `hkpTriangleShape`
 */
public object HkpTriangleShape {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpShape` */
    public const val userData: Int = 0x8

    /** `enum:uint32`, from `hkpShape` */
    public const val type: Int = 0xc

    /** `float`, from `hkpConvexShape` */
    public const val radius: Int = 0x10

    /** `uint16` */
    public const val weldingInfo: Int = 0x14

    /** `enum:uint8` */
    public const val weldingType: Int = 0x16

    /** `uint8` */
    public const val isExtruded: Int = 0x17

    /** `hkVector4` */
    public const val vertexA: Int = 0x20

    /** `hkVector4` */
    public const val vertexB: Int = 0x30

    /** `hkVector4` */
    public const val vertexC: Int = 0x40

    /** `hkVector4` */
    public const val extrusion: Int = 0x50
}

/**
 * `hkpTriggerVolume`, 0x34 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpTriggerVolume`
 */
public object HkpTriggerVolume {
    public const val ByteSize: Int = 0x34

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkArray<ptr>` */
    public const val overlappingBodies: Int = 0x14

    /** `hkArray<struct>` */
    public const val eventQueue: Int = 0x20

    /** `struct*` */
    public const val triggerBody: Int = 0x2c

    /** `uint32` */
    public const val sequenceNumber: Int = 0x30
}

/**
 * `hkpTriggerVolumeEventInfo`, 0x10 bytes.
 */
public object HkpTriggerVolumeEventInfo {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val sortValue: Int = 0x0

    /** `struct*` */
    public const val body: Int = 0x8

    /** `enum:int32` */
    public const val operation: Int = 0xc
}

/**
 * `hkpTwistLimitConstraintAtom`, 0x14 bytes.
 *
 * `hkpConstraintAtom` -> `hkpTwistLimitConstraintAtom`
 */
public object HkpTwistLimitConstraintAtom {
    public const val ByteSize: Int = 0x14

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint8` */
    public const val isEnabled: Int = 0x2

    /** `uint8` */
    public const val twistAxis: Int = 0x3

    /** `uint8` */
    public const val refAxis: Int = 0x4

    /** `float` */
    public const val minAngle: Int = 0x8

    /** `float` */
    public const val maxAngle: Int = 0xc

    /** `float` */
    public const val angularLimitsTauFactor: Int = 0x10
}

/**
 * `hkpTypedBroadPhaseHandle`, 0xc bytes.
 *
 * `hkpBroadPhaseHandle` -> `hkpTypedBroadPhaseHandle`
 */
public object HkpTypedBroadPhaseHandle {
    public const val ByteSize: Int = 0xc

    /** `uint32`, from `hkpBroadPhaseHandle` */
    public const val id: Int = 0x0

    /** `int8` */
    public const val type: Int = 0x4

    /** `int8` */
    public const val ownerOffset: Int = 0x5

    /** `int8` */
    public const val objectQualityType: Int = 0x6

    /** `uint32` */
    public const val collisionFilterInfo: Int = 0x8
}

/**
 * `hkpUnaryAction`, 0x1c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpAction` -> `hkpUnaryAction`
 */
public object HkpUnaryAction {
    public const val ByteSize: Int = 0x1c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*`, from `hkpAction` */
    public const val world: Int = 0x8

    /** `void*`, from `hkpAction` */
    public const val island: Int = 0xc

    /** `hkUlong`, from `hkpAction` */
    public const val userData: Int = 0x10

    /** `hkStringPtr`, from `hkpAction` */
    public const val unnamed0x14: Int = 0x14

    /** `struct*` */
    public const val entity: Int = 0x18
}

/**
 * `hkpVelocityConstraintMotor`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpConstraintMotor` -> `hkpLimitedForceConstraintMotor` -> `hkpVelocityConstraintMotor`
 */
public object HkpVelocityConstraintMotor {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `enum:int8`, from `hkpConstraintMotor` */
    public const val type: Int = 0x8

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val minForce: Int = 0xc

    /** `float`, from `hkpLimitedForceConstraintMotor` */
    public const val maxForce: Int = 0x10

    /** `float` */
    public const val tau: Int = 0x14

    /** `float` */
    public const val velocityTarget: Int = 0x18

    /** `bool` */
    public const val useVelocityTargetFromConstraintTargets: Int = 0x1c
}

/**
 * `hkpViscousSurfaceModifierConstraintAtom`, 0x20 bytes.
 *
 * `hkpConstraintAtom` -> `hkpModifierConstraintAtom` -> `hkpViscousSurfaceModifierConstraintAtom`
 */
public object HkpViscousSurfaceModifierConstraintAtom {
    public const val ByteSize: Int = 0x20

    /** `enum:uint16`, from `hkpConstraintAtom` */
    public const val type: Int = 0x0

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val modifierAtomSize: Int = 0x10

    /** `uint16`, from `hkpModifierConstraintAtom` */
    public const val childSize: Int = 0x12

    /** `struct*`, from `hkpModifierConstraintAtom` */
    public const val child: Int = 0x14

    /** `uint32[2]`, from `hkpModifierConstraintAtom` */
    public const val pad: Int = 0x18
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
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkUlong`, from `hkpConstraintData` */
    public const val userData: Int = 0x8

    /** `struct` */
    public const val atoms: Int = 0x10

    /** `hkVector4` */
    public const val initialAxleInB: Int = 0x140

    /** `hkVector4` */
    public const val initialSteeringAxisInB: Int = 0x150
}

/**
 * `hkpWheelConstraintDataAtoms`, 0x130 bytes.
 */
public object HkpWheelConstraintDataAtoms {
    public const val ByteSize: Int = 0x130

    /** `struct` */
    public const val suspensionBase: Int = 0x0

    /** `struct` */
    public const val lin0Limit: Int = 0x90

    /** `struct` */
    public const val lin0Soft: Int = 0x9c

    /** `struct` */
    public const val lin1: Int = 0xa8

    /** `struct` */
    public const val lin2: Int = 0xac

    /** `struct` */
    public const val steeringBase: Int = 0xb0

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
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `struct*` */
    public const val simulation: Int = 0x8

    /** `hkVector4` */
    public const val gravity: Int = 0x10

    /** `void*` */
    public const val fixedIsland: Int = 0x20

    /** `struct*` */
    public const val fixedRigidBody: Int = 0x24

    /** `hkArray<ptr>` */
    public const val activeSimulationIslands: Int = 0x28

    /** `hkArray<ptr>` */
    public const val inactiveSimulationIslands: Int = 0x34

    /** `hkArray<ptr>` */
    public const val dirtySimulationIslands: Int = 0x40

    /** `void*` */
    public const val maintenanceMgr: Int = 0x4c

    /** `void*` */
    public const val memoryWatchDog: Int = 0x50

    /** `bool` */
    public const val assertOnRunningOutOfSolverMemory: Int = 0x54

    /** `void*` */
    public const val broadPhase: Int = 0x58

    /** `void*` */
    public const val kdTreeManager: Int = 0x5c

    /** `bool` */
    public const val autoUpdateTree: Int = 0x60

    /** `void*` */
    public const val broadPhaseDispatcher: Int = 0x64

    /** `void*` */
    public const val phantomBroadPhaseListener: Int = 0x68

    /** `void*` */
    public const val entityEntityBroadPhaseListener: Int = 0x6c

    /** `void*` */
    public const val broadPhaseBorderListener: Int = 0x70

    /** `void*` */
    public const val multithreadedSimulationJobData: Int = 0x74

    /** `void*` */
    public const val collisionInput: Int = 0x78

    /** `void*` */
    public const val collisionFilter: Int = 0x7c

    /** `void*` */
    public const val collisionDispatcher: Int = 0x80

    /** `void*` */
    public const val convexListFilter: Int = 0x84

    /** `void*` */
    public const val pendingOperations: Int = 0x88

    /** `int32` */
    public const val pendingOperationsCount: Int = 0x8c

    /** `int32` */
    public const val pendingBodyOperationsCount: Int = 0x90

    /** `int32` */
    public const val criticalOperationsLockCount: Int = 0x94

    /** `int32` */
    public const val criticalOperationsLockCountForPhantoms: Int = 0x98

    /** `bool` */
    public const val blockExecutingPendingOperations: Int = 0x9c

    /** `bool` */
    public const val criticalOperationsAllowed: Int = 0x9d

    /** `void*` */
    public const val pendingOperationQueues: Int = 0xa0

    /** `int32` */
    public const val pendingOperationQueueCount: Int = 0xa4

    /** `struct` */
    public const val multiThreadCheck: Int = 0xa8

    /** `bool` */
    public const val processActionsInSingleThread: Int = 0xb4

    /** `bool` */
    public const val allowIntegrationOfIslandsWithoutConstraintsInASeparateJob: Int = 0xb5

    /** `uint32` */
    public const val minDesiredIslandSize: Int = 0xb8

    /** `void*` */
    public const val modifyConstraintCriticalSection: Int = 0xbc

    /** `int32` */
    public const val isLocked: Int = 0xc0

    /** `void*` */
    public const val islandDirtyListCriticalSection: Int = 0xc4

    /** `void*` */
    public const val propertyMasterLock: Int = 0xc8

    /** `bool` */
    public const val wantSimulationIslands: Int = 0xcc

    /** `bool` */
    public const val useHybridBroadphase: Int = 0xcd

    /** `float` */
    public const val snapCollisionToConvexEdgeThreshold: Int = 0xd0

    /** `float` */
    public const val snapCollisionToConcaveEdgeThreshold: Int = 0xd4

    /** `bool` */
    public const val enableToiWeldRejection: Int = 0xd8

    /** `bool` */
    public const val wantDeactivation: Int = 0xd9

    /** `bool` */
    public const val shouldActivateOnRigidBodyTransformChange: Int = 0xda

    /** `float` */
    public const val deactivationReferenceDistance: Int = 0xdc

    /** `float` */
    public const val toiCollisionResponseRotateNormal: Int = 0xe0

    /** `int32` */
    public const val maxSectorsPerMidphaseCollideTask: Int = 0xe4

    /** `int32` */
    public const val maxSectorsPerNarrowphaseCollideTask: Int = 0xe8

    /** `bool` */
    public const val processToisMultithreaded: Int = 0xec

    /** `int32` */
    public const val maxEntriesPerToiMidphaseCollideTask: Int = 0xf0

    /** `int32` */
    public const val maxEntriesPerToiNarrowphaseCollideTask: Int = 0xf4

    /** `int32` */
    public const val maxNumToiCollisionPairsSinglethreaded: Int = 0xf8

    /** `enum:int32` */
    public const val simulationType: Int = 0xfc

    /** `float` */
    public const val numToisTillAllowedPenetrationSimplifiedToi: Int = 0x100

    /** `float` */
    public const val numToisTillAllowedPenetrationToi: Int = 0x104

    /** `float` */
    public const val numToisTillAllowedPenetrationToiHigher: Int = 0x108

    /** `float` */
    public const val numToisTillAllowedPenetrationToiForced: Int = 0x10c

    /** `uint32` */
    public const val lastEntityUid: Int = 0x110

    /** `uint32` */
    public const val lastIslandUid: Int = 0x114

    /** `uint32` */
    public const val lastConstraintUid: Int = 0x118

    /** `hkArray<ptr>` */
    public const val phantoms: Int = 0x11c

    /** `hkArray<ptr>` */
    public const val actionListeners: Int = 0x128

    /** `hkArray<ptr>` */
    public const val entityListeners: Int = 0x134

    /** `hkArray<ptr>` */
    public const val phantomListeners: Int = 0x140

    /** `hkArray<ptr>` */
    public const val constraintListeners: Int = 0x14c

    /** `hkArray<ptr>` */
    public const val worldDeletionListeners: Int = 0x158

    /** `hkArray<ptr>` */
    public const val islandActivationListeners: Int = 0x164

    /** `hkArray<ptr>` */
    public const val worldPostSimulationListeners: Int = 0x170

    /** `hkArray<ptr>` */
    public const val worldPostIntegrateListeners: Int = 0x17c

    /** `hkArray<ptr>` */
    public const val worldPostCollideListeners: Int = 0x188

    /** `hkArray<ptr>` */
    public const val islandPostIntegrateListeners: Int = 0x194

    /** `hkArray<ptr>` */
    public const val islandPostCollideListeners: Int = 0x1a0

    /** `hkArray<ptr>` */
    public const val contactListeners: Int = 0x1ac

    /** `hkArray<ptr>` */
    public const val contactImpulseLimitBreachedListeners: Int = 0x1b8

    /** `hkArray<ptr>` */
    public const val worldExtensions: Int = 0x1c4

    /** `void*` */
    public const val violatedConstraintArray: Int = 0x1d0

    /** `void*` */
    public const val broadPhaseBorder: Int = 0x1d4

    /** `void*` */
    public const val destructionWorld: Int = 0x1d8

    /** `void*` */
    public const val npWorld: Int = 0x1dc

    /** `hkVector4[2]` */
    public const val broadPhaseExtents: Int = 0x320

    /** `int32` */
    public const val broadPhaseNumMarkers: Int = 0x340

    /** `int32` */
    public const val sizeOfToiEventQueue: Int = 0x344

    /** `int32` */
    public const val broadPhaseQuerySize: Int = 0x348

    /** `int32` */
    public const val broadPhaseUpdateSize: Int = 0x34c

    /** `enum:int8` */
    public const val contactPointGeneration: Int = 0x350
}

/**
 * `hkpWorldCinfo`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldCinfo`
 */
public object HkpWorldCinfo {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `hkVector4` */
    public const val gravity: Int = 0x10

    /** `int32` */
    public const val broadPhaseQuerySize: Int = 0x20

    /** `float` */
    public const val contactRestingVelocity: Int = 0x24

    /** `enum:int8` */
    public const val broadPhaseBorderBehaviour: Int = 0x28

    /** `bool` */
    public const val mtPostponeAndSortBroadPhaseBorderCallbacks: Int = 0x29

    /** `struct` */
    public const val broadPhaseWorldAabb: Int = 0x30

    /** `bool` */
    public const val useKdTree: Int = 0x50

    /** `bool` */
    public const val useMultipleTree: Int = 0x51

    /** `enum:int8` */
    public const val treeUpdateType: Int = 0x52

    /** `bool` */
    public const val autoUpdateKdTree: Int = 0x53

    /** `float` */
    public const val collisionTolerance: Int = 0x54

    /** `struct*` */
    public const val collisionFilter: Int = 0x58

    /** `struct*` */
    public const val convexListFilter: Int = 0x5c

    /** `float` */
    public const val expectedMaxLinearVelocity: Int = 0x60

    /** `int32` */
    public const val sizeOfToiEventQueue: Int = 0x64

    /** `float` */
    public const val expectedMinPsiDeltaTime: Int = 0x68

    /** `struct*` */
    public const val memoryWatchDog: Int = 0x6c

    /** `int32` */
    public const val broadPhaseNumMarkers: Int = 0x70

    /** `enum:int8` */
    public const val contactPointGeneration: Int = 0x74

    /** `bool` */
    public const val allowToSkipConfirmedCallbacks: Int = 0x75

    /** `bool` */
    public const val useHybridBroadphase: Int = 0x76

    /** `float` */
    public const val solverTau: Int = 0x78

    /** `float` */
    public const val solverDamp: Int = 0x7c

    /** `int32` */
    public const val solverIterations: Int = 0x80

    /** `int32` */
    public const val solverMicrosteps: Int = 0x84

    /** `float` */
    public const val maxConstraintViolation: Int = 0x88

    /** `bool` */
    public const val forceCoherentConstraintOrderingInSolver: Int = 0x8c

    /** `float` */
    public const val snapCollisionToConvexEdgeThreshold: Int = 0x90

    /** `float` */
    public const val snapCollisionToConcaveEdgeThreshold: Int = 0x94

    /** `bool` */
    public const val enableToiWeldRejection: Int = 0x98

    /** `bool` */
    public const val enableDeprecatedWelding: Int = 0x99

    /** `float` */
    public const val iterativeLinearCastEarlyOutDistance: Int = 0x9c

    /** `int32` */
    public const val iterativeLinearCastMaxIterations: Int = 0xa0

    /** `uint8` */
    public const val deactivationNumInactiveFramesSelectFlag0: Int = 0xa4

    /** `uint8` */
    public const val deactivationNumInactiveFramesSelectFlag1: Int = 0xa5

    /** `uint8` */
    public const val deactivationIntegrateCounter: Int = 0xa6

    /** `bool` */
    public const val shouldActivateOnRigidBodyTransformChange: Int = 0xa7

    /** `float` */
    public const val deactivationReferenceDistance: Int = 0xa8

    /** `float` */
    public const val toiCollisionResponseRotateNormal: Int = 0xac

    /** `int32` */
    public const val maxSectorsPerMidphaseCollideTask: Int = 0xb0

    /** `int32` */
    public const val maxSectorsPerNarrowphaseCollideTask: Int = 0xb4

    /** `bool` */
    public const val processToisMultithreaded: Int = 0xb8

    /** `int32` */
    public const val maxEntriesPerToiMidphaseCollideTask: Int = 0xbc

    /** `int32` */
    public const val maxEntriesPerToiNarrowphaseCollideTask: Int = 0xc0

    /** `int32` */
    public const val maxNumToiCollisionPairsSinglethreaded: Int = 0xc4

    /** `float` */
    public const val numToisTillAllowedPenetrationSimplifiedToi: Int = 0xc8

    /** `float` */
    public const val numToisTillAllowedPenetrationToi: Int = 0xcc

    /** `float` */
    public const val numToisTillAllowedPenetrationToiHigher: Int = 0xd0

    /** `float` */
    public const val numToisTillAllowedPenetrationToiForced: Int = 0xd4

    /** `bool` */
    public const val enableDeactivation: Int = 0xd8

    /** `enum:int8` */
    public const val simulationType: Int = 0xd9

    /** `bool` */
    public const val enableSimulationIslands: Int = 0xda

    /** `uint32` */
    public const val minDesiredIslandSize: Int = 0xdc

    /** `bool` */
    public const val processActionsInSingleThread: Int = 0xe0

    /** `bool` */
    public const val allowIntegrationOfIslandsWithoutConstraintsInASeparateJob: Int = 0xe1

    /** `float` */
    public const val frameMarkerPsiSnap: Int = 0xe4

    /** `bool` */
    public const val fireCollisionCallbacks: Int = 0xe8
}

/**
 * `hkpWorldObject`, 0x8c bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkpWorldObject`
 */
public object HkpWorldObject {
    public const val ByteSize: Int = 0x8c

    /** `uint16`, from `hkReferencedObject` */
    public const val memSizeAndFlags: Int = 0x4

    /** `int16`, from `hkReferencedObject` */
    public const val referenceCount: Int = 0x6

    /** `void*` */
    public const val world: Int = 0x8

    /** `hkUlong` */
    public const val userData: Int = 0xc

    /** `struct` */
    public const val collidable: Int = 0x10

    /** `struct` */
    public const val multiThreadCheck: Int = 0x6c

    /** `hkStringPtr` */
    public const val unnamed0x78: Int = 0x78

    /** `hkArray<struct>` */
    public const val properties: Int = 0x7c

    /** `void*` */
    public const val treeData: Int = 0x88
}
