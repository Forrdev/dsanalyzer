package com.sappyoak.dsanalyzer.shared.math

import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

public data class Vec3(public val x: Float, public val y: Float, public val z: Float) {
    public companion object {
        public val Zero: Vec3 = Vec3(0f, 0f, 0f)
    }
}

public fun BinaryReader.readVec3(): Vec3 = Vec3(readFloat(), readFloat(), readFloat())