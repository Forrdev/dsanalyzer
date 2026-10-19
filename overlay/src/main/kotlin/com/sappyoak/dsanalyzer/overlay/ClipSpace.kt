package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.shared.math.Mat4

internal const val NEAR_PLANE: Float = 0f
internal const val MIN_W: Float = 1e-4f

internal fun Mat4.clipX(x: Float, y: Float, z: Float): Float =
    x * r0.x + y * r1.x + z * r2.x + r3.x

internal fun Mat4.clipY(x: Float, y: Float, z: Float): Float =
    x * r0.y + y * r1.y + z * r2.y + r3.y

internal fun Mat4.clipZ(x: Float, y: Float, z: Float): Float =
    x * r0.z + y * r1.z + z * r2.z + r3.z

internal fun Mat4.clipW(x: Float, y: Float, z: Float): Float =
    x * r0.w + y * r1.w + z * r2.w + r3.w