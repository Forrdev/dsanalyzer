package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** THrough [FieldArea.CameraPointer] */
private const val FIELD_AREA_SIZE: Int = 0x40

/** Through the frustum at '+0x5C' */
private const val FRPG_CAM_SIZE: Int = 0x60

public object FieldArea {
    public val Pointer: GamePointer = GamePointer(
        name = "FieldArea",
        base = DSRSignatures.FieldArea,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = FIELD_AREA_SIZE
    )

    public const val WorldInfoOwnerPointer: Int = 0x18
    public const val CameraPointer: Int = 0x38
}

/**
 * 'NS_FRPG::ChrCam'
 *
 * This seems to be Remastereds version of PTDE's 'ChrFollowCam.' The offsets are actually
 * shared as a 4x4 float matrix does not widen on x64, and the object's header absorbs the vtable going
 * from 4 to 8 bytes, so nothing after it moves.
 */
public object ChrCam {
    public val Pointer: GamePointer = GamePointer(
        name = "ChrCam",
        base = DSRSignatures.FieldArea,
        offsets = listOf(0L, FieldArea.CameraPointer.toLong()),
        lifetime = Lifetime.World,
        size = FRPG_CAM_SIZE
    )

    public const val Transform: Int = 0x10
    public const val FieldOfView: Int = 0x50
    public const val Aspect: Int = 0x54
    public const val Near: Int = 0x58
    public const val Far: Int = 0x5C
}