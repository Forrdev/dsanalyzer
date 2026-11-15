package com.sappyoak.dsanalyzer.shared.math

import kotlin.math.PI

internal const val PI_F = PI.toFloat()
internal const val TO_DEGREES = 180f / PI_F
internal const val TO_RADIANS = PI_F / 180f

public fun Float.toRadians() = this * TO_RADIANS
public fun Float.toDegrees() = this * TO_DEGREES
