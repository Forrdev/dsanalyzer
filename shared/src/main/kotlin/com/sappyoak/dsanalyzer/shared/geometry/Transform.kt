package com.sappyoak.dsanalyzer.shared.geometry

import kotlinx.serialization.Serializable

@Serializable
data class Transform(
    val position: Vec3,
    val rotation: Vec3,
    val scale: Vec3 = Vec3(1f, 1f, 1f)
)