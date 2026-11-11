package com.sappyoak.dsanalyzer.shared.binary

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.Vec4

public fun littleEndian(size: Int): ByteBuffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN)
public fun ByteArray.littleEndian(): ByteBuffer = ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN)

public fun Int.toLittleEndian(): ByteArray = littleEndian(Int.SIZE_BYTES).putInt(this).array()

public fun Float.toLittleEndian(): ByteArray = littleEndian(Float.SIZE_BYTES).putFloat(this).array()

public fun ByteArray.toInts(): IntArray {
    val buffer = littleEndian().asIntBuffer()
    return IntArray(buffer.remaining()).also(buffer::get)
}

public fun ByteBuffer.putVec3(v: Vec3): ByteBuffer = putFloat(v.x).putFloat(v.y).putFloat(v.z)

public fun ByteBuffer.putVec4(v: Vec4): ByteBuffer = putFloat(v.x).putFloat(v.y).putFloat(v.z).putFloat(v.w)
